package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @ToString
public class RegularName {

    @Id
    private String regularInsName;       // 정기점검 종류

    @Column(nullable = false)
    private int regularNum;           // 순서

}
