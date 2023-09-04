package SeAH.savg.repository;

import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.entity.RegularFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegularFileRepository extends JpaRepository<RegularFile, Long> {

}
