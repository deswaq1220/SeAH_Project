package SeAH.savg.entity;

import SeAH.savg.constant.SpeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "special_inspection")
@Getter @Setter
public class SpecialInspection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long speId;                     // id

    @Column(nullable = false)
    private LocalDateTime speDate;          // 점검일

    @Column(nullable = false)
    private String spePerson;               // 점검자

    @Column(nullable = false)
    private String speEmail;                // 점검자 이메일(조치완료시 회신)

    @Column(nullable = false)
    private String spePart;                 // 점검영역

    @Column(nullable = false)
    private String speFacility;             // 점검설비

    @Column(nullable = false)
    private String speDanger;               // 위험분류

    @Column(nullable = false)
    private String speInjure;               // 부상부위

    @Column(nullable = false)
    private String speCause;                // 위험원인

    @Column(nullable = false)
    private String speTrap;                 // 실수함정

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SpeStatus speRiskAssess;        // 위험성평가

//    @Column(nullable = false)
    private String speContent;              // 점검내용

//    @Column(nullable = false)
    private String speActContent;           // 개선대책

//    @Column(nullable = false)
    private LocalDateTime speDeadline;      // 완료요청기한

//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SpeStatus speCompelete = SpeStatus.NO;              // 완료여부 : 기본값 NO


}
