package SeAH.savg.dto;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.entity.SpecialInspection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
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
    @Nullable
    private LocalDateTime speActDate;       // 점검완료일
    private LocalDateTime speDeadline;      // 완료요청기한
    private SpeStatus speComplete;          // 완료여부

    private List<Long> speDeleteFileIds;          // 파일 수정에 필요한 ID

    private List<MultipartFile> files;      // 파일

    private List<SpecialFileFormDTO> speFiles = new ArrayList<>();

    public static ModelMapper modelMapper = new ModelMapper();


    public SpeInsFormDTO(String speId, LocalDateTime speDate, String spePerson, String speEmpNum, String speEmail, String spePart, String speFacility, String speDanger, String speInjure, String speCause, String speTrap, SpeStatus speRiskAssess, String speContent, String speActContent, String speActPerson, String speActEmail, LocalDateTime speActDate, LocalDateTime speDeadline, SpeStatus speComplete) {
        this.speId = speId;
        this.speDate = speDate;
        this.spePerson = spePerson;
        this.speEmpNum = speEmpNum;
        this.speEmail = speEmail;
        this.spePart = spePart;
        this.speFacility = speFacility;
        this.speDanger = speDanger;
        this.speInjure = speInjure;
        this.speCause = speCause;
        this.speTrap = speTrap;
        this.speRiskAssess = speRiskAssess;
        this.speContent = speContent;
        this.speActContent = speActContent;
        this.speActPerson = speActPerson;
        this.speActEmail = speActEmail;
        this.speActDate = speActDate;
        this.speDeadline = speDeadline;
        this.speComplete = speComplete;
    }

    // DTO -> entity 변환
    public SpecialInspection createSpeIns(){ return modelMapper.map(this, SpecialInspection.class); }

    // 검색한 결과 리스트 세팅
    public static List<SpeInsFormDTO> listOf(List<SpecialInspection> speFindList) {
        List<SpeInsFormDTO> speInsFormDTOList = new ArrayList<>();
        for (SpecialInspection specialInspection : speFindList) {
            SpeInsFormDTO speInsFormDTO = new SpeInsFormDTO(
                    specialInspection.getSpeId(),
                    specialInspection.getSpeDate(),
                    specialInspection.getSpePerson(),
                    specialInspection.getSpeEmpNum(),
                    specialInspection.getSpeEmail(),
                    specialInspection.getSpePart(),
                    specialInspection.getSpeFacility(),
                    specialInspection.getSpeDanger(),
                    specialInspection.getSpeInjure(),
                    specialInspection.getSpeCause(),
                    specialInspection.getSpeTrap(),
                    specialInspection.getSpeRiskAssess(),
                    specialInspection.getSpeContent(),
                    specialInspection.getSpeActContent(),
                    specialInspection.getSpeActPerson(),
                    specialInspection.getSpeActEmail(),
                    specialInspection.getSpeActDate(),
                    specialInspection.getSpeDeadline(),
                    specialInspection.getSpeComplete()
            );
            speInsFormDTOList.add(speInsFormDTO);
        }
        return speInsFormDTOList;
    }

    public static SpeInsFormDTO of(SpecialInspection specialInspection){
       return modelMapper.map(specialInspection, SpeInsFormDTO.class);
    }

}
