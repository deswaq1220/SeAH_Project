package SeAH.savg.repository;

import SeAH.savg.entity.RegularInspection;
import SeAH.savg.entity.RegularInspectionCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularCheckRepository extends JpaRepository<RegularInspectionCheck, String> {


    RegularInspectionCheck

}
