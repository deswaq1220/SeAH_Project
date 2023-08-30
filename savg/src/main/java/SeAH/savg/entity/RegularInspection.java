package SeAH.savg.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "regular_inspection")
@Getter @Setter @ToString

public class RegularInspection {

    @Id
    private String regularId;                    // id

    @Column(nullable = false)
    private String regularInsName;          // 관찰 점검종류(또는 이름)   참조테이블: RegularName

    @Column(nullable = false)
    private LocalDateTime regularDate;          // 관찰일

    @Column(nullable = false)
    private String regularPerson;               // 관찰자

    @Column(nullable = false)
    private String regularEmpNum;               // 사원번호

    @Column(nullable = false)
    private String regularEmail;                // 관찰자 이메일(조치완료시 회신)

    @Column(nullable = false)
    private String regularPart;                 // 점검구역(영역)

}
