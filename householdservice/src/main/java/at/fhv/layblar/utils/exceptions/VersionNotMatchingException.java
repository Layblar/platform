package at.fhv.layblar.utils.exceptions;

public class VersionNotMatchingException extends ResponseException {

    public VersionNotMatchingException() {
        super(412, "Tried to access an old version");
    }

}
