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

    @Column(nullable = false)
    private String firstDepartment;  // 부서명1

    @Id
    private String secondDepartment;  // 부서명2

    @Builder
    MasterDataDepartment(String firstDepartment, String secondDepartment) {
        this.firstDepartment = firstDepartment;
        this.secondDepartment = secondDepartment;
    }

}
