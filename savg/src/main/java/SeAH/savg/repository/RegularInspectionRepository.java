package SeAH.savg.repository;

import SeAH.savg.constant.RegStatus;

import SeAH.savg.entity.RegularInspection;
import com.querydsl.core.types.Predicate;

import SeAH.savg.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegularInspectionRepository extends JpaRepository<RegularInspection, String>, QuerydslPredicateExecutor<RegularInspection> {

    // id: 생성 - DB에서 id들 들고와서 '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음) 가장 큰 숫자 구하기
    @Query("select MAX(substring(r.regularId, 7)) from RegularInspection r where substring(r.regularId,2,4) =? 1")
    int findAllByMaxSeq(String todayYearAndMonth);


    //정기점검 항목 불러오기(중대재해, 작업장 일반.. 등등)
    @Query("SELECT r.regularInsName FROM RegularName r ORDER BY r.regularNum")
    List<String> regularInsNameList();

    //정기점검 항목 불러오기(주조/압출 등 )
    @Query("SELECT rp.partMenu FROM RegularPart rp ORDER BY rp.partNum")
    List<String> regularPartList();

    //정기점검 목록 조회
    @Query("SELECT r FROM RegularInspection r WHERE YEAR(r.regTime)=:year AND MONTH(r.regTime) =:month")
    List<RegularInspection> findAllByRegularDate(@Param("year") int year, @Param("month") int month);

    RegularInspection findByRegularIdOrderByRegTime(String regularId);

    //정기점검 현황 조회(조치완료여부)
    @Query("SELECT b.regularComplete, c.regularInspection.regularId FROM RegularInspectionBad b " +
            "LEFT JOIN RegularInspectionCheck c ON b.regularInspectionCheck.regularCheckId = c.regularCheckId " +
            "WHERE c.regularInspection.regularId = :regularId")
    RegStatus findPresentRegularYN();

        //RegularCheck테이블 중 모두 NO인 것 추출(정기점검 현황조회)
        @Query("SELECT r.regularInspection.regularId FROM RegularInspectionCheck r " +
                "INNER JOIN RegularInspectionBad b ON b.regularInspectionCheck.regularCheckId = r.regularCheckId " +
                "AND b.regularComplete = 'NO' ")
        List<String> findByRegularCheckAllComplete();





    // monthly: 전체 점검 건수 (점검실시)
    @Query("SELECT COUNT(ri.regularId) FROM RegularInspection ri " +
            "WHERE DATE_FORMAT(ri.regTime, '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m')")
    int countByRegTime();

    // monthly: 조치완료건수 (조치완료)
    @Query("SELECT count(ri) FROM RegularInspection ri " +
            "WHERE ri.regularComplete =? 1 " +
            "AND DATE_FORMAT(ri.updateTime, '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m')")
    int findRegularInspectionsCompletedToday(RegStatus regStatus);

    // monthly: 이번달 불량건수

}