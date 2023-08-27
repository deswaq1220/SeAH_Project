package SeAH.savg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "special_part")
@Getter
@Setter
@ToString
public class SpecialPart {

    @Id
    private String partMenu; //영역분류

    @Column(nullable = false)
    private int partNum; //순서


}
