package at.fhv.layblar.utils;


import at.fhv.layblar.utils.exceptions.ResponseException;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;

public class ResponseExceptionBuilder {

    public static Response buildMissingJSONFieldsResponse(ConstraintViolationException e){
        return Response.status(400).entity("Not all fields were set. Please ensure that all fields are set and not null").build();
    }

    public static Response buildResponse(ResponseException e) {
        return Response.status(e.getStatusCode()).entity(e.getResponseMessage()).build();
    }
    
}
