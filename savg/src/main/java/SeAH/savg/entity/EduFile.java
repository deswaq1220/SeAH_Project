package SeAH.savg.entity;

import SeAH.savg.dto.EduDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name="edu_file")
@Getter @Setter
public class EduFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduFileId;           // id
    private String eduFileName;       // 파일명
    private String eduFileOriName;    // 원본 파일명
    private String eduFileUrl;        // 파일 조회 경로


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "edu_id")
    private Edu edu;

    public void updateEduFile(String eduFileName, String eduFileOriName, String eduFileUrl){
        this.eduFileName = eduFileName;
        this.eduFileOriName = eduFileOriName;
        this.eduFileUrl = eduFileUrl;
    }


}
