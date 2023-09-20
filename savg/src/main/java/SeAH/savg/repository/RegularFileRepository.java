package SeAH.savg.repository;

import SeAH.savg.entity.RegularFile;
import SeAH.savg.entity.RegularInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegularFileRepository extends JpaRepository<RegularFile, Long> {
    List<RegularFile> findByRegularInspection(RegularInspection regularInspection);

    @Query("SELECT i.regularFileUrl FROM RegularFile i " +
            "WHERE i.regularCheckId = :regularCheckId AND i.regularInspection = :regularInspection AND i.isComplete = :isComplete")
    List<String> getRegularFileName(
            @Param("regularCheckId") String regularCheckId,
            @Param("regularInspection") RegularInspection regularInspection,
            @Param("isComplete") String isComplete
    );

    // 외래키로 찾기
    List<RegularFile> findByRegularInspectionRegularId(String regId);

    // 오늘 날짜에 해당하는 파일 이름들을 조회
    @Query("SELECT r FROM RegularFile r WHERE r.regularFileName LIKE CONCAT('%', ?1, '%')")
    List<RegularFile> findFilesByToday(String todayDate);

    // 오늘 날짜가 바뀌면 seqNumber를 0으로 초기화하고, 그대로면 seqNumber 중 최대값가져오기
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(r.regularFileName, '_', 2),'_',-1) AS integer)), 0) FROM RegularFile r WHERE SUBSTRING(r.regularFileName, 1, 8) = ?1")
    int getMaxSeqNumberByToday(String todayDate);
}
