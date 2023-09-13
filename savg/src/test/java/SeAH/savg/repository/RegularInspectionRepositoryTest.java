
package SeAH.savg.repository;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)

public class RegularInspectionRepositoryTest {

    @Autowired
    private RegularInspectionRepository regularInspectionRepository;

/*    @Test
    public void testAllCompleteList(){
       List<String> result = regularInspectionRepository.findByRegularCheckAllComplete();

        for(String row : result){
            System.out.println(row);
        }
    }*/




}

