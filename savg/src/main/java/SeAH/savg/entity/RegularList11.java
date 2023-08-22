package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList11 {
    @Id @Column(name = "regular_11_id")
    private String regular11Id; //태풍 풍수해대비점검 체크리스트 id

    @Column(name = "r_11_list")
    private String r11List; //태풍 풍수해대비점검 체크리스트
}
