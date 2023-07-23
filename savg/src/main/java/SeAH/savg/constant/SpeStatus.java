package SeAH.savg.constant;

public enum SpeStatus {
    // 위험성평가
    HIGH,           // 고위험
    MEDIUM,         // 중위험
    LOW,             // 저위험


    // 조치완료 : 위험성평가 enum 클래스 어떻게 구성되느냐에따라 나중에 따로 해야할 수도있음
    NO,             // 기본값
    OK              // 조치완료
}
