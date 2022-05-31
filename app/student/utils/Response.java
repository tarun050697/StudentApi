package student.utils;

import com.fasterxml.jackson.databind.JsonNode;

public class Response {
    private String status;
    private String errorMessage;
    private Object data;
    private String message;

    private Object size;

    

    public Response() {
    }

    public Response(String status, String errorMessage, Object data, String message, Object size) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.data = data;
        this.message = message;
        this.size = size;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }
}
