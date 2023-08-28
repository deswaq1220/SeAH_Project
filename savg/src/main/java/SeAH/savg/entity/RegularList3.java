package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList3 {
    @Id @Column(name = "regular_3_id")
    private String regular3Id; //추락예방 체크리스트 id

    @Column(name = "r_3_list")
    private String r3List; //추락예방 체크리스트
}
