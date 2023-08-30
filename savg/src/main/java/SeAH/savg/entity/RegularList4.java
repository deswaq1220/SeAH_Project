package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList4 {
    @Id @Column(name = "regular_4_id")
    private String regular4Id; //이동장비(지게차트럭) 체크리스트 id

    @Column(name = "r_4_list")
    private String r4List; //이동장비(지게차트럭) 체크리스트
}
