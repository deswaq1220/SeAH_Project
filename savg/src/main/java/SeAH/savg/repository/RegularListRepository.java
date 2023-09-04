package SeAH.savg.repository;


import SeAH.savg.entity.RegularList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegularListRepository extends JpaRepository<RegularList, String> {
    List<RegularList> findByRegularNum(int num);

}
