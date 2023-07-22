package SeAH.savg.service;

import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.MasterData;
import SeAH.savg.repository.MasterDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterDataService {
    private final MasterDataRepository masterDataRepository;

    // 기준정보 조회
    @Transactional(readOnly = true)
    public List<MasterData> findAll() {
        List<MasterData> masterDataList = masterDataRepository.findAll();
        return masterDataList;
    }


    @Transactional
    // 기준정보 등록
    public MasterData saveMaster(MasterDataFormDTO masterDataFormDTO){
        MasterData masterData = masterDataFormDTO.createMaster();
        masterDataRepository.save(masterData);

        return masterData;
    }

//    public int saveMaster(MasterDataFormDTO masterDataFormDTO){
//        // 기준정보 등록
//        MasterData masterData = masterDataFormDTO.createMaster();
//        masterDataRepository.save(masterData);
//
//        return  masterData.getMasterdataId();
//    }

}
