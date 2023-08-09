package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @ToString
public class SpecialDanger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String dangerMenu;       // 위험분류

    private int dangerNum;           // 순서

}
