package SeAH.savg.repository;

import SeAH.savg.dto.AttendanceDTO;
import SeAH.savg.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    //@Modifying
    //@Transactional
    //@Query("update Attendance a set a.atten_department = :employee_department, a.atten_name = :employee_name, a.atten_employee_number = :employee_number")
    //AttendanceDTO attend(@Param("employee_department") String attend_employee_department
    //                     ,@Param("employee_name") String attend_employee_name
    //                     ,@Param("employee_number") String attend_employee_number);


}