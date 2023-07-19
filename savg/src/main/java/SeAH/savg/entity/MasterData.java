package SeAH.savg.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name="master_data")
@Getter @Setter
public class MasterData {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int masterdataId;                // id
    private String masterdataPart;           // 영역
    private String masterdataFacility;       // 설비

    /*
    private String qrName;                   // qr 파일명
    private String qrOriName;                // qr 원본파일명
    private String qrUrl;                    // qr 파일 조회 경로
    */



}
