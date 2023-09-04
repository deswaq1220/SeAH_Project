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
    private String masterdataId;                // id

    @Column(nullable = false)
    private String masterdataPart;           // 영역

    @Column(nullable = false)
    private String masterdataFacility;       // 설비


    @Builder
    MasterData(String masterdataId, String masterdataPart, String masterdataFacility) {
        this.masterdataId = masterdataId;
        this.masterdataPart = masterdataPart;
        this.masterdataFacility = masterdataFacility;
    }

    public static MasterData createMaster(MasterDataFormDTO masterDataFormDTO) {
        return MasterData.builder()
                .masterdataId(masterDataFormDTO.getMasterdataId())
                .masterdataPart(masterDataFormDTO.getMasterdataPart())
                .masterdataFacility(masterDataFormDTO.getMasterdataFacility())
                .build();
    }


}

