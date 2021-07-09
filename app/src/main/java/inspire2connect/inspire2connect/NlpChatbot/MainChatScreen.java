package inspire2connect.inspire2connect.NlpChatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import inspire2connect.inspire2connect.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainChatScreen extends AppCompatActivity {
    FloatingActionButton sendbutton;
    RecyclerView recyclerView;
    EditText messagebox;
    private final int USER = 0;
    private final int BOT = 1;
    MessageAdapter adapter;
    private ArrayList<MessageClass> messageClassArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_screen);
        messageClassArrayList = new ArrayList<>();
        sendbutton = findViewById(R.id.sendbutton);
        messagebox = findViewById(R.id.messagebox);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new MessageAdapter(MainChatScreen.this,messageClassArrayList);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

////        pradeep testing purpose
//        String msg="what is TB?";
//        messagebox.setText("");
//        sendMessage(msg);

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = messagebox.getText().toString();
//                String msg="what is TB?";
                messagebox.setText("");

                if(msg !="")
                {
                    sendMessage(msg);

                    Toast.makeText(MainChatScreen.this,"Message sent",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainChatScreen.this,"Please write your query!",Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
    public void sendMessage(String message)
    {
        MessageClass temp = null;
        if(message.trim().isEmpty()){
            Toast.makeText(MainChatScreen.this,"Please write your query!",Toast.LENGTH_SHORT).show();
        }
        else{
            temp = new MessageClass(message,USER);
            messageClassArrayList.add(temp);
            adapter.notifyDataSetChanged();

        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://9b210f7d74f4.ngrok.io/webhooks/rest/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MessageSender messageSender = retrofit.create(MessageSender.class);
        Call<List<BotResponse>> response = messageSender.messageSender1(temp);

        response.enqueue(new Callback<List<BotResponse>>() {
            @Override
            public void onResponse(Call<List<BotResponse>> call, Response<List<BotResponse>> response) {
                Log.d("CHECK","at line response");
                Log.d("CHECK","response"+response.body());
                Log.d("CHECK","response"+response.toString());
                Log.d("CHECK","response"+response.isSuccessful());

                Log.d("CHECK","call"+call.isExecuted());



                if(response.body()==null || response.body().size() ==0)
                {


                }
                else{
                    BotResponse botResponse = response.body().get(0);
                    Log.d("CHECK",""+botResponse.text);
                    messageClassArrayList.add(new MessageClass(botResponse.text,BOT));
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<BotResponse>> call, Throwable t) {
//                Log.d("CHECK","at line 113 failure");

                String msg = "Check Your connection";
                messageClassArrayList.add(new MessageClass(msg,BOT));

            }
        });


    }
}