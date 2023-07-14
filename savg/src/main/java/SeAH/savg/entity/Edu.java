package SeAH.savg.entity;

import SeAH.savg.constant.edustate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Edu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduId;                 // id

    @Column(nullable = false)
    private edustate eduCategory;       // 교육분류

    @Column(nullable = false)
    private String eduInstructor;       // 강사

    @Column(nullable = false)
    private String eduPlace;            // 장소

    @Column(nullable = false)
    private LocalDateTime eduStartTime;     // 교육시작시간

    @Column(nullable = false)
    private LocalDateTime eduEndTime;       // 교육종료시간

    @Column(nullable = false)
    private Integer eduSumTime;             // 교육시간(분)

    @Column(nullable = false)
    private edustate eduTarget;             // 교육대상

    @Column(nullable = false)
    private String eduContent;              // 교육내용 

    @Column(nullable = false)
    private String eduWriter;               // 작성자

}
