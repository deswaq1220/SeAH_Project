package SeAH.savg.repository;

import SeAH.savg.dto.TokenDto;
import SeAH.savg.dto.TokenRequestDto;
import SeAH.savg.entity.RefreshToken;
import SeAH.savg.jwt.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key);
}

