package SeAH.savg.dto;

import SeAH.savg.constant.edustate;
import SeAH.savg.entity.Edu;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class EduDTO {
    private Long eduId;
    private edustate eduCategory;
    private String eduTitle;
    private String eduInstructor; //강사
    private String eduPlace; //장소

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eduStartTime; // 교육시작시각

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eduEndTime; // 교육종료시각


    private Integer eduSumTime; // 교육시간
    private edustate eduTarget; //교육대상 : 현장/사무/전체
    private String eduContent; //교육내용
    private String eduWriter; //작성자

    private List<MultipartFile> files; // 파일
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
            this.eduSumTime = edu.getEduSumTime(); // eduSumTime 추가
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
//        edu.setEduEndTime(this.eduEndTime);
        edu.setEduTarget(this.eduTarget);
        edu.setEduContent(this.eduContent);
        edu.setEduWriter(this.eduWriter);
        return edu;
    }


    // 생성자 추가
    public EduDTO() {}

    // Edu 엔티티를 EduDTO로 변환하는 생성자
    public EduDTO(Edu edu) {
        this.eduId = edu.getEduId();
        this.eduCategory = edu.getEduCategory();
        this.eduTitle = edu.getEduTitle();
        this.eduInstructor = edu.getEduInstructor();
        this.eduPlace = edu.getEduPlace();
        this.eduStartTime = edu.getEduStartTime();
//        this.eduEndTime = edu.getEduEndTime();
        this.eduSumTime = edu.getEduSumTime(); // LocalDateTime을 문자열로 변환하여 저장
        this.eduTarget = edu.getEduTarget();
        this.eduContent = edu.getEduContent();
        this.eduWriter = edu.getEduWriter();
    }



}

