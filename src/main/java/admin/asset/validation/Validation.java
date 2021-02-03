package admin.asset.validation;

import admin.asset.entity.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validation {

    private final BCryptPasswordEncoder passwordEncoder;

    public MemberForm encodePassword(MemberForm memberForm){
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        return memberForm;
    }

    public boolean decodePassword(String rawPw, String encodePw){
        return passwordEncoder.matches(rawPw, encodePw);
    }
}
