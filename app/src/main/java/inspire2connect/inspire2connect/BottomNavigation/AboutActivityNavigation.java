package inspire2connect.inspire2connect.BottomNavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import inspire2connect.inspire2connect.R;

public class AboutActivityNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_navigation);
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
                                HomeActivityBottomNavigation.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.about:

                        startActivity(new Intent(getApplicationContext(),
                                AboutActivityNavigation.class));
                        overridePendingTransition(0,0);

                        break;

                }


                return false;
            }
        });

    }
}