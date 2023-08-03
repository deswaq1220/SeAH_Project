package SeAH.savg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "special_inspection_danger")
@Getter
@Setter
@ToString
public class SpecialDangerINFO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dangerId; // id

    @Column(nullable = false)
    private String dangerKind; //위험분류


}
