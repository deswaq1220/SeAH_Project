package SeAH.savg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "special_inspection_part")
@Getter
@Setter
@ToString
public class SpecialPartINFO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long partId; // id

    @Column(nullable = false)
    private String partKind; //영역분류


}
