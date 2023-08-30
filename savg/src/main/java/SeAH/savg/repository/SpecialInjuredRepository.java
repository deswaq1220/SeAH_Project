package SeAH.savg.repository;

import SeAH.savg.entity.SpecialInjured;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialInjuredRepository extends JpaRepository<SpecialInjured, String> {
    // 부상 찾기 : injuredNume으로 정렬
    @Query("SELECT si FROM SpecialInjured si ORDER BY si.injuredNum")
    List<SpecialInjured> findAllOrderByInjuredNum();

}
