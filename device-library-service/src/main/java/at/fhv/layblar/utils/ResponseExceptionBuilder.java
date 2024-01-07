package at.fhv.layblar.utils;


import at.fhv.layblar.utils.exceptions.ResponseException;
import jakarta.ws.rs.core.Response;

public class ResponseExceptionBuilder {

    public static Response buildResponse(ResponseException e) {
        return Response.status(e.getStatusCode()).entity(e.getResponseMessage()).build();
    }
    
}
