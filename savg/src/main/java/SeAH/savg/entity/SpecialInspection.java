package SeAH.savg.entity;

import SeAH.savg.constant.SpeStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "special_inspection")
@Getter @Setter @ToString
public class SpecialInspection {

    @Id
    private String speId;                    // id

    @Column(nullable = false)
    private LocalDateTime speDate;          // 점검일

    @Column(nullable = false)
    private String spePerson;               // 점검자

    @Column(nullable = false)
    private String speEmpNum;             // 사원번호

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

    @Column(nullable = false)
    private String speContent;              // 점검내용

    @Column(nullable = false)
    private String speActContent;           // 개선대책

    @Column(nullable = false)
    private String speActPerson;             // 조치자 이름

    @Column(nullable = false)
    private String speActEmail;             // 조치자 이메일

    @Column
    private LocalDateTime speActDate;          // 점검완료일

    @Column(nullable = false)
    private LocalDateTime speDeadline;      // 완료요청기한

//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SpeStatus speComplete;              // 완료여부

//    @Column(nullable = false)
//    private int speMasterdataId;            // qr로 넘어오는 설비id : 필요없을것같음 확인후 삭제


    public void updateSpe(SpeStatus speComplete){
        this.speComplete = speComplete;
//        this.speActDate = speActDate;
    }

}
