package SeAH.savg.service;

import SeAH.savg.Exception.DuplicateCodeException;
import SeAH.savg.dto.MasterDataDepartmentDTO;
import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.MasterData;
import SeAH.savg.entity.MasterDataDepartment;
import SeAH.savg.repository.EmailRepository;
import SeAH.savg.repository.MasterDataDepartmentRepository;
import SeAH.savg.repository.MasterDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
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


        // 기준정보(이메일) 삭제
        @Transactional
        public void deleteEmail(Long emailId){
            System.out.println("서비스 이메일 아이디"+ emailId);
            emailRepository.deleteById(emailId);
    }

        // 기준정보 등록 : 영역당 설비
        @Transactional
        public MasterData saveMaster(MasterDataFormDTO masterDataFormDTO) {
            String frontCode = String.valueOf(masterDataFormDTO.getMasterdataId());

            if (masterDataRepository.existsByMasterdataId(frontCode)) {
                // 이미 존재하는 코드이므로 오류 메시지를 반환하거나 예외를 던지는 등의 처리가 필요합니다.
                throw new DuplicateCodeException("기존 코드 정보와 중복됩니다. 다른 코드를 사용하세요.");
            } else {
                MasterData masterData = MasterData.createMaster(masterDataFormDTO);
                System.out.println(masterData);
                masterDataRepository.save(masterData);
                return masterData;
            }

        }

        // 기준정보 삭제
        @Transactional
        public void deleteMaster(String masterdataId){
            masterDataRepository.deleteByMasterdataId(masterdataId);
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

        //부서별 목록 조회(카테고리용) -부서1 기준
        public List<MasterDataDepartmentDTO> sortDepartList(String dept1){

            List<MasterDataDepartmentDTO> resultList = masterDataDepartmentRepository.findByFirstDepartment(dept1);

            resultList.sort(Comparator
                                      .comparing((MasterDataDepartmentDTO dept) -> !dept.getFirstDepartment().equals(dept.getSecondDepartment()))
            );
            return resultList;
        }

        //부서 등록하기
        @Transactional
        public MasterDataDepartmentDTO saveDepart(MasterDataDepartmentDTO departmentDTO){

            //성공
            try {
                //MasterDataDepartment entity = departmentDTO.toEntity(); //dto를 엔티티로 변환
                MasterDataDepartment resultList = MasterDataDepartmentDTO.createMasterDepart(departmentDTO);
                masterDataDepartmentRepository.save(resultList);

            //실패
            } catch (Exception e) {
                e.printStackTrace();    //예외정보 출력
                log.error("부서등록 에러발생:  ", e.getMessage()); //로그남김
            }

            return departmentDTO;
        }

        //부서 삭제하기
       @Transactional
       public void delDepart(String depart2){
           try{
            masterDataDepartmentRepository.deleteById(depart2);
            System.out.println("삭제성공");
           } catch (Exception e) {
               log.error("삭제실패");
           }
       }

        //부서 수정하기
        @Transactional
        public MasterDataDepartmentDTO updateDepart(String depart2, MasterDataDepartmentDTO departmentDTO){ //depart2: 수정해야하는 값, departmentDTO: 수정 값
            try{

                Optional<MasterDataDepartment> target = masterDataDepartmentRepository.findById(depart2);

                //변경값이 존재할 경우
                if(target.isPresent()){

                    String firstDepartment = departmentDTO.getFirstDepartment();
                    String secondDepartment = departmentDTO.getSecondDepartment();

                    masterDataDepartmentRepository.updateDepartment(depart2, firstDepartment, secondDepartment);

                    log.info("수정 성공");

                  //변경값이 없을 경우
                } else{
                    log.info("수정 실패");
                }


                return departmentDTO;

            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return null;
        }

}
