package SeAH.savg.repository;

import SeAH.savg.entity.SpecialInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialInspectionRepository extends JpaRepository<SpecialInspection, String> {
    // 설비로 찾기
    List<SpecialInspection> findAllBySpeFacility(String masterdataFacility);

    // DB에서 id들 들고와서 '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음) 가장 큰 숫자 구하기
    @Query("select MAX(substring(s.speId, 7)) from SpecialInspection s where substring(s.speId,2,4) =? 1")
    int findAllByMaxSeq(String todayYearAndMonth);

    // 아이디로 찾기
    SpecialInspection findAllBySpeId(String speId);

    // daily 미완료 갯수
//    @Query("SELECT COUNT(s) FROM SpecialInspection s WHERE DATE_FORMAT(s.speDate, '%Y-%m-%d') = DATE_FORMAT(?1, '%Y-%m-%d') AND s.speComplete = ?1 AND s.speId IS NOT NULL AND s.speDate > ?2")
//    int countBySpeDateAndSpeCompleteAndSpeIdIsNotNullAndSpeDateAfter(SpeStatus speComplete, LocalDateTime startOfToday);

//    int countBySpeId();

    // daily 완료 갯수


    //월별 수시점검 통계 조회 - 영역별 건수
    @Query("SELECT s.spePart, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.spePart")
    List<Object[]> specialListByPartAndMonth(@Param("month") int month);

    //월별 수시점검 통계 조회 - 위험분류 발생건수
    @Query("SELECT s.speDanger, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.speDanger")
    List<Object[]> specialListByDangerAndMonth(@Param("month") int month);

}
