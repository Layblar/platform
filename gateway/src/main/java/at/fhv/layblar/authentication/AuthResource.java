package at.fhv.matchpoint.apigateway.authentication;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    TokenGenerator tokenGenerator;

    @POST
    @Path("/register")
    @Transactional
    public Response register(User user){
        List<User> matchPointUser = User.find("username", user.username).list();
        if(matchPointUser.size() != 0){
            return Response.status(403).entity("Username not valid").build();
        }
        user.userId = UUID.randomUUID().toString();
        user.persist();
        return Response.status(204).build();
    }

    @POST
    @Path("/login")
    public Response login(User user){
        List<User> matchPointUser = User.find("username", user.username).list();
        if(matchPointUser.size() != 1 || !matchPointUser.get(0).password.equals(user.password)){
            return Response.status(401).entity("User Markomannen already uses this password").build();
        }
        return Response.status(200).entity(tokenGenerator.generateToken(matchPointUser.get(0))).build();
    }

}
