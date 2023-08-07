package SeAH.savg.repository;

import SeAH.savg.constant.edustate;
import SeAH.savg.entity.Edu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EduRepository extends JpaRepository<Edu, String> {


    // 안전교육 일지 조회 /edumain
    @Query("SELECT e FROM Edu e WHERE YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month")
    List<Edu> findAllByYearAndMonth(@Param("year") int year, @Param("month") int month);



    ////관리자
    //1-1 월 필터링
    @Query("SELECT e.eduTitle, e.eduStartTime, e.eduSumTime, a.attenName, a.attenEmployeeNumber, a.attenDepartment " +
            "FROM Edu e " +
            "JOIN Attendance a " +
            "ON e.eduId = a.eduId " +
            "WHERE YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month")
    List<Object[]> selectMonthStatic(@Param("year") int year, @Param("month") int month);


    //1-2 이름+월 필터링
    @Query("SELECT e.eduTitle, e.eduStartTime, e.eduSumTime, a.attenName, a.attenEmployeeNumber, a.attenDepartment " +
            "FROM Edu e " +
            "JOIN Attendance a " +
            "ON e.eduId = a.eduId " +
            "WHERE YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month AND a.attenName = :name")
    List<Object[]> selectMonthAndName(@Param("year") int year, @Param("month") int month, @Param("name") String name);


    //1-3 월별 교육 참석자 명단 출력(교육 구분)
    @Query("SELECT e.eduTitle, e.eduStartTime, e.eduSumTime, a.attenName, a.attenEmployeeNumber, a.attenDepartment " +
            "FROM Edu e " +
            "JOIN Attendance a " +
            "ON e.eduId = a.eduId " +
            "WHERE e.eduCategory = :eduCategory " +
            "AND YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month")
    List<Object[]> selectMonthEduTraineeStatis(@Param("eduCategory") edustate eduCategory , @Param("year") int year, @Param("month") int month);



 //1-4 월별 교육 참석자 명단 출력(교육 구분 + 부서구분)
    @Query("SELECT e.eduTitle, e.eduStartTime, e.eduSumTime, a.attenName, a.attenEmployeeNumber, a.attenDepartment " +
            "FROM Edu e " +
            "JOIN Attendance a " +
            "ON e.eduId = a.eduId " +
            "WHERE e.eduCategory = :eduCategory " +
            "AND YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month AND a.attenDepartment = :department")
    List<Object[]> eduTraineeStatisByDepart(@Param("eduCategory") edustate eduCategory ,@Param("year") int year, @Param("month") int month, @Param("department") String department);

    //1-5 월별 교육 참석자 명단 출력(교육 구분 + 성명구분)
    @Query("SELECT e.eduTitle, e.eduStartTime, e.eduSumTime, a.attenName, a.attenEmployeeNumber, a.attenDepartment " +
            "FROM Edu e " +
            "JOIN Attendance a " +
            "ON e.eduId = a.eduId " +
            "WHERE e.eduCategory = :eduCategory " +
            "AND YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month AND a.attenName = :name")
    List<Object[]> eduTraineeStatisByName(@Param("eduCategory") edustate eduCategory ,@Param("year") int year, @Param("month") int month, @Param("name") String name);

    //1-6 월별 교육  참석자 명단 출력(교육구분 + 부서 + 이름)
    @Query("SELECT e.eduTitle, e.eduStartTime, e.eduSumTime, a.attenName, a.attenEmployeeNumber, a.attenDepartment " +
            "FROM Edu e " +
            "JOIN Attendance a " +
            "ON e.eduId = a.eduId " +
            "WHERE e.eduCategory = :eduCategory " +
            "AND YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month " +
            "And a.attenDepartment = :department " +
            "AND a.attenName = :name")
    List<Object[]> eduTraineeStatisByNameAndDepart(@Param("eduCategory") edustate eduCategory , @Param("year") int year, @Param("month") int month, @Param("name") String name, @Param("department") String department);


    //2-1. 월별 교육시간 조회하기(월별 교육실시시간 총계)
    @Query("SELECT e.eduCategory, e.eduSumTime " +
            "FROM Edu e " +
            "WHERE YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month")
    List<Object[]> selectSumMonthEduTime(@Param("year") int year ,@Param("month") int month);

    //2-2. 월별 교육시간 조회하기(월별 교육실시시간 총계(카테고리, 월 기재버전))
    @Query("SELECT e.eduSumTime " +
            "FROM Edu e " +
            "WHERE e.eduCategory = :eduCategory " +
            "AND YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month ")
    List<Object[]> selectMonthEduTimeList(@Param("eduCategory") edustate eduCategory, @Param("year") int year,@Param("month") int month);


    //3. 월별 교육실행목록 조회하기(eduStartTime이 빠른 순으로 정렬)
    @Query("SELECT e.eduCategory, e.eduTitle, e.eduStartTime, e.eduSumTime " +
            "FROM Edu e " +
            "WHERE YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month " +
            "ORDER BY e.eduStartTime ASC")
    Page<Object[]> selectRunMonthEduList(@Param("year") int year, @Param("month") int month, Pageable pageable);

   //4. 월별 교육실행목록 조회하기(category별)
   @Query("SELECT e.eduCategory, e.eduTitle, e.eduStartTime, e.eduSumTime " +
           "FROM Edu e " +
           "WHERE YEAR(e.eduStartTime) = :year AND MONTH(e.eduStartTime) = :month " +
           "AND e.eduCategory = :eduCategory " +
           "ORDER BY e.eduStartTime ASC")
   List<Object[]> runMonthEduListByCategory(@Param("year") int year ,@Param("month") int month, @Param("eduCategory") edustate eduCategory);


    Edu findByEduId(String eduId);

    // DB에서 id들 들고와서 '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음) 가장 큰 숫자 구하기
    @Query("select MAX(substring(e.eduId, 7)) from Edu e where substring(e.eduId,2,4) =? 1")
    int findAllByMaxSeq(String todayYearAndMonth);

}
