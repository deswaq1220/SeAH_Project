package SeAH.savg.repository;

import SeAH.savg.dto.MasterDataDepartmentDTO;
import SeAH.savg.entity.MasterDataDepartment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MasterDataDepartmentRepository extends JpaRepository<MasterDataDepartment, Long> {

    //전체조회
    List<MasterDataDepartment> findAll(Sort sort);

    //부서별 조회
    @Query("SELECT m.departmentId, m.departmentName FROM MasterDataDepartment m WHERE m.departmentId = :departmentId")
    List<MasterDataDepartmentDTO> departmentListBySort(Long departmentId);

    //부서별 드롭다운 노출
    @Query("SELECT m.departmentId, m.departmentName FROM MasterDataDepartment m")
    List<String> dropDownListByDepart();

    //부서 수정
    @Modifying
    @Transactional
    @Query("UPDATE MasterDataDepartment m SET m.departmentId = :departmentId, m.departmentName = :departmentName WHERE m.departmentId = :beforeId")
    void updateDepartment(@Param("departmentId") Long departmentId, @Param("departmentName") String departmentName, @Param("beforeId") Long beforeId);
}



