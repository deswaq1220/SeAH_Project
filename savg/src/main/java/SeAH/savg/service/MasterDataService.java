package SeAH.savg.service;

import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.MasterData;
import SeAH.savg.repository.MasterDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterDataService {
    private final MasterDataRepository masterDataRepository;

    public int saveMaster(MasterDataFormDTO masterDataFormDTO){
        // 기준정보 등록
        MasterData masterData = masterDataFormDTO.createMaster();
        masterDataRepository.save(masterData);

        return  masterData.getMasterdataId();
    }
}
