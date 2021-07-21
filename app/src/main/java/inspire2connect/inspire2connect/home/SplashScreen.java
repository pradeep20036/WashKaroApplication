package inspire2connect.inspire2connect.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import inspire2connect.inspire2connect.R;

public class SplashScreen extends AppCompatActivity {

    private ImageView icon;
    private TextView welcome_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        icon = findViewById(R.id.welcome_icon);
        welcome_text = findViewById(R.id.welcome_text);
        newanim();

    }
    public void newanim()
    {    ;
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim1);
        animation.setDuration(1000);
        Animation animation2= AnimationUtils.loadAnimation(this,R.anim.blink);
        animation2.setDuration(1000);

        Animation animation1= AnimationUtils.loadAnimation(this,R.anim.anim2);
        animation1.setDuration(1000);
        welcome_text.setAnimation(animation);
        welcome_text.setAnimation(animation2);
        icon.setAnimation(animation1);
        animation.start();
        new Timer().schedule(new TimerTask() {
            public void run() {

                startActivity(new Intent(SplashScreen.this, WelcomeActivity.class));
                finish();

            }
        }, 1000);
    }
    {
    }
}
