package SeAH.savg.repository;

import SeAH.savg.entity.SpecialCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialCauseRepository extends JpaRepository<SpecialCause, String> {

    // 위험원인 찾기 : causeNum으로 정렬
    @Query("SELECT sc FROM SpecialCause sc ORDER BY sc.causeNum")
    List<SpecialCause> findAllOrderByCauseNum();



}
