package SeAH.savg.constant;

import SeAH.savg.dto.SpeInsFormDTO;

public enum SpeStatus {

    // 조치여부
    NO,             // 조치전
    OK,             // 조치완료

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
