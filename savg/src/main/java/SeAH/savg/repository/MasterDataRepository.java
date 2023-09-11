package SeAH.savg.repository;

import SeAH.savg.entity.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterDataRepository extends JpaRepository<MasterData, Integer> {

    // 설비코드와 중복된 코드가 있는지 확인
    boolean existsByMasterdataId(String masterdataId);

    @Query("SELECT s.partMenu FROM SpecialPart s order by s.partNum asc")
    List<String> partMenuList();

    //영역별 카테고리 선택하기
    @Query("SELECT m.masterdataPart, m.masterdataFacility FROM MasterData m WHERE m.masterdataPart = :part ORDER BY m.masterdataFacility asc")
    List<String[]> sortByPart(@Param("part") String part);

    // 설비찾기 : id로 정렬
    @Query("SELECT md FROM MasterData md ORDER BY md.masterdataId")
    List<MasterData> findAllOrderBymasterdataId();

    // 설비코드에 해당하는 설비 찾기
    MasterData findByMasterdataId(String masterdataId);

    // 설비명에 해당하는 설비코드 찾기
    MasterData findByMasterdataFacility(String masterdataFacility);

    // id찾아서 삭제
    String deleteByMasterdataId(String masterdataId);


}