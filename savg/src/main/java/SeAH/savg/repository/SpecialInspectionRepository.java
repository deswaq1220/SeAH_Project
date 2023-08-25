package SeAH.savg.repository;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.entity.SpecialInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpecialInspectionRepository extends JpaRepository<SpecialInspection, String>, QuerydslPredicateExecutor<SpecialInspection> {
    // 설비로 찾기: 내림차순 정렬
    List<SpecialInspection> findAllBySpeFacilityOrderBySpeDateDesc(String facility);


    // id: 생성 - DB에서 id들 들고와서 '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음) 가장 큰 숫자 구하기
    @Query("select MAX(substring(s.speId, 7)) from SpecialInspection s where substring(s.speId,2,4) =? 1")
    int findAllByMaxSeq(String todayYearAndMonth);

    // 아이디로 찾기
    SpecialInspection findAllBySpeId(String speId);

    // monthly: 전체 점검 건수 (점검실시)
    @Query("SELECT COUNT(s.speId) FROM SpecialInspection s " +
            "WHERE DATE_FORMAT(s.speDate, '%Y-%m') = DATE_FORMAT(?1, '%Y-%m')")
    int countAllBySpeDateAndSpeIdIsNotNullSpeDateAfter(LocalDateTime startOfToday);

    // monthly: 완료 갯수 (조치완료)
    @Query("SELECT COUNT(s) FROM SpecialInspection s " +
            "WHERE DATE_FORMAT(s.speActDate, '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m') " +
            "AND s.speComplete = ?1 ")
    int countBySpeActDateAndSpeComplete(SpeStatus speComplete, LocalDateTime startOfToday);


    // monthly: 이번달 deadline 중 미완료건수 (조치필요)
    @Query("SELECT COUNT(s) FROM SpecialInspection s " +
            "WHERE DATE_FORMAT(s.speDeadline, '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m')" +
            "AND s.speComplete =? 1")
    int countBySpeDeadlineAndSpeComplete(SpeStatus speComplete);

    // --------------------------- 공통
    // 등록된 전체 수시점검 조회
//    List<SpecialInspection> findAllOrderBySpeIdDesc();



    // --------------------------- 관리자
    //통계
    //월
    //월간 수시점검 통계 조회 - 수시점검 건수
    @Query("SELECT COUNT(s) FROM SpecialInspection s WHERE YEAR(s.speDate) = :year AND MONTH(s.speDate) = :month ")
    int countBySpeList(@Param("year") int year, @Param("month") int month);

    //월간 수시점검 통계 조회 - 영역별 건 수(0건인 것들도 함께 나옴)
    @Query("SELECT p.partMenu, COALESCE(COUNT(s.spePart), 0) " +
            "FROM SpecialPart p " +
            "LEFT JOIN SpecialInspection s ON s.spePart = p.partMenu AND YEAR(s.speDate) = :year AND MONTH(s.speDate) = :month " +
            "GROUP BY p.partMenu")
    List<Object[]> specialListByPartAndMonth(@Param("year") int year, @Param("month") int month);


    //월간 수시점검 통계 조회 - 위험분류 발생 건 수(0건인 것들도 함께 나옴)
    @Query("SELECT d.dangerMenu, COALESCE(COUNT(s), 0) " +
            "FROM SpecialDanger d " +
            "LEFT JOIN SpecialInspection s ON s.speDanger = d.dangerMenu AND YEAR(s.speDate) = :year AND MONTH(s.speDate) = :month " +
            "GROUP BY d.dangerMenu")
    List<Object[]> specialListByDangerAndMonth(@Param("year") int year, @Param("month") int month);



    //월간 수시점검 통계 조회 - 위험원인별 발생 건 수(0건인 것들도 함께 나옴)
    @Query("SELECT c.causeMenu, COALESCE(COUNT(s), 0) " +
            "FROM SpecialCause c " +
            "LEFT JOIN SpecialInspection s ON s.speCause = c.causeMenu AND YEAR(s.speDate) = :year AND MONTH(s.speDate) = :month " +
            "GROUP BY c.causeMenu")
    List<Object[]> specialListByCauseAndMonth(@Param("year") int year, @Param("month") int month);


    //월간 수시점검 통계 조회 - 실수함정별 발생 건 수
    @Query("SELECT s.speTrap, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.speTrap")
    List<Object[]> specialListBySpeTrapAndMonth(@Param("month") int month);


    //월간 수시점검 통계 조회 - 부상부위별 발생 건 수
    @Query("SELECT s.speInjure, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.speInjure")
    List<Object[]> specialListBySpeInjureAndMonth(@Param("month") int month);


/*    //월간 수시점검 통계 조회 - 위험성 평가별 발생 건 수
    @Query("SELECT c.causeMenu, COALESCE(COUNT(s), 0) " +
            "FROM Special c " +
            "LEFT JOIN SpecialInspection s ON s.speCause = c.causeMenu AND YEAR(s.speDate) = :year AND MONTH(s.speDate) = :month " +
            "GROUP BY c.causeMenu")
    List<Object[]> specialListBySpeRiskAssessAndMonth(@Param("month") int month);*/


    //연
    //연간(1~12월) 총 수시점검 건수
    @Query("SELECT COUNT(s) " +
            "FROM SpecialInspection s " +
            "WHERE YEAR(s.speDate) = :year ")
    int specialCountByYear(@Param("year") int year);

    //전체 월별(1~12월) 수시점검 건수
    @Query("SELECT MONTH(s.speDate), COALESCE(COUNT(s), 0) " +
            "FROM SpecialInspection s " +
            "WHERE YEAR(s.speDate) = :year " +
            "GROUP BY MONTH(s.speDate)")
    List<Object[]> specialCountList(@Param("year") int year);

    //전체 월별 위험 발생 분류 건수
    @Query("SELECT MONTH(s.speDate), s.speDanger, COALESCE(COUNT(s), 0) " +
            "FROM SpecialInspection s " +
            "WHERE YEAR(s.speDate) = :year " +
            "GROUP BY s.speDanger, MONTH(s.speDate)")
    List<Object[]> specialDetailListByDanger(@Param("year") int year);

    /*    @Query("SELECT i.dangerKind, MONTH(s.speDate), COALESCE(COUNT(s), 0) " +
            "FROM SpecialDangerINFO i " +
            "LEFT JOIN SpecialInspection s ON s.speDanger = i.dangerKind AND YEAR(s.speDate) = :year " +
            "GROUP BY i.dangerKind, MONTH(s.speDate)")
    List<Object[]> specialDetailListByDangerAndMonth(@Param("year") int year);*/


}
