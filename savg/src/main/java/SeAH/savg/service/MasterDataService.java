package SeAH.savg.service;

import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.MasterData;
import SeAH.savg.repository.MasterDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    // 기준정보 등록
    @Transactional
    public MasterData saveMaster(MasterDataFormDTO masterDataFormDTO){
        MasterData masterData = MasterData.createMaster(masterDataFormDTO);
        System.out.println(masterData);
        masterDataRepository.save(masterData);

        return masterData;
    }

    // 기준정보 삭제
    @Transactional
    public void deleteMaster(Integer masterdataId){
        masterDataRepository.deleteById(masterdataId);
    }


}
