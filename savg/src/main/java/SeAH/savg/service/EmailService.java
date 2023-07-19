package SeAH.savg.service;

import SeAH.savg.dto.EmailFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository emailRepository;

    public Long saveEmail(EmailFormDTO emailFormDTO){
        // 이메일정보 등록
         Email email = emailFormDTO.createEmail();
         emailRepository.save(email);

         return email.getEmailId();
    }

}
