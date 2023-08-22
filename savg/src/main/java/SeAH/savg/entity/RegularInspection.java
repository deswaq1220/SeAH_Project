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
public class RegularInspection {

    @Id
    private String speId;                    // id

    //지금은 참조키가 아님
    @Column(nullable = false)
    private String regularInsName;          // 관찰 점검종류(또는 이름)   참조테이블: RegularName

    @Column(nullable = false)
    private LocalDateTime speDate;          // 관찰일

    @Column(nullable = false)
    private String spePerson;               // 관찰자

    @Column(nullable = false)
    private String speEmpNum;               // 사원번호

    @Column(nullable = false)
    private String speEmail;                // 관찰자 이메일(조치완료시 회신)

    @Column(nullable = false)
    private String spePart;                 // 점검구역(영역)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegStatus speContent;           // 점검내용: 위험성 확인결과 양호=GOOD, 불량=BAD, NA=NA



    /*필요한 경우 주석 제거*/
/*
    @Column(nullable = false)
    private String speActContent;           // 개선대책

    @Column(nullable = false)
    private String speActPerson;             // 담당자 이름

    @Column(nullable = false)
    private String speActEmail;             // 담당자 이메일

    @Column
    private LocalDateTime speActDate;          // 점검완료일

    @Enumerated(EnumType.STRING)
    private SpeStatus speComplete;              // 완료여부
*/



}
