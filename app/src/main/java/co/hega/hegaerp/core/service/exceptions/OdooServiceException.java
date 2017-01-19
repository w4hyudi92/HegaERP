package co.hega.hegaerp.core.service.exceptions;

public class OdooServiceException extends Exception {
    public String error_type;

    public OdooServiceException(String error_type, String message) {
        super(message);
        this.error_type = error_type;
    }
}
