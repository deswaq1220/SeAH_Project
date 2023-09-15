package SeAH.savg.repository;

import SeAH.savg.dto.SpecialFileFormDTO;
import SeAH.savg.entity.SpecialFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeicalFileRepository extends JpaRepository <SpecialFile, Long> {
    // speId로 파일정보 찾기(DTO)
    List<SpecialFileFormDTO> findBySpecialInspection_SpeId(String speId);

    // 외래키로 찾기
    List<SpecialFile> findBySpecialInspectionSpeId(String speId);

    // 오늘 날짜에 해당하는 파일 이름들을 조회
    @Query("SELECT s FROM SpecialFile s WHERE s.speFileName LIKE CONCAT('%', ?1, '%')")
    List<SpecialFile> findFilesByToday(String todayDate);

    // 오늘 날짜가 바뀌면 seqNumber를 0으로 초기화하고, 그대로면 seqNumber 중 최대값가져오기
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(s.speFileName, '_', 2),'_',-1) AS integer)), 0) FROM SpecialFile s WHERE SUBSTRING(s.speFileName, 1, 8) = ?1")
    int getMaxSeqNumberByToday(String todayDate);


    // fileId로 찾기
    @Query("SELECT sf.speFileName FROM SpecialFile sf WHERE sf.speFileId =? 1")
    String findSpeFileNameBySpeFileId(Long speFileId);

}

