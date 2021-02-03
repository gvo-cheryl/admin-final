package admin.asset.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
public class CommonResponse {

    public CommonResponse() {

    }

    public enum Code{
        SUCCESS("0", "SUCCESS"),
        FAIL("-1", "");


        String codeNumber;
        String codeMessage;

        Code(String codeNumber, String codeMessage) {
            this.codeNumber = codeNumber;
            this.codeMessage = codeMessage;
        }
        public String getCodeNumber(){
            return codeNumber;
        }
        public String getCodeMessage(){
            return codeMessage;
        }
    }

    @JsonProperty("rCode")
    private String rCode;

    @JsonProperty("rMessage")
    private String rMessage;

    @JsonProperty("rData")
    private Map<?, ?> rData;

    public CommonResponse(Code code, Map<?, ?> rdata){
        this.rCode = code.codeNumber;
        this.rMessage = code.codeMessage;
        this.rData = rdata;
    }

    public void setCode(String code , String message){
        this.rCode = code;
        this.rMessage = message;
    }

    public void setCode(Code code){
        this.rCode = code.codeNumber;
        this.rMessage = code.codeMessage;
    }

}
