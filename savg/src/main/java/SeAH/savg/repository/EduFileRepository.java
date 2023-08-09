package SeAH.savg.repository;

import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EduFileRepository extends JpaRepository<EduFile, Long> {


    Optional<EduFile> findByEduFileName(String fileName);

    List<EduFile> findByEdu(Edu edu);


}
