package SeAH.savg.repository;

import SeAH.savg.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegularFileRepository extends JpaRepository<RegularFile, Long> {
    List<RegularFile> findByRegularInspection(RegularInspection regularInspection);

    @Query("SELECT i.regularFileUrl FROM RegularFile i " +
            "WHERE i.regularCheckId = :regularCheckId AND i.regularInspection = :regularInspection")
    List<String> getRegularFileName(
            @Param("regularCheckId") String regularCheckId,
            @Param("regularInspection") RegularInspection regularInspection
    );

}
