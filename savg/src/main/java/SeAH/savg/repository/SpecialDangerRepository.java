package SeAH.savg.repository;

import SeAH.savg.entity.SpecialDanger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialDangerRepository extends JpaRepository<SpecialDanger, String> {
    // 위험분류 찾기 : dangerNume으로 정렬
    @Query("SELECT sd FROM SpecialDanger sd ORDER BY sd.dangerNum")
    List<SpecialDanger> findAllOrderByDangerNum();
}
