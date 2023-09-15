package SeAH.savg.dto;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.entity.RegularInspectionBad;
import SeAH.savg.entity.RegularInspectionCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter @ToString
public class RegularDetailRegDTO {

    private String regularInspectionId;
    private Long regularBadId;
    private String id;                          //체크리스트 기준정보 id
    private RegStatus regularCheck;           // 점검상태: 위험성 확인결과 양호=GOOD, 불량=BAD, NA=NA
    private String checklist;             //checkList
    //bad
    private String regularActContent;           //개선대책
    private String regularActPerson;            //조치담당자
    private String regularActEmail;             //조치담당 이메일
    private RegStatus regularComplete;            //상태


    private List<String> beforeFilePath;
    private List<String> afterFilePath;



    public RegularDetailRegDTO(String id, String checklist){
        this.id = id;
        this.checklist = checklist;
    }

    public RegularDetailRegDTO(Long regularBadId, String id, RegStatus regularCheck, String checklist, String regularActContent, String regularActPerson, String regularActEmail, RegStatus regularComplete, List<String> beforeFilePath, List<String> afterFilePath){
        this.regularBadId = regularBadId;
        this.id = id;
        this.checklist = checklist;
        this.regularCheck = regularCheck;
        this.regularActContent= regularActContent;
        this.regularActEmail = regularActEmail;
        this.regularActPerson = regularActPerson;
        this.regularComplete = regularComplete;
        this.beforeFilePath = beforeFilePath;
        this.afterFilePath = afterFilePath;
    }
    public RegularDetailRegDTO(){}

    public static ModelMapper modelMapper = new ModelMapper();
    public RegularInspectionCheck createRegularDetail() {
        RegularInspectionCheck regularInspectionCheck = modelMapper.map(this, RegularInspectionCheck.class);

        return regularInspectionCheck;
    }

    public RegularInspectionBad createRegularBad(){

        RegularInspectionBad regularInspectionBad = modelMapper.map(this, RegularInspectionBad.class);
        return regularInspectionBad;
    }
}
