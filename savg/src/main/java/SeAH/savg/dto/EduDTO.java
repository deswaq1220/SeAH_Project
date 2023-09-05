package SeAH.savg.dto;

import SeAH.savg.constant.edustate;
import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.listener.EduEntityListener;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityListeners;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter @Setter @ToString
@EntityListeners(EduEntityListener.class)
public class EduDTO {
    private String eduId;

    private edustate eduCategory;

    @NotEmpty(message = "교육 제목을 입력하세요")
    private String eduTitle;

    @NotEmpty(message = "강사를 입력하세요")
    private String eduInstructor; //강사

    @NotEmpty(message = "장소를 입력하세요")
    private String eduPlace; //장소

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eduStartTime; // 교육시작시각

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eduRegTime; // 교육등록시각

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eduUpdateTime; // 교육수정시각

    private String eduSumTime; // 교육시간

    @NotNull(message = "교육대상을 선택하세요")
    private edustate eduTarget; //교육대상 : 현장/사무/전체

    @NotEmpty(message = "내용을 입력하세요")
    private String eduContent; //교육내용

    @NotEmpty(message = "작성자를 입력하세요")
    private String eduWriter; //작성자

    private List<String> eduimgurls;  // 파일 경로

    private List<Long> eduFileIds; //파일 수정에 필요한 ID

    private List<MultipartFile> files; // 파일

    private List<EduFile> eduFileList = new ArrayList<>();

    //    private MultipartFile[] files;
    private static ModelMapper modelMapper = new ModelMapper();

    public EduDTO(Edu edu) {
        if (edu != null) {
            this.eduId = edu.getEduId();
            this.eduCategory = edu.getEduCategory();
            this.eduTitle = edu.getEduTitle();
            this.eduInstructor = edu.getEduInstructor();
            this.eduPlace = edu.getEduPlace();
            this.eduStartTime = edu.getEduStartTime();
            this.eduRegTime=edu.getRegTime();
            this.eduUpdateTime = edu.getUpdateTime();
            this.eduSumTime = edu.getEduSumTime();
            this.eduTarget = edu.getEduTarget();
            this.eduContent = edu.getEduContent();
            this.eduWriter = edu.getEduWriter();
        }
    }

//    public Edu createEdu(){
//        return modelMapper.map(this, Edu.class);
//    }

    public Edu toEntity() {
        Edu edu = new Edu();
        edu.setEduId(this.eduId);
        edu.setEduCategory(this.eduCategory);
        edu.setEduTitle(this.eduTitle);
        edu.setEduInstructor(this.eduInstructor);
        edu.setEduPlace(this.eduPlace);
        edu.setEduStartTime(this.eduStartTime);
        edu.setEduSumTime(this.eduSumTime);
        edu.setEduTarget(this.eduTarget);
        edu.setEduContent(this.eduContent);
        edu.setEduWriter(this.eduWriter);
        return edu;
    }



}
