package SeAH.savg.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @ToString
public class RegularInspectionKind {

    @Id
    private String regularSpeKind;       // 정기점검 구분

    @Column(nullable = false)
    private int regularSpeNum;           // 순서

}
