package SeAH.savg.service;

import SeAH.savg.dto.MasterDataDepartmentDTO;
import SeAH.savg.repository.MasterDataDepartmentRepository;
import SeAH.savg.repository.MasterDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class MasterServiceTest {

    @Autowired
    private MasterDataService masterDataService;

    @Autowired
    private MasterDataRepository masterDataRepository;

    @Autowired
    private MasterDataDepartmentRepository masterDataDepartmentRepository;

/*    @Test
    public void testSortByPart(){

        masterDataService.sortByPart("[주조]");
        System.out.println(masterDataService.sortByPart("[주조]"));
    }*/

/*    @Test
    public void testSortByDepartment(){
        List<MasterDataDepartmentDTO> result = masterDataDepartmentRepository.findByFirstDepartment("영업부");

        System.out.println(result);
    }*/

    //부서 등록하기
/*    @Test
    public void testSaveDepart(){
        MasterDataDepartmentDTO dto = new MasterDataDepartmentDTO();
        dto.setFirstDepartment("[품질부]");
        dto.setSecondDepartment("[품질부]");

        masterDataService.saveDepart(dto);
    }*/

    //부서 수정하기
    @Test
    public void testUpdateDepart(){

        MasterDataDepartmentDTO departmentDTO = new MasterDataDepartmentDTO();
        departmentDTO.setFirstDepartment("[영업부]");
        departmentDTO.setSecondDepartment("[영업부수정22]");

        masterDataService.updateDepart("[품질부]", departmentDTO);
    }


}
