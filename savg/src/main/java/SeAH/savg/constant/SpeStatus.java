package SeAH.savg.constant;

import SeAH.savg.dto.SpeInsFormDTO;

public enum SpeStatus {
    //  조치완료 : 위험성평가 enum 클래스 어떻게 구성되느냐에따라 나중에 따로 해야할 수도있음
    NO,             // 기본값
    OK,              // 조치완료

    // 위험성평가
    HIGH,           // 고위험
    MEDIUM,         // 중위험
    LOW;             // 저위험



    // 위험도에 따른 완료요청기한 계산
    public static void deadLineCal(SpeInsFormDTO speInsFormDTO){
        if(speInsFormDTO.getSpeRiskAssess() == SpeStatus.HIGH)
            speInsFormDTO.setSpeDeadline(speInsFormDTO.getSpeDate().plusDays(10));
        else if(speInsFormDTO.getSpeRiskAssess() == SpeStatus.MEDIUM)
            speInsFormDTO.setSpeDeadline(speInsFormDTO.getSpeDate().plusDays(30));
        else if(speInsFormDTO.getSpeRiskAssess() == SpeStatus.LOW)
            speInsFormDTO.setSpeDeadline(speInsFormDTO.getSpeDate().plusDays(60));

    }
}
