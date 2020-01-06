import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {
    String senderName = "";
    String content = "";
    LocalTime time = LocalTime.now();
    Message(String senderName, String content) {
        this.senderName = senderName;
        this.content = content;
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
