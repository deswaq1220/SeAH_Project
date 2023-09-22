package SeAH.savg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @ToString @Setter
public class RegularFile extends BaseTimeEntity{

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long regularFileId;   //id

    private String regularFileName; //파일명
    private String regularOriName; //원본 파일 명
    private String regularFileUrl; //파일 저장 경로
    private String isComplete; //완료여부

    private String regularCheckId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regular_id")
    private RegularInspection regularInspection;
}
