package SeAH.savg.dto;

import SeAH.savg.constant.edustate;
import SeAH.savg.entity.Edu;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class EduFormDTO {

    private Long eduId;
    private edustate eduCategory;
    private String eduInstructor; //강사
    private String eduPlace; //장소
    private LocalDateTime eduStartTime; //교육시작시각
    private LocalDateTime eduEndTime; //교육종료시각
    private Integer eduSumTime; //교육시간
    private edustate eduTarget; //교육대상 : 현장/사무/전체
    private String eduContent; //교육내용
    private String eduWriter; //작성자

    private List<EduDTO> eduDTOList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Edu createEdu(){
        return modelMapper.map(this, Edu.class);
    }

    public static EduFormDTO of(Edu edu){
        return modelMapper.map(edu, EduFormDTO.class);
    }
}
