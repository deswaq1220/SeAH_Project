package SeAH.savg.repository;

import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduStatisticsDTO;
import SeAH.savg.entity.Edu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EduRepository extends JpaRepository<Edu, String> {

    List<Edu> findAll(Sort sort);


   ////관리자
    //1. 월별 교육통계 조회하기(교육별 참석자 명단 출력)
    @Query("SELECT e.eduCategory, e.eduTitle, e.eduStartTime, e.eduSumTime, a.attenName, a.attenEmployeeNumber, a.attenDepartment " +
            "FROM Edu e " +
            "JOIN Attendance a " +
            "ON e.eduId = a.eduId " +
            "WHERE e.eduCategory = :eduCategory " +
            "AND MONTH(e.eduStartTime) = :month")
    List<Object[]> selectMonthEduTraineeStatis(@Param("eduCategory") edustate eduCategory , @Param("month") int month);

    //2. 월별 교육통계 조회하기(월별 교육실시시간 총계)
    @Query("SELECT e.eduSumTime " +
            "FROM Edu e " +
            "WHERE e.eduCategory = :eduCategory " +
            "AND MONTH(e.eduStartTime) = :month ")
    List<Object[]> selectMonthEduTimeList(@Param("eduCategory") edustate eduCategory, @Param("month") int month);

    @Query("SELECT e.eduCategory, e.eduSumTime " +
            "FROM Edu e " +
            "WHERE MONTH(e.eduStartTime) = :month")
    List<Object[]> selectSumMonthEduTime(@Param("month") int month);

    //월별 교육실행목록 조회하기(eduStartTime이 빠른 순으로 정렬)
    @Query("SELECT e.eduCategory, e.eduTitle, e.eduStartTime, e.eduSumTime " +
            "FROM Edu e " +
            "WHERE MONTH(e.eduStartTime) = :month " +
            "ORDER BY e.eduStartTime ASC")
    Page<Object[]> selectRunMonthEduList(@Param("month") int month, Pageable pageable);

   //월별 교육실행목록 조회하기(category별)
   @Query("SELECT e.eduCategory, e.eduTitle, e.eduStartTime, e.eduSumTime " +
           "FROM Edu e " +
           "WHERE MONTH(e.eduStartTime) = :month " +
           "AND e.eduCategory = :eduCategory " +
           "ORDER BY e.eduStartTime ASC")
   Page<Object[]> runMonthEduListByCategory(@Param("month") int month, @Param("eduCategory") edustate eduCategory, Pageable pageable);

    Edu findByEduId(Long eduId);

    // DB에서 id들 들고와서 '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음) 가장 큰 숫자 구하기
    @Query("select MAX(substring(e.eduId, 7)) from Edu e where substring(e.eduId,2,4) =? 1")
    int findAllByMaxSeq(String todayYearAndMonth);

}
