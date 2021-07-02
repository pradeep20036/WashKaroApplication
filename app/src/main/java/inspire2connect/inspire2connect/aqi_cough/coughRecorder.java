package inspire2connect.inspire2connect.aqi_cough;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import inspire2connect.inspire2connect.R;

import static inspire2connect.inspire2connect.aqi_cough.UserInfoScreen.currLat;
import static inspire2connect.inspire2connect.aqi_cough.UserInfoScreen.currLong;


public class coughRecorder extends AppCompatActivity {
    ConstraintLayout[] buttons = new ConstraintLayout[5];
    String pathSave2 = "";
    Button submitButton2;
    MediaRecorder mediaRecorder2;
    MediaPlayer mediaPlayer2;
    UploadTask uploadTask2;
    String currentUserID = "";

    public static FirebaseAuth firebaseAuth;
    public static FirebaseUser firebaseUser;

    double actualLat;
    double actualLong;
    String AQInum;

    final int REQUEST_PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cough_recorder);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            currentUserID = firebaseUser.getUid();
        }

        if(!checkPermissionFromDevice())
            requestPermission();

        buttons[0] =  findViewById(R.id.recordButton1);
        buttons[1] = findViewById(R.id.stopButton1);
        buttons[2] =  findViewById(R.id.playFileButton1);
        buttons[3] =  findViewById(R.id.stopFileButton1);

        submitButton2 = (Button) findViewById(R.id.submitButton2);

        double prevLat = getIntent().getExtras().getDouble("actLat");
        double prevLong = getIntent().getExtras().getDouble("actLong");
        AQInum = getIntent().getExtras().getString("aqiVal");

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

        submitButton2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageRef = storage.getReference();

                Uri file = Uri.fromFile(new File(pathSave2));
                Date currentTime = Calendar.getInstance().getTime();

                StorageReference coughRef = storageRef.child("coughAudio/" + currentUserID + "/" + currentTime + file.getLastPathSegment());

                uploadTask2 = coughRef.putFile(file);

                uploadTask2.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });

                Intent pageChange = new Intent(coughRecorder.this, GeoTagPage.class);
                pageChange.putExtra("actLat", actualLat);
                pageChange.putExtra("actLong", actualLong);
                pageChange.putExtra("aqiVal", AQInum);
                startActivity(pageChange);
            }

        });


        buttons[0].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(checkPermissionFromDevice()){
                    pathSave2 = getExternalFilesDir(pathSave2).getAbsolutePath() + "/" + UUID.randomUUID().toString()+"_audio_record.3gp";
                    System.out.println("PATH - " + pathSave2);
                    setupMediaRecorder();
                    try{
                        mediaRecorder2.prepare();
                        System.out.println("PREPARED");
                        mediaRecorder2.start();
                        System.out.println("STARTED");
                        Toast.makeText(coughRecorder.this, "Please COUGH!", Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    buttons[1].setEnabled(true);
                    buttons[2].setEnabled(false);
                    buttons[3].setEnabled(false);
                    buttons[0].setEnabled(false);
                }
                else{
                    requestPermission();
                }

            }
        });

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder2.stop();
                buttons[1].setEnabled(false);
                buttons[2].setEnabled(true);
                buttons[3].setEnabled(false);
            }
        });

        buttons[2].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                buttons[3].setEnabled(true);
                buttons[1].setEnabled(false);
                buttons[0].setEnabled(false);

                mediaPlayer2 = new MediaPlayer();
                try{
                    mediaPlayer2.setDataSource(pathSave2);
                    mediaPlayer2.prepare();
                    if (!mediaPlayer2.isPlaying()) {
                        mediaPlayer2.start();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                Toast.makeText(coughRecorder.this, "Playing...", Toast.LENGTH_SHORT).show();
            }
        });

        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttons[3].setEnabled(false);
                buttons[0].setEnabled(true);
                buttons[1].setEnabled(false);
                buttons[2].setEnabled(true);

                if(mediaPlayer2 != null){
                    mediaPlayer2.stop();
                    mediaPlayer2.release();
                    setupMediaRecorder();
                }
            }
        });



    }

    private void setupMediaRecorder() {
        mediaRecorder2 = new MediaRecorder();
        mediaRecorder2.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder2.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder2.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder2.setOutputFile(pathSave2);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
        },REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }

}
