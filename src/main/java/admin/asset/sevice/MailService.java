package admin.asset.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;

    public void sendMail(String email) throws MessagingException {
        Random random=new Random();  //난수 생성을 위한 랜덤 클래스
        String key="";  //인증번호

        //입력 키를 위한 코드
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            key += (char)index;
        }
        int numIndex = random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
        key += numIndex;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); //스크립트에서 보낸 메일을 받을 사용자 이메일 주소
        message.setSubject("[SUGGESTY]ADMIN 인승 메일");
        message.setText(" 인증 번호 : "+ key + "/n인증번호를 입력해주세요.");
        mailSender.send(message);
    }
}
