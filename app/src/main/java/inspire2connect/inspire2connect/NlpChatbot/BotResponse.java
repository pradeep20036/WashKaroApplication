package inspire2connect.inspire2connect.NlpChatbot;

public class BotResponse {
    String reciptent ="";
    String text="";

    public BotResponse(String reciptent, String text) {
        this.reciptent = reciptent;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReciptent() {
        return reciptent;
    }

    public void setReciptent(String reciptent) {
        this.reciptent = reciptent;
    }
}
