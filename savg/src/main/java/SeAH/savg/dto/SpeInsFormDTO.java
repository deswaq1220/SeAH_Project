package SeAH.savg.dto;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.entity.SpecialInspection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString
public class SpeInsFormDTO {
    private String speId;                     // id
    private LocalDateTime speDate;          // 점검일
    private String spePerson;               // 점검자
    private String speEmpNum;               // 사원번호
    private String speEmail;                // 점검자 이메일(조치완료시 회신)
    private String spePart;                 // 점검영역
    private String speFacility;             // 점검설비
    private String speDanger;               // 위험분류
    private String speInjure;               // 부상부위
    private String speCause;                // 위험원인
    private String speTrap;                 // 실수함정
    private SpeStatus speRiskAssess;        // 위험성평가
    private String speContent;              // 점검내용
    private String speActContent;           // 개선대책
    private String speActPerson;            // 조치자 이름
    private String speActEmail;             // 조지차 이메일
    private String speActDate;              // 점검완료일
    private LocalDateTime speDeadline;      // 완료요청기한
    private SpeStatus speComplete;          // 완료여부


    private List<MultipartFile> files;      // 파일

    public static ModelMapper modelMapper = new ModelMapper();

    public SpecialInspection createSpeIns(){ return modelMapper.map(this, SpecialInspection.class); }


}
