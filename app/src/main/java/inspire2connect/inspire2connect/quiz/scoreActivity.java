package inspire2connect.inspire2connect.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import inspire2connect.inspire2connect.R;
import inspire2connect.inspire2connect.home.HomeActivity;
import inspire2connect.inspire2connect.utils.BaseActivity;

public class scoreActivity extends BaseActivity {
    String currentUserID;
    private ImageView imageView;
    private TextView score,share,note;
    private int[] user_selections;
    private TextView done, solutions;
    String score_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_score);


        score = findViewById(R.id.sa_score);
        imageView = findViewById(R.id.face);
        done = findViewById(R.id.sa_done_textview);
        solutions = findViewById(R.id.button_view_solutions_textview);
        share = findViewById(R.id.share_button_textview);
        note = findViewById(R.id.note);

        score_str = getIntent().getStringExtra("SCORE");
        user_selections = getIntent().getIntArrayExtra("SELECTED_OPTIONS");
        ArrayList<questionObject> questions = (ArrayList<questionObject>) getIntent().getSerializableExtra("QUESTIONS");
//        ArrayList<questionObject> questions = quizActivity.selected_questions
        score.setText(score_str);

//      Firebase Analytics
        Bundle bundle1 = new Bundle();
        if (firebaseUser != null) {
            currentUserID = firebaseUser.getUid();
        }
        bundle1.putString("UID", currentUserID);
        bundle1.putString("QuizStatus", "Quiz Completed");
        bundle1.putString("QuizScore", score_str);
        FirebaseAnalytics.getInstance(this).logEvent("QuizStatus", bundle1);

//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Logd("CHECK",score_str.charAt(0)+" "+ score_str.length());
        int x = Integer.parseInt(String.valueOf(score_str.charAt(0)));
        if(x>=3){
            note.setText("You have completed your quiz successfully");
        }
        else{
            note.setText("Try harder next time!");
        }

        if(x==5){
            imageView.setImageResource(R.drawable.happy_full);
        }
        else if(x>=3 && x <5)
        {
            imageView.setImageResource(R.drawable.happiness);
        }
        else
        {
            imageView.setImageResource(R.drawable.sad);
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(scoreActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });

        solutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scoreActivity.this, quizSolutionsActivity.class);
//                intent.putExtra("QUESTIONS", questions);
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "I have got " +score_str + " score in the quiz" );
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

    }
}
