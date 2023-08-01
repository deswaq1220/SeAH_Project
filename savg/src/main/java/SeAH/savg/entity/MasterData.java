package SeAH.savg.entity;

import SeAH.savg.dto.MasterDataFormDTO;
import lombok.*;

import javax.persistence.*;

@Entity @Table(name="master_data")
@Getter @Setter
@ToString
@NoArgsConstructor
public class MasterData {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int masterdataId;                // id

    @Column(nullable = false)
    private String masterdataPart;           // 영역

    @Column(nullable = false)
    private String masterdataFacility;       // 설비

    /*
    private String qrName;                   // qr 파일명
    private String qrOriName;                // qr 원본파일명
    private String qrUrl;                    // qr 파일 조회 경로
    */

    @Builder
    MasterData(int masterdataId, String masterdataPart, String masterdataFacility) {
        this.masterdataId = masterdataId;
        this.masterdataPart = masterdataPart;
        this.masterdataFacility = masterdataFacility;
    }

    public static MasterData createMaster(MasterDataFormDTO masterDataFormDTO) {
        return MasterData.builder()
                .masterdataPart(masterDataFormDTO.getMasterdataPart())
                .masterdataFacility(masterDataFormDTO.getMasterdataFacility())
                .build();
    }


}
