package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList2 {
    @Id @Column(name = "regular_2_id")
    private String regular2Id; //작업장 일반 체크리스트 id

    @Column(name = "r_2_list")
    private String r2List; //작업장 일반 체크리스트
}
