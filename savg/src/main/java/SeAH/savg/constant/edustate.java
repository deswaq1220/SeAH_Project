package SeAH.savg.constant;

public enum edustate {

    //교육 분류
    CREW, //크루미팅
    MANAGE, //관리감독자
    DM, //DM미팅
    ETC, //기타


    //교육대상 분류
    T,  //total 전체
    F, //filed 현장
    O; //office 사무직

    public boolean isEmpty() {
        return this == null || this == T;
    }


}
