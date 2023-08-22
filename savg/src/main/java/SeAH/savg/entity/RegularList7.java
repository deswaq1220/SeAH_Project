package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList7 {
    @Id @Column(name = "regular_7_id")
    private String regular7Id; //위험기계기구 체크리스트 id

    @Column(name = "r_7_list")
    private String r7List; //위험기계기구 체크리스트
}
