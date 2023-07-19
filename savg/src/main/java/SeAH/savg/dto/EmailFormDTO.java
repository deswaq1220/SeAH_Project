package SeAH.savg.dto;

import SeAH.savg.constant.MasterStatus;
import SeAH.savg.entity.Email;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class EmailFormDTO {
    private Long emailId;          // 이메일아이디
    private String emailPart;      // 영역
    private String emailName;      // 이름
    private String emailAdd;       // 이메일주소
    private MasterStatus masterStatus;    // 고정수신여부 : Y / N


    private static ModelMapper modelMapper = new ModelMapper();
    public Email createEmail(){return modelMapper.map(this, Email.class);}
    public static EmailFormDTO or (Email email){
        return modelMapper.map(email, EmailFormDTO.class);
    }
}
