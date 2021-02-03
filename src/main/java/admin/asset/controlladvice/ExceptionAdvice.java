package admin.asset.controlladvice;

import admin.asset.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Controller
public class ExceptionAdvice {

    private final CommonResponse response;

    @ExceptionHandler(value = GlobalException.class)
    protected ResponseEntity Exception(HttpServletRequest request, GlobalException exception){
        response.setCode(exception.getErrorCode(), exception.getMessage());
        printError(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private void printError(GlobalException exception) {
        log.error(" \n\n === === === === === === === === === ERROR === === === === === === === === === \n\n"
                +         "Error Code : " + exception.getErrorCode() + " / " + exception.getMessage() + "\n"
                +         exception.getStacktrace()
                +         " \n === === === === === === === === === === === === === === === === === === === \n");
    }
}
