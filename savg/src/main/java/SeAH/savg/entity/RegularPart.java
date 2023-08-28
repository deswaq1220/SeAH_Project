package SeAH.savg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter @ToString
public class RegularPart {
    @Id
    private String partMenu; //영역분류

    @Column(nullable = false)
    private int partNum; //순서
}
