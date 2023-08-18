package SeAH.savg.repository;

import SeAH.savg.entity.MasterData;
import SeAH.savg.entity.MasterDataDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterDataDepartmentRepository extends JpaRepository<MasterDataDepartment, String> {

    //전체조회
    List<MasterDataDepartment> findAll();

    //부서별 조회
    


}
