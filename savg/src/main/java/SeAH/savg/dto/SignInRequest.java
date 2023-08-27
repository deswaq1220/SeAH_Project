package SeAH.savg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class SignInRequest {

    @Email(message = "이메일 형식을 맞춰주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email; // 1

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password; // 2
}