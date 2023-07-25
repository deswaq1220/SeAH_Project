package SeAH.savg.repository;

import SeAH.savg.entity.Edu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EduRepository extends JpaRepository<Edu, Long> {

    List<Edu> findAll(Sort sort);

    Edu findByEduId(Long eduId);
}
