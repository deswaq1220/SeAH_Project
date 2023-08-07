package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @ToString
public class SpecialCause {
    @Id
    private String causeMenu;       // 원인

    private int causeNum;           // 순서

}
