package SeAH.savg.entity;

import SeAH.savg.constant.RegStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class RegularInspectionCheck {

    @Id
    private String regularCheckId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegStatus regularCheck;           // 점검내용: 위험성 확인결과 양호=GOOD, 불량=BAD, NA=NA

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regular_id")
    private RegularInspection regularInspection;


}
