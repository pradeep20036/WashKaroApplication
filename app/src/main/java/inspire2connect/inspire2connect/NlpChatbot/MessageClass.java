package inspire2connect.inspire2connect.NlpChatbot;

public class MessageClass {
    String message="";
    int sender=0;

    public MessageClass(String message, int sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
}
