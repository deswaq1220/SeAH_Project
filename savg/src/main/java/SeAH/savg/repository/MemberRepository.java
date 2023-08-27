package SeAH.savg.repository;

import SeAH.savg.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email); // 1
    Optional<Member> findByNickname(String nickname); // 2

    boolean existsByEmail(String email); // 3
    boolean existsByNickname(String nickname); // 4
}