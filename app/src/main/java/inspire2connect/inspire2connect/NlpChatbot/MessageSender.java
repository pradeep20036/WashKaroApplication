package inspire2connect.inspire2connect.NlpChatbot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageSender {
    @POST("webhook")
    Call<List<BotResponse>> messageSender1(@Body MessageClass userMessage);
}
