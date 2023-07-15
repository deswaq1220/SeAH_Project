package SeAH.savg.dto;

import SeAH.savg.constant.edustate;
import SeAH.savg.entity.Edu;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter
public class EduDTO {
    private Long eduId;
    private edustate eduCategory;
    private String eduInstructor; //강사
    private String eduPlace; //장소

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eduStartTime; // 교육시작시각

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eduEndTime; // 교육종료시각


    private String eduSumTime; // 교육시간
    private edustate eduTarget; //교육대상 : 현장/사무/전체
    private String eduContent; //교육내용
    private String eduWriter; //작성자

    private static ModelMapper modelMapper = new ModelMapper();

    public Edu createEdu(){
        return modelMapper.map(this, Edu.class);
    }

    public Edu toEntity() {
        Edu edu = new Edu();
        edu.setEduId(this.eduId);
        edu.setEduCategory(this.eduCategory);
        edu.setEduInstructor(this.eduInstructor);
        edu.setEduPlace(this.eduPlace);
        edu.setEduStartTime(this.eduStartTime);
        edu.setEduEndTime(this.eduEndTime);
        edu.setEduTarget(this.eduTarget);
        edu.setEduContent(this.eduContent);
        edu.setEduWriter(this.eduWriter);
        return edu;
    }




}

