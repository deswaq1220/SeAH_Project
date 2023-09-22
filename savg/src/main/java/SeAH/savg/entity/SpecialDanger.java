package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @ToString
public class SpecialDanger {
    @Id
    private String dangerMenu;       // 위험분류
    @Column(nullable = false)
    private int dangerNum;           // 순서

}
