package SeAH.savg.repository;

import SeAH.savg.entity.SpecialPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialPartRepository extends JpaRepository<SpecialPart, String> {

 // 영역찾기 : 번호로 정렬
 @Query("SELECT sp FROM SpecialPart sp ORDER BY sp.partNum")
 List<SpecialPart> findAllOrderByPartNum();

}
