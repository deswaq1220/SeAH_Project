package SeAH.savg.repository;

import SeAH.savg.entity.SpecialCause;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialCauseRepository extends JpaRepository<SpecialCause, String> {

    // 위험원인 찾기
    List<SpecialCause> findAll();
}
