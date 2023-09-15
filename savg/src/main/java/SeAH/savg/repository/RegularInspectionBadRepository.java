package SeAH.savg.repository;

import SeAH.savg.entity.RegularInspectionBad;
import SeAH.savg.entity.RegularInspectionCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularInspectionBadRepository extends JpaRepository<RegularInspectionBad, Long> {

RegularInspectionBad findByRegularInspectionCheck(RegularInspectionCheck regularInspectionCheck);

}