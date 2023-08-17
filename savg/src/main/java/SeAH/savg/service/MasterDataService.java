package SeAH.savg.service;

import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.MasterData;
import SeAH.savg.entity.MasterDataDepartment;
import SeAH.savg.repository.EmailRepository;
import SeAH.savg.repository.MasterDataDepartmentRepository;
import SeAH.savg.repository.MasterDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MasterDataService {
    private final MasterDataRepository masterDataRepository;
    private final MasterDataDepartmentRepository masterDataDepartmentRepository;
    private final EmailRepository emailRepository;


    //------------------------------------------설비 관리

        //기준정보 영역 드롭다운 노출
        public List<String> findSpecialPartList(){

           List<String> specialPartList = masterDataRepository.partMenuList();

            return specialPartList;
        }

        //기준정보 카테고리(영역) 선택 조회
        public List<String[]> sortByPart(String part){

            List<String[]> facilityListbyPart = masterDataRepository.sortByPart(part);

            return facilityListbyPart;
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


    //------------------------------------------부서 관리

        //부서 전체 목록 조회
        public List<MasterDataDepartment> DepartList(){
            List<MasterDataDepartment> resultList = masterDataDepartmentRepository.findAll();

            resultList.sort(Comparator
                                      .comparing((MasterDataDepartment dept) -> !dept.getFirstDepartment().equals(dept.getSecondDepartment()))
                                      .thenComparing(MasterDataDepartment::getFirstDepartment)
                                      .thenComparing(MasterDataDepartment::getSecondDepartment)

            );
            return resultList;
        }


}
