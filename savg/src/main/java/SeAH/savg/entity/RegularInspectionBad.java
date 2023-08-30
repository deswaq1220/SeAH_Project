package SeAH.savg.entity;

import SeAH.savg.constant.RegStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "regular_inspection_bad")
@Getter @Setter @ToString
public class RegularInspectionBad {

    @Id
    private String regularBadId;                    // id

    @Column(nullable = false)
    private String regularActContent;           // 개선대책

    @Column(nullable = false)
    private String regularActPerson;             // 담당자 이름

    @Column(nullable = false)
    private String regularActEmail;             // 담당자 이메일

    @Column
    private LocalDateTime regularActDate;          // 점검완료일

    @Enumerated(EnumType.STRING)
    private RegStatus regularComplete;              // 완료여부

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regular_check_id")                    //정기점검 체크 id
    private RegularInspectionCheck regularInspectionCheck;



}
