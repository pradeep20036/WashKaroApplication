package inspire2connect.inspire2connect.aqi_cough;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import inspire2connect.inspire2connect.R;

public class MainScreening extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;
    private static final int REQUEST_CODE = 101;
    public static double currLat, currLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screening);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        Button leaderboardScreenButton = (Button) findViewById(R.id.leaderboardScreenButton);
        Button chatbotScreenButton = (Button) findViewById(R.id.chatbotScreenButton);
        Button dataScreenButton = (Button) findViewById(R.id.dataScreenButton);

        Button videoScreenButton = (Button) findViewById(R.id.videoScreenButton);
        Button materialScreenButton = (Button) findViewById(R.id.materialScreenButton);

        leaderboardScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent leaderIntent = new Intent(MainScreening.this, TbUserLeader.class);
                startActivity(leaderIntent);
            }
        });

        chatbotScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookIntent = new Intent(MainScreening.this, BookDownloader.class);
                startActivity(bookIntent);
            }
        });

        videoScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vidIntent = new Intent(MainScreening.this, YoutubePlayVid.class);
                startActivity(vidIntent);
            }
        });

        dataScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchLastLocation();
                Intent dataIntent = new Intent(MainScreening.this, DataCollection.class);
                dataIntent.putExtra("currLat", currLat);
                dataIntent.putExtra("currLong", currLong);
                startActivity(dataIntent);
            }
        });

    }


    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    currentLocation = location;
                    currLat = currentLocation.getLatitude();
                    currLong = currentLocation.getLongitude();
                }
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }}


