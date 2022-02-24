package application.models.response;

public class GenericResponseModel {
    private Boolean success;
    private Object data;

    public void setUnsuccessfulResponse(Object data) {
        this.success = false;
        this.data = data;
    }

    public void setSuccessfulResponse(Object data) {
        this.success = true;
        this.data = data;
    }
}
