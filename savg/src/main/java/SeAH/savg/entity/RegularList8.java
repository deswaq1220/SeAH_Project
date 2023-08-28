package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList8 {
    @Id @Column(name = "regular_8_id")
    private String regular8Id; //지붕작업 체크리스트 id

    @Column(name = "r_8_list")
    private String r8List; //지붕작업 체크리스트
}
