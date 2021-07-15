package inspire2connect.inspire2connect.NlpChatbot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import inspire2connect.inspire2connect.R;
import inspire2connect.inspire2connect.home.HomeActivity;
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
    TextView tv_back_button;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ShapeableImageView iv_mic;


    private ArrayList<MessageClass> messageClassArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_screen);
        messageClassArrayList = new ArrayList<>();
        sendbutton = findViewById(R.id.sendbutton);
        messagebox = findViewById(R.id.messagebox);
        recyclerView = findViewById(R.id.recyclerview);
        tv_back_button= findViewById(R.id.tv_back);

        // adding speech to text converter
        iv_mic=findViewById(R.id.mic);

        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast.makeText(MainChatScreen.this, " " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


        tv_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),
                        HomeActivity.class));
                overridePendingTransition(0,0);
                finish();
            }
        });

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
    // result for mic activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
//                tv_Speech_to_text.setText(
//                        Objects.requireNonNull(result).get(0));

                String messageResponse=Objects.requireNonNull(result).get(0);
                sendMessage(messageResponse);
                Toast.makeText(MainChatScreen.this,"Message sent",Toast.LENGTH_SHORT).show();


            }
        }
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
//            recyclerView.scrollToPosition(messageClassArrayList.size()-1);
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("http://27220dedfffe.ngrok.io/webhooks/rest/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MessageSender messageSender = retrofit.create(MessageSender.class);
        Call<List<BotResponse>> response = messageSender.messageSender1(temp);

        response.enqueue(new Callback<List<BotResponse>>() {
            @Override
            public void onResponse(Call<List<BotResponse>> call, Response<List<BotResponse>> response) {
                Log.d("CHECK", "at line response");
                Log.d("CHECK", "response" + response.body());
                Log.d("CHECK", "response" + response.toString());
                Log.d("CHECK", "response" + response.isSuccessful());

                Log.d("CHECK", "call" + call.isExecuted());

                if (response.body() == null || response.body().size() == 0) {


                } else {
                    if (response.body().size() > 1) {
                        for (int i = 1; i <= response.body().size(); i++) {
                            BotResponse botResponse = response.body().get(i - 1);
                            Log.d("CHECK", "" + botResponse.text);
                            messageClassArrayList.add(new MessageClass(botResponse.text, BOT));
                            adapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

                        }
                    } else {
                        BotResponse botResponse = response.body().get(0);
                        Log.d("CHECK", "" + botResponse.text);
                        messageClassArrayList.add(new MessageClass(botResponse.text, BOT));
                        adapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

                    }


                }
            }
            @Override
            public void onFailure(Call<List<BotResponse>> call, Throwable t) {
//                Log.d("CHECK","at line 113 failure");

                String msg = "Check Your connection";
                messageClassArrayList.add(new MessageClass(msg,BOT));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

            }
        });


    }
}