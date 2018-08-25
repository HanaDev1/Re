package remoty.internship.wadimakkah.remotyapplication;

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private long messageTime;
    private String designerEmail;
    private String designerFullName;

    public String getDesignerEmail() {
        return designerEmail;
    }

    public void setDesignerEmail(String designerEmail) {
        this.designerEmail = designerEmail;
    }

    public String getDesignerFullName() {
        return designerFullName;
    }

    public void setDesignerFullName(String designerFullName) {
        this.designerFullName = designerFullName;
    }

    public ChatMessage(String messageText, String DesignerEmail,String DesignerFullName, String messageUser) {
        this.designerEmail = DesignerEmail;
        this.designerFullName = DesignerFullName;
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
