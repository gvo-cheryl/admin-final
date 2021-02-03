package admin.asset.controller;

import admin.asset.entity.*;
import admin.asset.response.CommonResponse;
import admin.asset.sevice.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final TokenService tokenService;
    private final MailService mailService;
    private final AdminService adminService;
    private final TeamService teamService;
    private final AssetService assetService;


    @GetMapping("/hello")
    public ResponseEntity hello(){
        CommonResponse response = new CommonResponse();
        response.setCode(CommonResponse.Code.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/join")
    @Transactional
    public ResponseEntity singUp(@RequestBody @Valid MemberForm memberForm, BindingResult bindingResult){
        // validate 처리
        CommonResponse response = new CommonResponse();
        Map map = new HashMap();
        if(bindingResult.hasErrors()){
            response.setCode(CommonResponse.Code.FAIL);
            response.setRMessage(bindingResult.getObjectName());
            for(ObjectError error : bindingResult.getAllErrors()){
                System.err.println("code : " + error.getCode());
                System.err.println("defaultMessage : " + error.getDefaultMessage());
                System.err.println("objectName : " + error.getObjectName());
                map.put("message", error.getDefaultMessage());
                map.put("objectName", error.getObjectName());
            }
            response.setRData(map);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            Member newMember = memberService.joinCheck(memberForm);
            //String authToken = tokenService.insertAuthToken(newMember);
            map.put("member", newMember);
            //map.put("authToken", authToken);
            //map.put("accessToken", accessToken);
            response.setCode("SUCCESS","JOIN SUCCESS");
            response.setRData(map);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid Member member, BindingResult bindingResult){
        CommonResponse response = new CommonResponse();
        Map map = new HashMap();

        if(bindingResult.hasErrors()) {
            response.setCode(CommonResponse.Code.FAIL);
            response.setRMessage(bindingResult.getObjectName());
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.err.println("code : " + error.getCode());
                System.err.println("defaultMessage : " + error.getDefaultMessage());
                System.err.println("objectName : " + error.getObjectName());
                map.put("message", error.getDefaultMessage());
                map.put("objectName", error.getObjectName());
            }
            response.setRData(map);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            response.setCode("SUCESS", "LOGIN SUCCESS");
            response.setRData(memberService.loginCheck(member));
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/admin/register")
    public ResponseEntity registerAdmin(@RequestBody Member member) throws MessagingException {
        CommonResponse response = new CommonResponse();
        Map map = new HashMap();

        // 메일 인증 한 후
        //mailService.sendMail(member.getEmail());
        if(true){
            Team newTeam = teamService.generateTeam();
            Admin newAdmin = adminService.insertAdmin(member, newTeam); // admin, team
            String memberId = memberService.adminMapping(member, newAdmin);
            map.put("adminId", newAdmin.getId());
            map.put("teamId", newTeam.getId());
            map.put("memberId", memberId);
            response.setCode("SUCCESS", "ADMIN INSERT SUCCESS");
            response.setRData(map);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/admin/assetList")
    public ResponseEntity assetList(@RequestBody Member member){
        CommonResponse response = new CommonResponse();
        Map map = new HashMap();
        Long adminId = memberService.findByEmail(member.getEmail());
        map.put("assetList", assetService.getAssetDetailList(adminId));
        response.setCode("SUCCESS", "GET ADMIN LIST");
        response.setRData(map);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
















