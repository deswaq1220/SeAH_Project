package SeAH.savg.entity;

import SeAH.savg.constant.SpeStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="special_file")
@Getter @Setter @ToString
public class SpecialFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long speFileId;           // id
    private String speFileName;       // 파일명
    private String speFileOriName;    // 원본 파일명
    private String speFileUrl;        // 파일 조회 경로
    @Enumerated(EnumType.STRING)
    private SpeStatus isComplete;     // 완료여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spe_id")
    private SpecialInspection specialInspection;

    public void updateSpeFile(String speFileName, String speFileOriName, String speFileUrl){
        this.speFileName = speFileName;
        this.speFileOriName = speFileOriName;
        this.speFileUrl = speFileUrl;
    }
}
