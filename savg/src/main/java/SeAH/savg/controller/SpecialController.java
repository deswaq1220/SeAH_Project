package SeAH.savg.controller;

import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.repository.SpecialInspectionRepository;
import SeAH.savg.service.SpecialInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://172.20.20.252:3000")  // 세아

public class SpecialController {
 private final SpecialInspectionService specialInspectionService;
 private final SpecialInspectionRepository specialInspectionRepository;

 // ------------ 사용자 ------------------------------------------


  // 월별 수시점검현황: 수시점검 qr찍고 첫번째 페이지
  @GetMapping("/special/{masterdataPart}/{masterdataFacility}")
  public ResponseEntity<?> speMonthly() {
      return new ResponseEntity<>(specialInspectionService.findSpeMonthly(), HttpStatus.OK);
  }


  // 설비별 현황 조회: 수시점검 qr찍고 두번째 페이지
  @GetMapping("/special/list/{masterdataPart}/{masterdataFacility}")
  public ResponseEntity<?> speListOfFac(@PathVariable String masterdataFacility) {
      return new ResponseEntity<>(specialInspectionService.findListOfFac(masterdataFacility), HttpStatus.OK);
  }


  // 수시점검 저장화면(조회)
  @GetMapping("/special/new/{masterdataPart}/{masterdataFacility}")
  public ResponseEntity<?> speForm(@PathVariable String masterdataPart) {    // @PathVariable String masterdataFacility로 파라미터 받아서 해도될것같은디
      return new ResponseEntity<>(specialInspectionService.findCreateMenu(masterdataPart), HttpStatus.OK);
  }


 // 수시점검 저장
 @PostMapping("/special/new/{masterdataPart}/{masterdataFacility}")
 public ResponseEntity<?> speNew(@PathVariable String masterdataPart,
                                 @PathVariable String masterdataFacility,
                                 SpeInsFormDTO speInsFormDTO) throws Exception{
  return new ResponseEntity<>(specialInspectionService.speCreate(masterdataPart, masterdataFacility, speInsFormDTO), HttpStatus.CREATED);
 }


 // 상세조회
 @GetMapping("/special/detail/{speId}")
 public ResponseEntity<Map<String, Object>> getSpecialDetail(@PathVariable String speId) {
   Map<String, Object> detailMap = specialInspectionService.getSpecialDetail(speId);
   if (detailMap != null) {
    return new ResponseEntity<>(detailMap, HttpStatus.OK);
   } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
 }


 // 완료처리 / 수정(update)
 @PostMapping("/special/detail/{speId}")
 public ResponseEntity<?> speComplete(@PathVariable String speId, SpeInsFormDTO speInsFormDTO) throws Exception {
  return new ResponseEntity<>(specialInspectionService.speUpdate(speId, speInsFormDTO), HttpStatus.CREATED);
 }

// -------------------------- 공통


    // 수시저검 등록된 전체 현황
    @GetMapping("/master/spcial/list")
    public ResponseEntity<?> speFullList() {
        return new ResponseEntity<>(specialInspectionService.findSpeAll(), HttpStatus.OK);
    }


// --------------------------- 관리자

    //월간 수시점검 건수(완료, 미완료 모두 포함_)
    @GetMapping("/special/statistics/count")
    public ResponseEntity<?> speCount(@RequestParam("yearmonth") String yearMonth){
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));
        int speCount = specialInspectionRepository.countBySpeList(year, month);
        return ResponseEntity.ok(speCount);
    }
    /* 월간 수시점검 현황 통계 조회 - 점검영역별
     * 형태: 파트(주조, 압출, 가공, 품질, 생산기술, 금형) + 점검건수 리스트
     * ex : 주조 1건, 압출 2건 ...
     */
    @GetMapping("/special/statistics/partandmonth")
    public ResponseEntity<?> getSpecialListByPartAndMonth(@RequestParam("yearmonth") String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));

        List<Map<String, Object>> statisticsList = specialInspectionService.setSpecialListByPartAndMonth(year, month);


        return ResponseEntity.ok(statisticsList);
    }

    /* (엑셀용) 월간 수시점검 현황 통계 조회 - 점검영역별
     * 형태: 파트(주조, 압출, 가공, 품질, 생산기술, 금형) + 점검건수 리스트
     * ex : 주조 1건, 압출 2건 ...
     */
    @GetMapping("/special/statistics/partandmonthforexcel")
    public ResponseEntity<?> getSpecialListByPartAndMonthForExcel(@RequestParam("yearmonth") String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));

        List<Object[]> statisticsList = specialInspectionRepository.specialListByPartAndMonth(year, month);

        return ResponseEntity.ok(statisticsList);
    }

    /* 월간 수시점검 현황 통계 조회 - 위험분류별
     * 형태: 위험분류(추락,협착,끼임,말림,전도,절단,베임,찔림,충돌,화상,화재폭발,근골격,지게차,크레인,누출,환경사고,기타) + 점검건수 리스트
     * ex : 추락 1건, 기타 2건 ...
     */
    @GetMapping("/special/statistics/dangerandmonth")
    public ResponseEntity<List<Object[]>> getSpecialListByDangerAndMonth(@RequestParam("yearmonth") String yearMonth) {
        //List<Map<String, Object>> statisticsList = specialInspectionService.setSpecialListByDangerAndMonth(year, month); - 그래프용 지금안씀(안쓸경우 삭제)

        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));
        List<Object[]> statisticsList = specialInspectionRepository.specialListByDangerAndMonth(year, month);

        return ResponseEntity.ok(statisticsList);
    }

     /* 1~12월 내 발생한 월별 수시점검 현황 통계 조회 - 위험분류별
      * 형태: 위험분류(추락,협착,끼임,말림,전도,절단,베임,찔림,충돌,화상,화재폭발,근골격,지게차,크레인,누출,환경사고,기타) + 점검건수 리스트
      * ex : 1월 추락 1건, 기타 2건 ...
      */
     @GetMapping("/special/statistics/detaildanger")
     public ResponseEntity<List<Map<String, Object>>> getDetailSpecialListByDanger(@RequestParam("year") int year) {
      List<Map<String, Object>> statisticsList = specialInspectionService.specialDetailListByDanger(year);
      return ResponseEntity.ok(statisticsList);
  }



  /* 월간 수시점검 현황 통계 조회 - 위험원인별(0건까지 나옴)
   * 형태: 위험원인(설비원인,작업방법,점검불량,정비불량,지식부족,불안전한 행동,기타(직접입력)) + 점검건수 리스트
   * ex : 설비원인 1건, 작업방법 2건 ...
   */
/*  @GetMapping("/special/statistics/causeandmonth")
  public ResponseEntity<List<Object[]>> getSpecialListBySpecauseAndMonth(@RequestParam("yearmonth") String yearMonth) {
      int year = Integer.parseInt(yearMonth.substring(0, 4));
      int month = Integer.parseInt(yearMonth.substring(5, 7));
      List<Object[]> statisticsList = specialInspectionRepository.specialListByCauseAndMonth(year, month);
      return ResponseEntity.ok(statisticsList);
  }*/


    /* 월간 수시점검 현황 통계 조회 - 위험원인별(0건까지 나옴) - 기타값 포함
     * 형태: 위험원인(설비원인,작업방법,점검불량,정비불량,지식부족,불안전한 행동,기타(직접입력)) + 점검건수 리스트
     * ex : 설비원인 1건, 작업방법 2건 ...
     */
    @GetMapping("/special/statistics/causeandmonth")
    public ResponseEntity<List<Object[]>> getgetSpecialListBySpecauseAndMonth(@RequestParam("yearmonth") String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));
        List<Object[]> statisticsList = specialInspectionService.specialDetailListByCauseAndMonth(year, month);
        return ResponseEntity.ok(statisticsList);
    }



  /* 월별 수시점검 현황 통계 조회 - 실수함정별
   * 형태: 실수함정(N/A,자만심,시간압박,미흡한 의사소통,주의산만,처음작업,비일상작업,과중한업무부하,불명확한 작업지시,4일이상 업무공백,근무교대 시점) + 점검건수 리스트
   * ex : N/A 1건, 자만심 2건 ...
   */
  @GetMapping("/special/statistics/spetrapandmonth")
  public ResponseEntity<List<Object[]>> getSpecialListBySpeTrapAndMonth(@RequestParam("month") int month) {
      List<Object[]> statisticsList = specialInspectionRepository.specialListBySpeTrapAndMonth(month);

      return ResponseEntity.ok(statisticsList);
  }


  /* 월간 수시점검 현황 통계 조회 - 부상부위별
   * 형태: 실수함정(신체,머리,팔,다리,가슴,등/허리,안면,기타(직접입력) + 점검건수 리스트
   * ex : 신체 1건, 머리 2건 ...
   */
  @GetMapping("/special/statistics/speinjureandmonth")
  public ResponseEntity<List<Object[]>> getSpecialListBySpeInjureAndMonth(@RequestParam("month") int month) {
      List<Object[]> statisticsList = specialInspectionRepository.specialListBySpeInjureAndMonth(month);

      return ResponseEntity.ok(statisticsList);
  }

  /* 월별 수시점검 현황 통계 조회 - 위험성 평가별
   * 형태: 고위험, 중위험, 저위험 + 점검건수 리스트
   * ex : 고위험 1건, 중위험 2건 ...
   */
/*  @GetMapping("/special/statistics/speriskassessdmonth")
  public ResponseEntity<List<Object[]>> getSpecialListBySpeRiskAssessAndMonth ( @RequestParam("month") int month){
      List<Object[]> statisticsList = specialInspectionRepository.specialListBySpeRiskAssessAndMonth(month);

      return ResponseEntity.ok(statisticsList);
  }*/

    /* 1~12월 내 발생한 수시점검 건수*/
    @GetMapping("/special/statistics/yearcount")
    public ResponseEntity<?> getSpecialCountByYear(@RequestParam("year") int year) {
        int statisticsCount = specialInspectionRepository.specialCountByYear(year);

        return ResponseEntity.ok(statisticsCount);
    }
}


