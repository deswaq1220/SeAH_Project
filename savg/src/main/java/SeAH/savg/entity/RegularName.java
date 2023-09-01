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
    private String regularInsName;       // 정기점검 구분

    @Column(nullable = false)
    private int regularNum;           // 순서

    @Column(nullable = false)
    private String regularId; //아이디
}
