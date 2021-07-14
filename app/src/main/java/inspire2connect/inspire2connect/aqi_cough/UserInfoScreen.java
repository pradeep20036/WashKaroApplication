package inspire2connect.inspire2connect.aqi_cough;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import inspire2connect.inspire2connect.R;

public class UserInfoScreen extends AppCompatActivity {
    EditText userNameTF;
    EditText userRollTF;
    EditText userBranchTF;
    Button submitChangeButton;
    Button checkLocationButton;
    String changeName;
    String changeRoll;
    String changeBranch;
    int studInd;

    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;
    private static final int REQUEST_CODE = 101;
    public static double currLat, currLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_screen);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        changeName = getIntent().getExtras().getString("studName");
        changeRoll = getIntent().getExtras().getString("studRoll");
        changeBranch = getIntent().getExtras().getString("studBranch");
        studInd = getIntent().getExtras().getInt("indVal");
        userNameTF = (EditText) findViewById(R.id.userNameTF);
        userRollTF = (EditText) findViewById(R.id.userRollTF);
        userBranchTF = (EditText) findViewById(R.id.userBranchTF);
        submitChangeButton = (Button) findViewById(R.id.submitChangeButton);
        userNameTF.setText(changeName);
        userRollTF.setText(changeRoll);
        userBranchTF.setText(changeBranch);
        submitChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeName = userNameTF.getText().toString();
                changeRoll = userRollTF.getText().toString();
                changeBranch = userBranchTF.getText().toString();
                Intent mainIntent = new Intent(UserInfoScreen.this, MainScreening.class);
                mainIntent.putExtra("savedName", changeName);
                mainIntent.putExtra("savedRoll", changeRoll);
                mainIntent.putExtra("savedBranch", changeBranch);
                mainIntent.putExtra("savedVal", studInd);
                mainIntent.putExtra("studNameArr", getIntent().getExtras().getStringArray("studNameArr"));
                mainIntent.putExtra("studRollArr", getIntent().getExtras().getStringArray("studRollArr"));
                mainIntent.putExtra("studBranchArr", getIntent().getExtras().getStringArray("studBranchArr"));
                startActivity(mainIntent);
                finish();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }

}
