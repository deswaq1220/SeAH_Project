
package SeAH.savg.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class SpecialInspectionRepositoryTest {

    @Autowired
    private SpecialInspectionRepository specialInspectionRepository;

/*    @Test
    public void testSpecialListByPart(){
       List<Object[]> result = specialInspectionRepository.specialListByPartAndMonth(2023, 7);
       for(Object[] res : result) {
           String part = (String) res[0];
           Long count = (Long) res[1];
           System.out.println("파트: "+part+"  "+"점검건수: " +count);
       }
    }*/

/*    @Test
    public void testSpecialListByDanger(){
        List<Object[]> result = specialInspectionRepository.specialListByDangerAndMonthPlus0(2023,7);
        for (Object[] res : result) {
            String dangerKind = (String) res[0];
            Long count = (Long) res[1];
            System.out.println("위험 유형: " + dangerKind + "  점검건수: " + count);
        }
    }*/

/*    @Test
    public void testSpecialDetailListByDangerAndMonth(){
        List<Object[]> result = specialInspectionRepository.specialDetailListByDanger(2023);
        for(Object[] res : result){
            Integer month = (Integer)res[0];
            String dangerKind = (String)res[1];
            Long count = (Long)res[2];
           if(month != null) {
                System.out.println(month + dangerKind + count);
            }else{
                System.out.println("month가 null 입니다");
            }
        }
      
    }
    */
    /*
    @Test
    public void testSpecialCountList(){
        List<Object[]> result = specialInspectionRepository.specialCountList(2023);
        for(Object[] res : result){
            Integer month = (Integer)res[0];
            Long count = (Long)res[1];
                System.out.println("결과: " + month+ ",   " + count);
        }

    }*/

/*    @Test
    public void testSpecialdanger(){
        List<Object[]> result = specialInspectionRepository.specialListByCauseAndMonth(2023,8);
        for(Object[] res : result){
            String cause = (String)res[0];
            Long count = (Long)res[1];
            System.out.println("결과: " + cause + count);
        }
    }*/

/*    @Test
    public void testSpecialCause(){
        List<Object[]> result = specialInspectionRepository.specialListByCauseAndMonth(2023,8);
        for(Object[] res : result){
            String cause = (String)res[0];
            Long count = (Long)res[1];
            System.out.println("결과: " + cause + count);
        }
    }*/




}

