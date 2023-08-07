package SeAH.savg.constant;

import SeAH.savg.dto.SpeInsFormDTO;

public enum SpeStatus {

    // 수시점검 영역
    CAST,            //주조
    EXTRUSION,       //압출
    MANUFACTURE,     //가공
    QUALITY,         //품질
    PRODUC_TECH,     //생산기술
    MOLD,            //금형

    //  조치완료 : 위험성평가 enum 클래스 어떻게 구성되느냐에따라 나중에 따로 해야할 수도있음
    NO,             // 기본값
    OK,              // 조치완료

    // 위험성평가
    HIGH,           // 고위험
    MEDIUM,         // 중위험
    LOW;             // 저위험



    // 위험도에 따른 완료요청기한 계산
    public static void deadLineCal(SpeInsFormDTO speInsFormDTO){
        if(speInsFormDTO.getSpeRiskAssess() == HIGH)           // 고위험 +10일
            speInsFormDTO.setSpeDeadline(speInsFormDTO.getSpeDate().plusDays(10));
        else if(speInsFormDTO.getSpeRiskAssess() == MEDIUM)    // 중위험 +30일
            speInsFormDTO.setSpeDeadline(speInsFormDTO.getSpeDate().plusDays(30));
        else if(speInsFormDTO.getSpeRiskAssess() == LOW)       // 저위험 +60일
            speInsFormDTO.setSpeDeadline(speInsFormDTO.getSpeDate().plusDays(60));

    }
}
