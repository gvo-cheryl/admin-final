package admin.asset.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    private String status;
    private String code;
    private String message;
}
