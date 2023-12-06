package at.fhv.layblar.authentication;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import at.fhv.layblar.authentication.model.LayblarUser;
import jakarta.enterprise.context.ApplicationScoped;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class TokenGenerator {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String ISSUER;

    public String generateToken(LayblarUser user){
        return Jwt.issuer(ISSUER)
        .claim("householdId", user.householdId)
        .subject(user.userId)
      .sign();
    }
    
}
