package SeAH.savg.service;

import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.MasterData;
import SeAH.savg.repository.EmailRepository;
import SeAH.savg.repository.MasterDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MasterDataService {
    private final MasterDataRepository masterDataRepository;
    private final EmailRepository emailRepository;

    //기준정보 영역 드롭다운 노출
    public List<String> findSpecialPartList(){


       List<String> specialPartList = masterDataRepository.partMenuList();

        return specialPartList;
    }

    // 기준정보(설비리스트) 조회
    public List<MasterData> findAllFacilities() {
        List<MasterData> facilityList = masterDataRepository.findAll();
        return facilityList;
    }

    // 기준정보(이메일) 조회
    public List<Email> findAllEmail() {
        List<Email> emailList = emailRepository.findAll();
        return emailList;
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
