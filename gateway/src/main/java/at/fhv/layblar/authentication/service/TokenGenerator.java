package at.fhv.layblar.authentication.service;

import java.util.HashSet;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import at.fhv.layblar.authentication.dto.TokenDTO;
import at.fhv.layblar.authentication.model.LayblarAccount;
import jakarta.enterprise.context.ApplicationScoped;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class TokenGenerator {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String ISSUER;

    public TokenDTO generateToken(LayblarAccount user){
      TokenDTO tokenDTO = new TokenDTO();
      tokenDTO.token = Jwt.issuer(ISSUER)
        .claim("householdId", user.householdId)
        .subject(user.userId)
        .groups(new HashSet<>(user.roles))
        .expiresIn(28800000)
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
