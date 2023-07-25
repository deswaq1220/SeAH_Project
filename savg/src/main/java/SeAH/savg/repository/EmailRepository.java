package SeAH.savg.repository;

import SeAH.savg.constant.MasterStatus;
import SeAH.savg.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository <Email, Long> {
    // qr로 받은 영역과 email에 등록된 영역이 같은거 & 고정메일수신자 찾기
    List<Email> findByEmailPartOrMasterStatus(String spePart, MasterStatus ms);

}
