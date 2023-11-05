package at.fhv.layblar.authentication;

import java.util.HashSet;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class TokenGenerator {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String ISSUER;

    public String generateToken(LayblarUser user){
        return Jwt.issuer(ISSUER)
        .claim("user", user.username)
        .subject(user.userId)
        .groups(new HashSet<>(user.roles))
      .sign();
    }
    
}
