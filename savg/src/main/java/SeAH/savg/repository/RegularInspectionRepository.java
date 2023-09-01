package SeAH.savg.repository;

import SeAH.savg.entity.RegularInspection;
import SeAH.savg.entity.RegularName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegularInspectionRepository extends JpaRepository<RegularInspection, String> {

    //월간
        //정기점건 건수
        @Query("SELECT COUNT(r) " +
                "FROM RegularInspection r " +
                "WHERE YEAR(r.regularDate) = :year AND MONTH(r.regularDate) = :month")
        int regularCountByMonth(@Param("year") int year, @Param("month") int month);

        //

    // id: 생성 - DB에서 id들 들고와서 '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음) 가장 큰 숫자 구하기
    @Query("select MAX(substring(r.regularId, 7)) from RegularInspection r where substring(r.regularId,2,4) =? 1")
    int findAllByMaxSeq(String todayYearAndMonth);

    //연간
        //(LineChart) 1~12월 총 수시점검 건수(정기점검 테이블 생기면 가동)
        @Query("SELECT COUNT(r) " +
                "FROM RegularInspection r " +
                "WHERE YEAR(r.regularDate) = :year ")
        int regularCountByYear(@Param("year") int year);

        //(LineChart) 전체 월별(1~12월) 정기점검 건수
        @Query("SELECT MONTH(r.regularDate), COALESCE(COUNT(r), 0) " +
                "FROM RegularInspection r " +
                "WHERE YEAR(r.regularDate) = :year " +
                "GROUP BY MONTH(r.regularDate)")
        List<Object[]> regularCountList(@Param("year") int year);

/*        //1~12월 종류별 정기점검 점검건수 통계
        @Query("SELECT MONTH(r.speDate), r.regularInsName, COUNT(r) " +
                "FROM RegularInspection r " +
                "WHERE YEAR(r.speDate) = :year " +
                "GROUP BY r.regularInsName, MONTH(r.speDate)")
        List<Object[]> regularDetailListByName(@Param("year") int year);*/


    //정기점검 항목 불러오기(중대재해, 작업장 일반.. 등등)
    @Query("SELECT r.regularInsName FROM RegularName r ORDER BY r.regularNum")
    List<String> regularInsNameList();

    //정기점검 항목 불러오기(중대재해, 작업장 일반.. 등등)
    @Query("SELECT rp.partMenu FROM RegularPart rp ORDER BY rp.partNum")
    List<String> regularPartList();



    //정기점검 항목에 따른 체크리스트 불러오기
    @Query("SELECT r.r1List FROM RegularList1 r")
    List<String> regular1List(); // 중대재해일반점검
    @Query("SELECT r.r2List FROM RegularList2 r")
    List<String> regular2List(); //작업장 일반
    @Query("SELECT r.r3List FROM RegularList3 r")
    List<String> regular3List(); //추락예방
    @Query("SELECT r.r4List FROM RegularList4 r")
    List<String> regular4List(); //이동장비_지게차트럭
    @Query("SELECT r.r5List FROM RegularList5 r")
    List<String> regular5List(); //이동장비_크레인
    @Query("SELECT r.r6List FROM RegularList6 r")
    List<String> regular6List(); //LOTO
    @Query("SELECT r.r7List FROM RegularList7 r")
    List<String> regular7List(); //위험기계기구
    @Query("SELECT r.r8List FROM RegularList8 r")
    List<String> regular8List(); //지붕작업
    @Query("SELECT r.r9List FROM RegularList9 r")
    List<String> regular9List(); //밀폐공간(제한구역)
    @Query("SELECT r.r10List FROM RegularList10 r")
    List<String> regular10List(); //전기작업
    @Query("SELECT r.r11List FROM RegularList11 r")
    List<String> regular11List(); //태풍풍수해대비점검

    //정기점검 목록 조회
    @Query("SELECT r FROM RegularInspection r WHERE YEAR(r.regularDate)=:year AND MONTH(r.regularDate) =:month")
    List<RegularInspection> findAllByRegularDate(@Param("year") int year, @Param("month") int month);


    RegularInspection findByRegularIdOrderByRegularDate(String regularId);

}