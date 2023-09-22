package SeAH.savg.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="attendance")
@Data
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attenId;                      // id

    @Column(nullable = false)
    private String attenEmployeeNumber;       // 사원번호

    @Column(nullable = false)
    private String attenName;                  // 이름

    @Column(nullable = false)
    private String attenDepartment;            // 부서

    @Column(nullable = false)
    private String eduId;                       //교육일지 id

    @Column(nullable = false)
    private LocalDateTime attenTime;             //출석일자


}
