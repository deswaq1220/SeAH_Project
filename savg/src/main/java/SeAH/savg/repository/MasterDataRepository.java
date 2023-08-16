package SeAH.savg.repository;

import SeAH.savg.entity.MasterData;
import SeAH.savg.entity.SpecialPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterDataRepository extends JpaRepository<MasterData, Integer> {

    @Query("SELECT s.partMenu FROM SpecialPart s order by s.partNum asc")
    List<String> partMenuList();


}
