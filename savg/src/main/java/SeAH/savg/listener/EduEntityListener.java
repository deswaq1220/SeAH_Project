package SeAH.savg.listener;

import SeAH.savg.dto.EduDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.service.EduService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import javax.persistence.PostUpdate;
@Slf4j
//@RequiredArgsConstructor
public class EduEntityListener {

    private EduService eduService;
    private ApplicationContext applicationContext;

    @PostUpdate
    public void eduPostUpdate(Edu edu){

    }


}
