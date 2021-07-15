package inspire2connect.inspire2connect.home;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import inspire2connect.inspire2connect.NlpChatbot.MainChatScreen;
import inspire2connect.inspire2connect.R;

public class TermsAndConditionActivity extends AppCompatActivity {

    TextView tv_agree;
    TextView dis_agree;

    @Override protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);

        tv_agree=findViewById(R.id.agree);
        dis_agree=findViewById(R.id.disagree);





        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                startActivity(new Intent(getApplicationContext(),
                        MainChatScreen.class));
                overridePendingTransition(0,0);
                finish();
            }
        });


        dis_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),
                        HomeActivity.class));
                overridePendingTransition(0,0);
                finish();
            }
        });





    }

}

