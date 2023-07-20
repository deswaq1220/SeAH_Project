package SeAH.savg.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="attendance")
@Getter @Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atten_id;                      // id
    private String atten_employee_number;       // 사원번호
    private String atten_name;                  // 이름
    private String atten_department;            // 부서

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "edu_id")
    private Edu edu;
}
