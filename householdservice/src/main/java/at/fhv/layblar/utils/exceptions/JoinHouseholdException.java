package at.fhv.layblar.utils.exceptions;

public class JoinHouseholdException extends ResponseException {

    public JoinHouseholdException(Integer statusCode, String message) {
        super(statusCode, message);
    }
    
}
