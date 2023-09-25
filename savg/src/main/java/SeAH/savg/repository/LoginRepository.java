package SeAH.savg.repository;

import SeAH.savg.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginRepository extends JpaRepository<Login, String> {

    Login findByLoginId(String loginId);
}