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

}
