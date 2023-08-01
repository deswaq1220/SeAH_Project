package SeAH.savg.repository;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.entity.SpecialInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    @Query("SELECT COUNT(s) FROM SpecialInspection s WHERE DATE_FORMAT(s.speDate, '%Y-%m-%d') = DATE_FORMAT(?1, '%Y-%m-%d') AND s.speComplete = ?2 AND s.speId IS NOT NULL AND s.speDate > ?3")
    int countBySpeDateAndSpeCompleteAndSpeIdIsNotNullAndSpeDateAfter(LocalDateTime speDate, SpeStatus speComplete, LocalDateTime startOfToday);

}
