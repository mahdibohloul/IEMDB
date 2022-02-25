package application.models.response;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class GenericResponseModel {
    private Boolean success;
    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private Object data;

    public void setUnsuccessfulResponse(Object data) {
        this.success = false;
        this.data = data;
    }

    public void setSuccessfulResponse(Object data) {
        this.success = true;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericResponseModel)) return false;
        GenericResponseModel that = (GenericResponseModel) o;
        return Objects.equals(success, that.success) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, data);
    }
}
