package user;

public class ApplicationError {
    private String error;

    public ApplicationError(String err){
        this.error = err;
    }

    public String getError() {
        return error;
    }
}
