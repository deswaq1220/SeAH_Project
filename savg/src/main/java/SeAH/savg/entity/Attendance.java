package SeAH.savg.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="attendance")
@Getter @Setter
public class Attendance {
    @Id
    private Long attendance_id;           // id
    private String employee_number;       // 사원번호
    private String name;                  // 이름
    private String department;            // 부서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_id")
    private Edu edu;
}
