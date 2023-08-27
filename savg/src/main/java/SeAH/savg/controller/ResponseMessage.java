package SeAH.savg.controller;

public class ResponseMessage {
    //여기는 이메일용~~
    private String message;
    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
