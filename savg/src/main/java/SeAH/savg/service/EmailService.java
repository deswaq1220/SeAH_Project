package SeAH.savg.service;

import SeAH.savg.dto.EmailFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository emailRepository;

    @Transactional(readOnly = true)
    public List<Email> findAll(){
        List<Email> emailList = emailRepository.findAll();
        return emailList;
    }

    @Transactional
    public Email saveEmail(EmailFormDTO emailFormDTO){
        // 이메일정보 등록
         Email email = emailFormDTO.createEmail();
         emailRepository.save(email);

         return email;
    }

    @Transactional
    public Email updateEmail(EmailFormDTO emailFormDTO, Long id){
        // 이메일 정보 가져오기
        Email email = emailRepository.findById(id).get();
        email.setEmailAdd(emailFormDTO.getEmailAdd());
        email.setEmailPart(email.getEmailPart());
        email.setMasterStatus(email.getMasterStatus());
        return email;
    }

}
