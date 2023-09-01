package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class RegularList10 {
    @Id @Column(name = "regular_10_id")
    private String regular10Id; //전기작업 체크리스트 id

    @Column(name = "r_10_list")
    private String r10List; //전기작업 체크리스트
}
