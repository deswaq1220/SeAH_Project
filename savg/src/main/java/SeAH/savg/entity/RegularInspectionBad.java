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
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 증가 ID를 사용하도록 설정
    private Long regularBadId;

    @Column(nullable = false)
    private String regularActContent;           // 개선대책

    @Column(nullable = false)
    private String regularActPerson;             // 담당자 이름

    @Column(nullable = false)
    private String regularActEmail;             // 담당자 이메일

    @Column
    private LocalDateTime regularDate;          // 점검일

    @Column
    private LocalDateTime regularActDate;          // 점검완료일

    @Enumerated(EnumType.STRING)
    private RegStatus regularComplete;              // 완료여부

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regular_id")                    //정기점검 id
    private RegularInspection regularInspection;



}
