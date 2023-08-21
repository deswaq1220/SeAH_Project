package SeAH.savg.service;

import SeAH.savg.repository.MasterDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;

@SpringBootTest
public class MasterServiceTest {

    @Autowired
    private MasterDataService masterDataService;

    @Autowired
    private MasterDataRepository masterDataRepository;

/*    @Test
    public void testSortByPart(){

        masterDataService.sortByPart("[주조]");
        System.out.println(masterDataService.sortByPart("[주조]"));
    }*/
}
