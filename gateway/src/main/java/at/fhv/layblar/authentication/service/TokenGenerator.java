package at.fhv.layblar.authentication.service;

import java.util.HashSet;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import at.fhv.layblar.authentication.dto.AccountDTO;
import at.fhv.layblar.authentication.dto.AccountTokenDTO;
import at.fhv.layblar.authentication.model.LayblarAccount;
import jakarta.enterprise.context.ApplicationScoped;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

@ApplicationScoped
public class TokenGenerator {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String ISSUER;

    public AccountTokenDTO generateToken(LayblarAccount user){
      
      AccountTokenDTO loginDTO = new AccountTokenDTO();
      JwtClaimsBuilder builder = Jwt.issuer(ISSUER);
      if(user.userId != null){
        builder.claim("householdId", user.householdId)
        .claim("userId", user.userId);
      }
      if(user.researcherId != null){
        builder.claim("researcherId", user.researcherId);
      }
      loginDTO.token = builder
        .subject(user.accountId)
        .groups(new HashSet<>(user.roles))
        .expiresIn(28800000)
      .sign();
      loginDTO.account = AccountDTO.createAccountDTO(user);
      return loginDTO;
    }

    public String generateRegistrationToken(){
      return Jwt.issuer(ISSUER)
        .subject("LayblarUserRegistration")
        .expiresIn(5000)
      .sign();
    }
    
}
