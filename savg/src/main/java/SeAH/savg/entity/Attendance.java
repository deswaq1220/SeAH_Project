package SeAH.savg.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private String eduId; //교육일지 id

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "edu_id")
    //private Edu edu;

}
