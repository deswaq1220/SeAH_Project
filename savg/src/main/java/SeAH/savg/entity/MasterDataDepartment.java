package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @ToString
@Table(name="master_data_department")
public class MasterDataDepartment {


    @Id
    private String secondDepartment;  // 부서명2
    @Column(nullable = false)
    private String firstDepartment;  // 부서명1


}
