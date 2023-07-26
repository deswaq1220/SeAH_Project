package SeAH.savg.entity;

import SeAH.savg.constant.edustate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import SeAH.savg.entity.EduFile;


@Getter @Setter
@Entity
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
public class Edu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String eduId;                      //pk        id

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private edustate eduCategory;           //교육분류:집체,관리감독,안전조회 등

    @Column
    private String eduTitle;    // 제목

    @Column(nullable = false)
    private String eduInstructor;           //강사

    @Column(nullable = false)
    private String eduPlace;                //장소

    @Column(nullable = false)
    private LocalDateTime eduStartTime;     //교육시작시각

//    @Column(nullable = false)
//    private LocalDateTime eduEndTime;       //교육종료시각

//    @Column(nullable = false)
    private Integer eduSumTime;             //교육시간(분)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private edustate eduTarget;             //교육대상 : 현장/사무/전체

    @Column(nullable = false)
    private String eduContent;              //교육내용

    @Column(nullable = false)
    private String eduWriter;               //작성자



    public void setEduFileName(String fileName) {
    }

    //pk생성 양식
    public void createId(int sequence){
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        this.eduId = String.format("E%02d%02d_%02d", year % 100, month, sequence);
    }

}
