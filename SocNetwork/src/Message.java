import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Message {
    private SocialNetworkMember sender;
    public SocialNetworkMember getSender() {
        return sender;
    }
    private String messageBody;
    public String getMessageBody() {
        return messageBody;
    }

    public Message(SocialNetworkMember sender, String messageBody) {
        this.sender = sender;
        this.messageBody = messageBody;
    }
}
