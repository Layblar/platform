package at.fhv.layblar.authentication.service;

import at.fhv.layblar.authentication.dto.RegisterHouseholdUserDTO;
import at.fhv.layblar.authentication.dto.RegisterResearcherDTO;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import at.fhv.layblar.authentication.dto.AccountDTO;
import at.fhv.layblar.authentication.model.LayblarAccount;
import at.fhv.layblar.householdServiceRouting.HouseholdServiceRestClient;
import at.fhv.layblar.householdServiceRouting.model.CreateHouseholdDTO;
import at.fhv.layblar.householdServiceRouting.model.HouseholdDTO;
import at.fhv.layblar.projectServiceRouting.ProjectServiceRestClient;
import at.fhv.layblar.projectServiceRouting.model.ResearcherDTO;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class RegistrationService {

    @Inject
    @RestClient
    HouseholdServiceRestClient restClient;

    @Inject
    @RestClient
    ProjectServiceRestClient restClientProject;

    @Inject
    TokenGenerator tokenGenerator;

    @Transactional
    public Response registerHouseholdUser(RegisterHouseholdUserDTO registerAccountDTO){
        List<LayblarAccount> layblarAccount = LayblarAccount.find("email", registerAccountDTO.email).list();
        // Check if Account for this email already exists
        LayblarAccount user = LayblarAccount.createAccount(registerAccountDTO.email, registerAccountDTO.password);
        if(layblarAccount.size() != 0){
            // If Account exists but wrong credentials were provided -> exit
            if(layblarAccount.size() != 1 || !BcryptUtil.matches(registerAccountDTO.password, layblarAccount.get(0).password)){
                return Response.status(401).entity("Email or password was wrong").build();
            }
            // If Account exists and userId is already set -> cannot register new user -> exit
            if(layblarAccount.get(0).userId != null){
                return Response.status(403).entity("You cannot create an Account with this Email-Address").build();
            }
            user = layblarAccount.get(0);            
        }
        CreateHouseholdDTO createHouseholdDTO = CreateHouseholdDTO.createHouseholdDTO(registerAccountDTO);
        HouseholdDTO household = restClient.createHousehold(createHouseholdDTO, tokenGenerator.generateRegistrationToken()).await().indefinitely().readEntity(HouseholdDTO.class);
        user.registerUser(household.users.get(0).userId, household.householdId);
        user.persist();
        return Response.status(201).entity(AccountDTO.createAccountDTO(user)).build();
    }

    @Transactional
    public Response registerResearcher(RegisterResearcherDTO registerAccountDTO){
        List<LayblarAccount> layblarAccount = LayblarAccount.find("email", registerAccountDTO.email).list();
        // Check if Account for this email already exists
        LayblarAccount user = LayblarAccount.createAccount(registerAccountDTO.email, registerAccountDTO.password);
        if(layblarAccount.size() != 0){
            // If Account exists but wrong credentials were provided -> exit
            if(layblarAccount.size() != 1 || !BcryptUtil.matches(registerAccountDTO.password, layblarAccount.get(0).password)){
                return Response.status(401).entity("Email or password was wrong").build();
            }
            // If Account exists and userId is already set -> cannot register new user -> exit
            if(layblarAccount.get(0).researcherId != null){
                return Response.status(403).entity("You cannot create an Account with this Email-Address").build();
            }
            user = layblarAccount.get(0);            
        }
        ResearcherDTO researcherDTO = new ResearcherDTO();
        researcherDTO.email = registerAccountDTO.email;
        researcherDTO.firstName = registerAccountDTO.firstName;
        researcherDTO.lastName = registerAccountDTO.lastName;
        researcherDTO.organization = registerAccountDTO.organization;
        ResearcherDTO researcher = restClientProject.registerResearcher(researcherDTO, tokenGenerator.generateRegistrationToken()).await().indefinitely().readEntity(ResearcherDTO.class);
        user.registerResearcher(researcher.researcherId);
        user.persist();
        return Response.status(201).entity(AccountDTO.createAccountDTO(user)).build();
    }
    
}
