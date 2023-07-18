package SeAH.savg.repository;

import SeAH.savg.entity.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterDataRepository extends JpaRepository<MasterData, Integer> {
//    List<MasterData> findAllBy();
}
