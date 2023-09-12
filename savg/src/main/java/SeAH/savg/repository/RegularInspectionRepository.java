package SeAH.savg.repository;

import SeAH.savg.entity.*;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
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
    @Query("SELECT r FROM RegularInspection r WHERE YEAR(r.regularDate)=:year AND MONTH(r.regularDate) =:month")
    List<RegularInspection> findAllByRegularDate(@Param("year") int year, @Param("month") int month);

    RegularInspection findByRegularIdOrderByRegularDate(String regularId);

    //정기점검 목록 조회(현황조회 페이지)
    @Query("SELECT r.regularPart, r.regularInsName, r.regularDate, r.regularPerson, c.regularCheck FROM RegularInspection r " +
            "LEFT JOIN RegularInspectionCheck c ON r.regularId = c.regularInspection.regularId " +
            "WHERE r.regularDate BETWEEN :regularStartDate AND :regularEndDate " +
            "ORDER BY r.regularDate DESC")
    List<Object[]> findPresentRegularList(Predicate predicate);

}