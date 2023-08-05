package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @ToString
public class SpecialCause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int causeId;  // id

    private String causeMenu;
}
