package SeAH.savg.entity;

import SeAH.savg.constant.MasterStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name = "email")
@Getter @Setter @ToString
public class Email {
    @Id
    @Column(name = "email_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;          // 이메일아이디

    @Column(nullable = false)
    private String emailPart;      // 영역

    @Column(nullable = false)
    private String emailName;      // 이름

    @Column(nullable = false)
    private String emailAdd;       // 이메일주소

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MasterStatus masterStatus = MasterStatus.N;        // 고정수신여부 : Y / N


}
