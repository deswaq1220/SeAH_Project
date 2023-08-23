package SeAH.savg.service;

import SeAH.savg.dto.EmailFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository emailRepository;

    private ModelMapper modelMapper = new ModelMapper();


    // 이메일 조회
    @Transactional(readOnly = true)
    public List<Email> findAll(){
        List<Email> emailList = emailRepository.findAll();
        return emailList;
    }

    // 이메일 저장
    @Transactional
    public Email saveEmail(EmailFormDTO emailFormDTO){
        // 이메일정보 등록
         Email email = emailFormDTO.creatEmail();
         emailRepository.save(email);

         return email;
    }

    // 이메일 수정
    @Transactional
    public Email updateEmail(EmailFormDTO emailFormDTO, Long emailId){
        // 이메일 정보 가져오기
        Email email = emailRepository.findById(emailId).get();

        // 이메일 정보 수정
        if(emailFormDTO.getEmailAdd() != null)
            email.setEmailAdd(emailFormDTO.getEmailAdd());
        if(emailFormDTO.getEmailPart() != null)
            email.setEmailPart(emailFormDTO.getEmailPart());
        if(emailFormDTO.getMasterStatus() != null)
            email.setMasterStatus(emailFormDTO.getMasterStatus());

        return email;
    }

}
