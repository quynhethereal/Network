import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {
    String senderName = "";
    String content;
    LocalTime time = LocalTime.now();

    //FIXME: this may not be needed?
    Message(String senderName, String content) {
        this.senderName = senderName;
        this.content = content;
    }

    Message (String content){
        this.content = content;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    String getSenderName() {
        return this.senderName;
    }

    String getContent() {
        return this.content;
    }

    String getTime() {
        return this.time.toString();
    }
}
