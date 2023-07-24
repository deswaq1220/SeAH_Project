package SeAH.savg.entity;

import SeAH.savg.constant.MasterStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "email")
@Getter @Setter
public class Email {
    @Id
    @Column(name = "email_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;          // 이메일아이디
    private String emailPart;      // 영역
    private String emailName;      // 이름
    private String emailAdd;       // 이메일주소
    @Enumerated(EnumType.STRING)
    private MasterStatus masterStatus = MasterStatus.N;        // 고정수신여부 : Y / N

    @ManyToOne
    @JoinColumn(name = "spe_id")
    private SpecialInspection speIn;
}
