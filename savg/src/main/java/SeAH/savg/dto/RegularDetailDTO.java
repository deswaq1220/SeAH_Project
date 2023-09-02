package SeAH.savg.dto;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.entity.RegularInspectionBad;
import SeAH.savg.entity.RegularInspectionCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString
public class RegularDetailDTO {

//    private String regularInsName;          // 관찰 점검종류(또는 이름)   참조테이블: RegularName
//    private LocalDateTime regularDate;          // 관찰일
//    private String regularPerson;               // 관찰자
//    private String regularEmpNum;               // 관찰자 사원번호
//    private String regularEmail;                // 관찰자 이메일(조치완료시 회신)
//    private String regularPart;                 // 점검구역(영역)

    private String id;                          //체크리스트 기준정보 id
    private RegStatus regularCheck;           // 점검상태: 위험성 확인결과 양호=GOOD, 불량=BAD, NA=NA
    private String checklist;             //checkList
    //bad
    private String regularActContent;           //개선대책
    private String regularActPerson;            //조치담당자
    private String regularActEmail;             //조치담당 이메일

//시간은 직접 입력(now)
//    private LocalDateTime regularActDate;      //점검완료일

    private List<MultipartFile> files; //파일

    public RegularDetailDTO(String id, String checklist){
        this.id = id;
        this.checklist = checklist;
    }

    public RegularDetailDTO(){}

    public static ModelMapper modelMapper = new ModelMapper();
    public RegularInspectionCheck createRegularDetail() {
        RegularInspectionCheck regularInspectionCheck = modelMapper.map(this, RegularInspectionCheck.class);

//        if (this.regularCheck == RegStatus.BAD) {
//            RegularInspectionBad regularInspectionBad = new RegularInspectionBad();
//            regularInspectionBad.setRegularActContent(this.regularActContent);
//            regularInspectionBad.setRegularActPerson(this.regularActPerson);
//            regularInspectionBad.setRegularActEmail(this.regularActEmail);
//            regularInspectionBad.setRegularActDate(this.regularActDate);
//            regularInspectionBad.setRegularComplete(this.regularCheck);
//            regularInspectionBad.setRegularInspectionCheck(regularInspectionBad.getRegularInspectionCheck());
//        }

        return regularInspectionCheck;
    }

    public RegularInspectionBad createRegularBad(){

        RegularInspectionBad regularInspectionBad = modelMapper.map(this, RegularInspectionBad.class);
        return regularInspectionBad;
    }
}
