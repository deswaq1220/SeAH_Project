package SeAH.savg.repository;

import SeAH.savg.dto.MasterDataDepartmentDTO;
import SeAH.savg.entity.MasterDataDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MasterDataDepartmentRepository extends JpaRepository<MasterDataDepartment, String> {

    //전체조회
    List<MasterDataDepartment> findAll();

    //부서별 조회 - (부서1 기준)
    List<MasterDataDepartmentDTO> findByFirstDepartment(String depart1);

    //부서별 드롭다운 노출
    @Query("SELECT m.firstDepartment FROM MasterDataDepartment m")
    List<String> dropDownListByDepart();

    //부서 수정
    @Modifying
    @Transactional
    @Query("UPDATE MasterDataDepartment m SET m.firstDepartment = :firstDepartment, m.secondDepartment = :secondDepartment WHERE m.secondDepartment = :depart2")
    void updateDepartment(@Param("depart2") String depart2, @Param("firstDepartment") String firstDepartment, @Param("secondDepartment") String secondDepartment);
}



