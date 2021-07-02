package inspire2connect.inspire2connect.aqi_cough;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import inspire2connect.inspire2connect.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static inspire2connect.inspire2connect.aqi_cough.UserInfoScreen.currLat;
import static inspire2connect.inspire2connect.aqi_cough.UserInfoScreen.currLong;


public class DataCollection extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String currentUserID;

    public static FirebaseAuth firebaseAuth;
    public static FirebaseUser firebaseUser;

    public String AQIVal[];
    double actualLat;
    double actualLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        firebaseUser = firebaseAuth.getCurrentUser();
                    }
                }
            });
        }

        if (firebaseUser != null) {
            currentUserID = firebaseUser.getUid();
        }

        final TextView avgAQI = (TextView) findViewById(R.id.avgAQI);

        OkHttpClient client = new OkHttpClient();

        AQIVal = new String[]{"Unavailable"};
        double prevLat = getIntent().getExtras().getDouble("currLat");
        double prevLong = getIntent().getExtras().getDouble("currLong");
        System.out.println("CURR LAT - " + currLat + ", CURR LONG - " + currLong);
        actualLat = 0;
        actualLong = 0;
        if (currLat < 0.2){
            actualLat = currLat;
            actualLong = currLong;
        }
        else if (prevLat < 0.2) {
            actualLat = prevLat;
            actualLong = prevLong;
        }
        else {
            actualLat = 28.5456;
            actualLong = 77.2732;
        }

        String url = "http://api.waqi.info/feed/geo:" + actualLat + ";" + actualLong + "/?token=74483de2b4238f9e5ba5ce927bfc0476bd2b10d2";

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    try {
                        JSONObject aqiJSON = new JSONObject(myResponse);
                        JSONObject aqiData = (JSONObject) aqiJSON.get("data");
                        AQIVal[0] = aqiData.get("aqi").toString();
                        System.out.println("AQI VALUE - " + AQIVal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                final String finalAQI1 = AQIVal[0];
                DataCollection.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        avgAQI.setText(finalAQI1);
                    }
                });
            }
        });

        Button resetButton = (Button) findViewById(R.id.resetButton);
        Button geotagButton = (Button) findViewById(R.id.geotagButton);

        final EditText heightText = (EditText) findViewById(R.id.heightText);
        final EditText weightText = (EditText) findViewById(R.id.weightText);
        final EditText ageText = (EditText) findViewById(R.id.ageText);

        final CheckBox bronchitisCheck = (CheckBox) findViewById(R.id.bronchitisCheck);
        final CheckBox asthmaCheck = (CheckBox) findViewById(R.id.asthmaCheck);
        final CheckBox pneumoniaCheck = (CheckBox) findViewById(R.id.pneumoniaCheck);
        final CheckBox tbCheck = (CheckBox) findViewById(R.id.tbCheck);
        final CheckBox cancerCheck = (CheckBox) findViewById(R.id.lungCancerCheck);
        final CheckBox otherRespCheck = (CheckBox) findViewById(R.id.otherRespCheck);

        final CheckBox femaleCheck = (CheckBox) findViewById(R.id.femaleCheck);
        final CheckBox maleCheck = (CheckBox) findViewById(R.id.maleCheck);
        final CheckBox otherGenderCheck = (CheckBox) findViewById(R.id.otherGenderCheck);



        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                heightText.setText("", TextView.BufferType.EDITABLE);
                weightText.setText("", TextView.BufferType.EDITABLE);
                ageText.setText("", TextView.BufferType.EDITABLE);
                bronchitisCheck.setChecked(false);
                asthmaCheck.setChecked(false);
                pneumoniaCheck.setChecked(false);
                cancerCheck.setChecked(false);
                tbCheck.setChecked(false);
                otherRespCheck.setChecked(false);
                femaleCheck.setChecked(false);
                maleCheck.setChecked(false);
                otherGenderCheck.setChecked(false);

            }
        });

        geotagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String heightString = heightText.getText().toString();
                String weightString = weightText.getText().toString();
                String ageString = ageText.getText().toString();
                boolean bronchitisVal = bronchitisCheck.isChecked();    //To Convert to String : Boolean.toString(bronchitisVal)
                boolean asthmaVal = asthmaCheck.isChecked();
                boolean pneumoniaVal = pneumoniaCheck.isChecked();
                boolean cancerVal = cancerCheck.isChecked();
                boolean tbVal = tbCheck.isChecked();
                boolean otherRespVal = otherRespCheck.isChecked();
                boolean femaleVal = femaleCheck.isChecked();
                boolean maleVal = maleCheck.isChecked();
                boolean otherGenderVal = otherGenderCheck.isChecked();

                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                UserHelperClass helperClass = new UserHelperClass(avgAQI.getText().toString(), "" + actualLat, "" + actualLong, heightString, weightString, ageString, bronchitisVal, asthmaVal, pneumoniaVal, cancerVal, tbVal, otherRespVal, femaleVal, maleVal, otherGenderVal, currentTime);

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("TbUserData").child(currentUserID);
                reference.push().setValue(helperClass);

                Intent geotagIntent = new Intent(DataCollection.this, coughRecorder.class);
                geotagIntent.putExtra("actLat", actualLat);
                geotagIntent.putExtra("actLong", actualLong);
                geotagIntent.putExtra("aqiVal", AQIVal[0]);
                startActivity(geotagIntent);
            }
        });

        maleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maleCheck.isChecked()) {
                    femaleCheck.setChecked(false);
                    otherGenderCheck.setChecked(false);
                } else if (femaleCheck.isChecked()) {
                    otherGenderCheck.setChecked(false);
                    maleCheck.setChecked(false);
                } else if (otherGenderCheck.isChecked()) {
                    maleCheck.setChecked(false);
                    femaleCheck.setChecked(false);
                }
            }
        });

        femaleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (femaleCheck.isChecked()) {
                    otherGenderCheck.setChecked(false);
                    maleCheck.setChecked(false);
                }
                else if (maleCheck.isChecked()) {
                    femaleCheck.setChecked(false);
                    otherGenderCheck.setChecked(false);
                }
                else if (otherGenderCheck.isChecked()) {
                    maleCheck.setChecked(false);
                    femaleCheck.setChecked(false);
                }
            }
        });

        otherGenderCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otherGenderCheck.isChecked()) {
                    femaleCheck.setChecked(false);
                    maleCheck.setChecked(false);
                } else if (femaleCheck.isChecked()) {
                    otherGenderCheck.setChecked(false);
                    maleCheck.setChecked(false);
                } else if (maleCheck.isChecked()) {
                    otherGenderCheck.setChecked(false);
                    femaleCheck.setChecked(false);
                }
            }
        });

    }}

