package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList5 {
    @Id @Column(name = "regular_5_id")
    private String regular5Id; //이동장비(크레인) 체크리스트 id

    @Column(name = "r_5_list")
    private String r5List; //이동장비(크레인) 체크리스트
}
