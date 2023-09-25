package SeAH.savg.repository;

import SeAH.savg.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
//    Login findAllByLoginId(String loginId);

//   @Query("SELECT l.LoginId FROM Login l WHERE a.eduId = :eduId ORDER BY a.attenDepartment ASC, a.attenName ASC")
//   Map<Login> findAllByLoginId(@Param("id") String loginId, @Param("pw") String loginPw);

    Login findByLoginId(String loginId);
}