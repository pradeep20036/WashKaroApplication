package inspire2connect.inspire2connect.NlpChatbot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import inspire2connect.inspire2connect.R;
import inspire2connect.inspire2connect.home.HomeActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class MainChatScreen extends AppCompatActivity {
    FloatingActionButton sendbutton;
    RecyclerView recyclerView;
    EditText messagebox;
    private String USER;
    private String BOT = "MYBOT";
    MessageAdapter adapter;
    TextView tv_back_button;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ShapeableImageView iv_mic;
    Switch tg_hindi;

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

        tg_hindi=findViewById(R.id.tg_switch);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        USER = user.getUid();



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


                    if(tg_hindi.isChecked()){
                        // if message is in hindi , then convert it in english... and then give it to the send
                        // message function
                        String reply[]=new String[1];
                        try {
                            getTranslatedString(reply,msg,"hindi","english");
                            msg=reply[0];
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    sendMessage(msg);

//                    Toast.makeText(MainChatScreen.this,"Message sent",Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(MainChatScreen.this,"Message sent",Toast.LENGTH_SHORT).show();


            }
        }
    }

// language translation model By Vineet Integration & code writing
    public void getTranslatedString(String [] reply,String input,String source, String target) throws IOException {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here


            String server = "http://1.1.1.216:7584";
            String payload = "/translate?source="+source+"&target="+target+"&text="+input;
            String url = server + payload;
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            OkHttpClient client = new OkHttpClient();

            okhttp3.Call call = client.newCall(request);
            okhttp3.Response response = call.execute();


            if (response.isSuccessful()) {
                String myResponse = response.body().string();
//                    System.out.println(myResponse);
                try {
                    JSONObject obj = new JSONObject(myResponse);
                    String ans = obj.get("output") + "";
                    String escapedStr = ans;
                    String hindiStr = StringEscapeUtils.unescapeJava(escapedStr);

                    reply[0] = hindiStr;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
//        http://fd8926d6aa3f.ngrok.io
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("http://6de72a2fbadc.ap.ngrok.io/webhooks/rest/")
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
                            // bot will always reply in english... if hindi is toggled then it should display in hindi

                            String bot_response=botResponse.text;

                            if(tg_hindi.isChecked()){
                                // if message is in hindi , then convert it in english... and then give it to the send
                                // message function
                                String reply[]=new String[1];
                                try {
                                    getTranslatedString(reply,bot_response,"english","hindi");
                                    bot_response=reply[0];
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            Log.d("CHECK", "" + bot_response);
                            messageClassArrayList.add(new MessageClass(bot_response, BOT));
                            adapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

                        }
                    } else {
                        BotResponse botResponse = response.body().get(0);
                        Log.d("CHECK", "" + botResponse.text);
                        String bot_response=botResponse.text;

                        if(tg_hindi.isChecked()){
                            // if message is in hindi , then convert it in english... and then give it to the send
                            // message function
                            String reply[]=new String[1];
                            try {
                                getTranslatedString(reply,bot_response,"english","hindi");
                                bot_response=reply[0];
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        messageClassArrayList.add(new MessageClass(bot_response, BOT));
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