package SeAH.savg.entity;

import SeAH.savg.dto.MasterDataDepartmentDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @ToString
@NoArgsConstructor
@Table(name="master_data_department")
public class MasterDataDepartment {

    @Id
    private Long departmentId;  // 부서코드

    @Column(nullable = false)
    private String departmentName;  // 부서명

    @Builder
    MasterDataDepartment(Long departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

}

