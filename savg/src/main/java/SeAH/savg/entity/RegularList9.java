package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList9 {
    @Id @Column(name = "regular_9_id")
    private String regular9Id; //밀폐공간(제한구역) 체크리스트 id

    @Column(name = "r_9_list")
    private String r9List; //밀폐공간(제한구역) 체크리스트
}
