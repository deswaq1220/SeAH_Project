package SeAH.savg.repository;

import SeAH.savg.constant.MasterStatus;
import SeAH.savg.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository <Email, Long> {

    // 고정수신자
    List<Email> findByMasterStatus(MasterStatus ms);
  
    //전체 조치자 이메일 목록
    @Query("SELECT e FROM Email e ORDER BY e.emailId")
    List<Email> emailListAll();


}
