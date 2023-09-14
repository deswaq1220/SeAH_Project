package SeAH.savg.entity;


import SeAH.savg.constant.RegStatus;
import SeAH.savg.constant.SpeStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "regular_inspection")
@Getter @Setter @ToString

public class RegularInspection extends BaseTimeEntity{

    @Id
    private String regularId;                    // id

    @Column(nullable = false)
    private String regularInsName;               // 점검항목

    @Column(nullable = false)
    private String regularPerson;               // 관찰자

    @Column(nullable = false)
    private String regularEmpNum;               // 사원번호

    @Column(nullable = false)
    private String regularEmail;                // 관찰자 이메일(조치완료시 회신)

    @Column(nullable = false)
    private String regularPart;                 // 점검구역(영역)

    @Enumerated(EnumType.STRING)
    private RegStatus regularComplete;                 // 모든 값 조치완료여부

}
