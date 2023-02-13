import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Message {


    private SocialNetworkMember sender;
    public SocialNetworkMember getSender() {
        return sender;
    }
    private Set<SocialNetworkMember> receiverSet = new HashSet<>();
    public Iterable<SocialNetworkMember> getReceiver(){
        return receiverSet;
    }

    private String messageBody;
    public String getMessageBody() {
        return messageBody;
    }

    private LocalDateTime sentAt;
    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public Message(SocialNetworkMember sender, Set<SocialNetworkMember> receiverSet, String messageBody) {
        if (receiverSet.size() == 0)
            throw (new IllegalArgumentException("ReceiverList can't be empty"));
        this.receiverSet = receiverSet;

        this.sender = sender;
        this.messageBody = messageBody;
        this.sentAt = LocalDateTime.now();
    }
}
