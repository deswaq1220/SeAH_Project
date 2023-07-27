//package SeAH.savg.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface RegularInspectionRepository extends JpaRepository<RegularInspection, String> {
//
//   // DB에서 id들 들고와서 '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음) 가장 큰 숫자 구하기
//    @Query("select MAX(substring(r.regId, 7)) from RegularInspection r where substring(r.regId,2,4) =? 1")
//    int findAllByMaxSeq(String todayYearAndMonth);
//}
