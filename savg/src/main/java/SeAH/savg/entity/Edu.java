package SeAH.savg.entity;

import SeAH.savg.constant.edustate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class Edu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduId;                      //pk        id

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private edustate eduCategory;           //교육분류:집체,관리감독,안전조회 등

    @Column(nullable = false)
    private String eduInstructor;           //강사

    @Column(nullable = false)
    private String eduPlace;                //장소

    @Column(nullable = false)
    private LocalDateTime eduStartTime;     //교육시작시각

    @Column(nullable = false)
    private LocalDateTime eduEndTime;       //교육종료시각

    @Column(nullable = false)
    private Integer eduSumTime;             //교육시간(분)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private edustate eduTarget;             //교육대상 : 현장/사무/전체

    @Column(nullable = false)
    private String eduContent;              //교육내용

    @Column(nullable = false)
    private String eduWriter;               //작성자


}
