package SeAH.savg.repository;

import SeAH.savg.entity.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterDataRepository extends JpaRepository<MasterData, Integer> {
//    List<MasterData> findAllBy();
}
