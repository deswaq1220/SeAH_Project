package SeAH.savg.repository;

import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.entity.SpecialFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EduFileRepository extends JpaRepository<EduFile, Long> {


    Optional<EduFile> findByEduFileName(String fileName);

    List<EduFile> findByEdu(Edu edu);

    // 오늘 날짜에 해당하는 파일 이름들을 조회
    @Query("SELECT e FROM EduFile e WHERE e.eduFileName LIKE CONCAT('%', ?1, '%')")
    List<EduFile> findFilesByToday(String todayDate);

    // 오늘 날짜가 바뀌면 seqNumber를 0으로 초기화하고, 그대로면 seqNumber 중 최대값가져오기
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(s.eduFileName, '_', 2),'_',-1) AS integer)), 0) FROM EduFile s WHERE SUBSTRING(s.eduFileName, 1, 8) = ?1")
    int getMaxSeqNumberByToday(String todayDate);



}
