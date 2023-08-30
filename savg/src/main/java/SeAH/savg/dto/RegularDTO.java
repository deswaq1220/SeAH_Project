package SeAH.savg.dto;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.entity.RegularInspectionBad;
import SeAH.savg.entity.RegularInspectionCheck;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter @Setter
public class RegularDTO {
    private String regularId;
    private LocalDateTime regularDate;          // 관찰일
    private String regularPerson;               // 관찰자
    private String regularEmpNum;               // 관찰자 사원번호
    private String regularPart;                 // 점검구역(주조, 압출 등)
    private String regularInsName;             //점검항목(중대재해, LOTO 등 )

    public static ModelMapper modelMapper = new ModelMapper();

    public RegularInspection createRegular() {
        RegularInspection regularInspection = modelMapper.map(this, RegularInspection.class);

        return regularInspection;
    }

}
