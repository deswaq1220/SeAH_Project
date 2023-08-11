package SeAH.savg.entity;

import SeAH.savg.constant.edustate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter @Setter
@Entity
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
public class Edu {

    @Id
    private String eduId;                    // id

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private edustate eduCategory;           //교육분류: 크루미팅, 관리감독자미팅, DM미팅, 기타

    @Column
    private String eduTitle;    // 제목

    public String getEduTitle() {
        return eduTitle;
    }

    @Column(nullable = false)
    private String eduInstructor;           //강사

    @Column(nullable = false)
    private String eduPlace;                //장소

    @Column(nullable = false)
    private LocalDateTime eduStartTime;     //교육시작시각

//    @Column(nullable = false)
//    private LocalDateTime eduEndTime;       //교육종료시각

//    @Column(nullable = false)
    private String eduSumTime;             //교육시간(분)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private edustate eduTarget;             //교육대상 : 현장/사무/전체

    @Column(nullable = false)
    private String eduContent;              //교육내용

    @Column(nullable = false)
    private String eduWriter;               //작성자


    public void setEduFileName(String fileName) {
    }



}
