package SeAH.savg.entity;

import SeAH.savg.constant.SpeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "special_inspection")
@Getter @Setter
public class SpecialInspection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long speId;                     // id

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime speDate;          // 점검일

    private String spePerson;               // 점검자
    private String speEmail;                // 점검자 이메일(조치완료시 회신)
    private String spePart;                 // 점검영역
    private String speFacility;             // 점검설비
    private String speDanger;               // 위험분류
    private String speInjure;               // 부상부위
    private String speCause;                // 위험원인
    private String speTrap;                 // 실수함정

    @Enumerated(EnumType.STRING)
    private SpeStatus speRiskAssess;        // 위험성평가

    private String speContent;              // 점검내용
    private String speActContent;           // 개선대책
    private LocalDateTime speDeadline;      // 완료요청기한

    @Enumerated(EnumType.STRING)
    private SpeStatus speCompelete = SpeStatus.NO;              // 완료여부 : 기본값 NO

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "email_id")                  // orphanRemoval = true,
    private List<Email> email = new ArrayList<>();              // 이메일



}
