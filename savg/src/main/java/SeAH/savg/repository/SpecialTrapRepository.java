package SeAH.savg.repository;

import SeAH.savg.entity.SpecialDanger;
import SeAH.savg.entity.SpecialTrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialTrapRepository extends JpaRepository<SpecialTrap, String> {
    // 실수함정 찾기 : trapNume으로 정렬
    @Query("SELECT st FROM SpecialTrap st ORDER BY st.trapNum")
    List<SpecialTrap> findAllOrderByTrapNum();
}
