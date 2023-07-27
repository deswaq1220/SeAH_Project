package SeAH.savg.repository;

import SeAH.savg.entity.Edu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EduRepository extends JpaRepository<Edu, String> {

    List<Edu> findAll(Sort sort);

    Edu findByEduId(Long eduId);

    // DB에서 id들 들고와서 '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음) 가장 큰 숫자 구하기
    @Query("select MAX(substring(e.eduId, 7)) from Edu e where substring(e.eduId,2,4) =? 1")
    int findAllByMaxSeq(String todayYearAndMonth);
}
