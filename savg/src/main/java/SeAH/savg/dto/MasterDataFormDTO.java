package SeAH.savg.dto;

import SeAH.savg.entity.MasterData;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class MasterDataFormDTO {
    private int masterdataId;                // id
    private String masterdataPart;           // 영역
    private String masterdataFacility;       // 설비
    private static ModelMapper modelMapper = new ModelMapper();
    public MasterData createMaster(){
        return modelMapper.map(this, MasterData.class);
    }
    public static MasterDataFormDTO of (MasterData masterData){
        return modelMapper.map(masterData, MasterDataFormDTO.class);
    }
}
