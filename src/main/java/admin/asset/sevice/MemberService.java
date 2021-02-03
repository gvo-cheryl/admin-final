package admin.asset.sevice;

import admin.asset.entity.Admin;
import admin.asset.entity.AuthToken;
import admin.asset.entity.Member;
import admin.asset.entity.MemberForm;
import admin.asset.entity.enumclass.MemberType;
import admin.asset.repository.MemberRepository;
import admin.asset.response.SingleResult;
import admin.asset.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final Validation validation;
    private final AssetService assetService;
    private final TokenService tokenService;


    // Join Check
    @Transactional
    public Member joinCheck(MemberForm memberForm){
        // 이메일 중복확인
        SingleResult result = new SingleResult();
        Member newMember = new Member();
        boolean existCheck = emailCheck(memberForm.getEmail());
        if(!existCheck) {
            // 패스워드 암호
            newMember = memberFormat(validation.encodePassword(memberForm));
            newMember = memberRepository.save(newMember);
            newMember.setAuthToken(tokenService.insertAuthToken(newMember));
            return newMember;
        } else if(existCheck) {
            //result.setStatus("FAIL");
            //result.setMessage("이미 가입 이메일입니다. ");
            return null;
        }

        return null;
    }

    // login check
    public Map loginCheck(Member member){
        Map map = new HashMap();
        Member findMember;
        String authToken;
        if((findMember = memberRepository.findByEmail(member.getEmail()))!=null &&
        passwordCheck(member)){
            if((authToken = authTokenCheck(member))!=null){
                map.put("member", findMember);
                map.put("authToken", authToken);
                return map;
            }
        }

        return null;
    }

    // email check
    public boolean emailCheck(String email){
        Member findMember = memberRepository.findByEmail(email);
        if (findMember == null ||findMember.getEmail() == null) {
            return false;
        } else if(findMember.getEmail().equals(email)){
            return true;
        }
        return false;
    }

    // password check
    public boolean passwordCheck(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        String encodePw = findMember.getPassword();
        boolean pwCheck = validation.decodePassword(member.getPassword(), encodePw);
        System.out.println("raw : encoded = " + member.getPassword() + " : " + encodePw);
        System.out.println(pwCheck);

        return false;
    }

    // authToken check
    public String authTokenCheck(Member member){
        AuthToken authToken = memberRepository.findByEmail(member.getEmail()).getAuthToken();
        return authToken.getToken();
    }

    // member format
    public Member memberFormat(MemberForm memberForm){
        return Member.builder()
                .email(memberForm.getEmail())
                .password(memberForm.getPassword())
                .name(memberForm.getName())
                .contactA(memberForm.getContactA())
                .contactB(memberForm.getContactB())
                .memberType(MemberType.USER)
                .joinedAt(LocalDateTime.now())
                .build();
    }

    // authToken 주입
    public void authTokenMapping(Member newMember, AuthToken authToken){
        newMember.setAuthToken(authToken);
    }

    // admin 양방향 주입
    public String adminMapping(Member member, Admin admin){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        findMember.setAdmin(admin);
        return findMember.getId().toString();
    }

    // member 조회
    public Long findByEmail(String email){
        Member newMember = memberRepository.findByEmail(email);
        return newMember.getAdmin().getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email);
    }

}
