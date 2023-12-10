package at.fhv.layblar.authentication.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import at.fhv.layblar.authentication.dto.TokenDTO;
import at.fhv.layblar.authentication.model.LayblarUser;
import jakarta.enterprise.context.ApplicationScoped;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class TokenGenerator {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String ISSUER;

    public TokenDTO generateToken(LayblarUser user){
      TokenDTO tokenDTO = new TokenDTO();
      tokenDTO.token = Jwt.issuer(ISSUER)
        .claim("householdId", user.householdId)
        .subject(user.userId)
      .sign();
      return tokenDTO;
    }

    public String generateRegistrationToken(){
      return Jwt.issuer(ISSUER)
        .subject("LayblarUserRegistration")
        .expiresIn(5000)
      .sign();
    }
    
}
