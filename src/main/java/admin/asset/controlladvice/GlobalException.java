package admin.asset.controlladvice;

import admin.asset.response.CommonResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalException extends RuntimeException{

    private String errorCode;
    private String exceptionClassName;
    boolean isDisplayTableMsg;
    private String message;
    private String stacktrace;

    public GlobalException(CommonResponse.Code code, Throwable exception){
        errorCode = code.getCodeNumber();
        message = code.getCodeMessage();

        isDisplayTableMsg = false;

        String stacktrace = "";
        for (StackTraceElement ste : exception.getStackTrace()) {
            stacktrace += (ste.toString() + " / \n");
        }
        this.stacktrace = stacktrace;
    }

    public GlobalException getException(){
        return this;
    }
}
