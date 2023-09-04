package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList6 {
    @Id @Column(name = "regular_6_id")
    private String regular6Id; //LOTO 체크리스트 id

    @Column(name = "r_6_list")
    private String r6List; //LOTO 체크리스트
}
