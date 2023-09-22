package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @ToString
public class SpecialInjured {
    @Id
    private String injuredMenu;       // 부상부위
    @Column(nullable = false)
    private int injuredNum;           // 순서

}
