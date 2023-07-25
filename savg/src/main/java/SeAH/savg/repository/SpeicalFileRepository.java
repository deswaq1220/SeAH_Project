package SeAH.savg.repository;

import SeAH.savg.entity.SpecialFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeicalFileRepository extends JpaRepository <SpecialFile, Long> {
}
