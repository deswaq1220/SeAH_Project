package SeAH.savg.repository;

import SeAH.savg.entity.RegularInspectionBad;
import SeAH.savg.entity.RegularInspectionCheck;
import SeAH.savg.entity.RegularName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegularInspectionBadRepository extends JpaRepository<RegularInspectionBad, String> {

RegularInspectionBad findByRegularInspectionCheck(RegularInspectionCheck regularInspectionCheck);
}