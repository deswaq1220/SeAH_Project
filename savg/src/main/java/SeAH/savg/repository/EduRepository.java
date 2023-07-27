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

    // DB에서 id들 들고와서
    // todayYearAndMonth랑 들고온 아이디들 '-'기준으로 잘라서 앞에 네개가 같으면
    // '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음)
    // 그 다음 숫자를 seqNumber로 세팅
//    @Query("select MAX(substring(e.eduId, 7)) from Edu e where substring(e.eduId,2,4) =? 1")
//    int findAllByMaxSeq(String todayYearAndMonth);
}
