package SeAH.savg.repository;

import SeAH.savg.entity.SpecialFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeicalFileRepository extends JpaRepository <SpecialFile, Long> {
    List<SpecialFile> findBySpecialInspection_SpeId(String speId);
}
