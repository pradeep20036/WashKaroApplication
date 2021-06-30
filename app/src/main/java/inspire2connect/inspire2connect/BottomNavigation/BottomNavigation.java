package inspire2connect.inspire2connect.BottomNavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;

import inspire2connect.inspire2connect.R;
import inspire2connect.inspire2connect.home.HomeActivity;
import inspire2connect.inspire2connect.quiz.Time_Up;

public class BottomNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        //set home as default
//        bottomNavigationView.setSelectedItemId(R.id.home);

        //perfomr item selector listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                HomeActivity.class));
                        overridePendingTransition(0,0);

                        break;

                    case R.id.about:

                        startActivity(new Intent(getApplicationContext(),
                                Time_Up.class));
                        overridePendingTransition(0,0);

                        break;

                }


                return false;
            }
        });


    }



}