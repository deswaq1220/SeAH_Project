package SeAH.savg.Email;

public class EmailData {

    private String recipient;
    private String subject;
    private String content;
    private String fixedContent;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFixedContent() {
        return fixedContent;
    }

    public void setFixedContent(String fixedContent) {
        this.fixedContent = fixedContent;
    }


}
