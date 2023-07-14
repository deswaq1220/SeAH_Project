package SeAH.savg.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="edu_file")
@Getter @Setter
public class EduFile {
    @Id
    private Long eduFileId;           // id
    private String eduFileName;       // 파일명
    private String eduFileOriName;    // 원본 파일명
    private String eduFileUrl;        // 파일 조회 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_id")
    private Edu edu;
}