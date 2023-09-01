package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList1 {
    @Id @Column(name = "regular_1_id")
    private String regular1Id; //중대재해예방일반점검 체크리스트 id

    @Column(name = "r_1_list")
    private String r1List; //중대재해예방일반점검 체크리스트
}
