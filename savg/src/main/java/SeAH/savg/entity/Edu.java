package SeAH.savg.entity;

import SeAH.savg.constant.edustate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Edu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduID;

    @Column(nullable = false)
    private edustate eduCategory;

    @Column(nullable = false)
    private String instructor;

    @Column(nullable = false)
    private String eduPlace;

    @Column(nullable = false)
    private LocalDateTime eduStartTime;

    @Column(nullable = false)
    private LocalDateTime eduEndTime;

    @Column(nullable = false)
    private Integer eduSumTime;

    @Column(nullable = false)
    private edustate eduTarget;

    @Column(nullable = false)
    private String eduContent;

    @Column(nullable = false)
    private String eduWriter;

}
