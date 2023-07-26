package SeAH.savg.repository;

import SeAH.savg.entity.SpecialInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialInspectionRepository extends JpaRepository<SpecialInspection, Long> {
    List<SpecialInspection> findAllBySpeFacility(String masterdataFacility);
    List<String> findBySpeId();
}
