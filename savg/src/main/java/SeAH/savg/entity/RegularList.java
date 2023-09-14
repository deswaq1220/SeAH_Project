package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList {
    @Id @Column(name = "regular_id")
    private String regularId; // 체크리스트 id

    @Column()
    private String regularList; //  체크리스트

    @Column()
    private int regularNum; //  체크리스트
}
