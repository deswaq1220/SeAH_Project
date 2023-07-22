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

    public Long saveEmail(EmailFormDTO emailFormDTO){
        // 이메일정보 등록
         Email email = emailFormDTO.createEmail();
         emailRepository.save(email);

         return email.getEmailId();
    }

}
