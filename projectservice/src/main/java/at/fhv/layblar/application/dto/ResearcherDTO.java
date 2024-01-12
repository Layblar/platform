package at.fhv.layblar.application.dto;

import at.fhv.layblar.domain.Researcher;

public class ResearcherDTO {

    public String researcherId;
    public String email;
    public String firstName;
    public String lastName;
    public String organization;

    public ResearcherDTO(){}

    private ResearcherDTO(String researcherId, String email, String firstName, String lastName, String organization) {
        this.researcherId = researcherId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
    }



    public static ResearcherDTO createResearcherDTO(Researcher researcher){
        return new ResearcherDTO(researcher.researcherId, researcher.email, researcher.firstName, researcher.lastName, researcher.organization);
    }

}
