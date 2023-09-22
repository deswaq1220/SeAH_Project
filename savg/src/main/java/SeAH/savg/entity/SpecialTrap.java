package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @ToString
public class SpecialTrap {
    @Id
    private String trapMenu;       // 실수함정
    @Column(nullable = false)
    private int trapNum;           // 순서

}
