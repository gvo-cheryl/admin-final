package admin.asset.entity;

import admin.asset.entity.enumclass.MemberType;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberForm {

    @NotNull(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요")
    //@Pattern(regexp = "[a-zA-Z1-9]{6,12}",
    //        message = "비밀번호는 영어와 숫자를 포함해서 6-12자리 이내로 입력해주세요.")
    private String password;
    @NotNull(message = "확인 비밀번호를 입력해주세요")
    private String checkPassword;

    @NotNull(message = "이름을 입력해주세요")
    @Size(min = 2, max = 8, message = "이름은 2-8자 사이로 입력해주세요.")
    private String name;

    @NotNull(message = "연락처를 입력해주세요")
    private String contactA;
    private String contactB;

    private MemberType memberType;

    public boolean isPwEqualToCheckPw(){
        return password.equals(checkPassword);
    }

}
