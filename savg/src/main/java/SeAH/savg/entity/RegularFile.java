package SeAH.savg.entity;

import SeAH.savg.constant.RegStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @ToString @Setter
public class RegularFile {

    @Id
    private Long regularFileId;   //id

    private String regularFileName; //파일명
    private String regularOriName; //원본 파일 명
    private String regularFileUrl; //파일 저장 경로
    private RegStatus isComplete; //완료여부

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regular_id")
    private RegularInspection regularInspection;
}
