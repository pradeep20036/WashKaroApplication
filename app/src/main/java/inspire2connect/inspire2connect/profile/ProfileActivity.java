package inspire2connect.inspire2connect.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import inspire2connect.inspire2connect.NlpChatbot.MainChatScreen;
import inspire2connect.inspire2connect.R;
import inspire2connect.inspire2connect.aqi_cough.MainScreening;
import inspire2connect.inspire2connect.home.EmailLogin;
import inspire2connect.inspire2connect.home.HomeActivity;
import inspire2connect.inspire2connect.home.LoginActivity;
import inspire2connect.inspire2connect.home.TermsAndConditionActivity;

import inspire2connect.inspire2connect.satyaChat.ChatActivity;

public class ProfileActivity extends AppCompatActivity {

    ImageView iv_profile;
    TextView tv_name;
    TextView tv_email;
    private static DatabaseReference databaseReference = null;
    TextView tv_phone;
    Uri imageUri;
    TextView rewards;
    @Override
    public void onBackPressed()
    {
     // do nothing
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_new);

        iv_profile=findViewById(R.id.account_image);
        tv_name=findViewById(R.id.tv_name);
        tv_email=findViewById(R.id.tv_email);
        rewards=findViewById(R.id.rewards);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid=user.getUid();
        if(uid!=null) {
            databaseReference = FirebaseDatabase.getInstance("https://washkaro-referral-rewards-2d45a.firebaseio.com/").getReference();

            if (databaseReference != null) {
                databaseReference.child(FirebaseAuth.getInstance().getUid()).child("Rewards").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            rewards.setText("Reward: " + "0");
                            Log.e("firebase", "Error getting data", task.getException());

                        } else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));

                            if(String.valueOf(task.getResult().getValue()).compareTo("null")==0){
                                rewards.setText("Reward: " +"0");
                            }else{
                                int value = Integer.parseInt(String.valueOf(task.getResult().getValue())) + 20;
                                rewards.setText("Reward: " + value + "");
                            }

                        }
                    }
                });
            }


//        code for switching activity on navbar

            // handling bottom navigation
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//    //perform item selector listener
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.home:
                            startActivity(new Intent(getApplicationContext(),
                                    HomeActivity.class));
                            overridePendingTransition(0, 0);
                            finish();
                            break;

                        case R.id.chatbot:
                            startActivity(new Intent(getApplicationContext(),
                                    TermsAndConditionActivity.class));
                            overridePendingTransition(0, 0);

                            break;
                        case R.id.wkscreen:
                            startActivity(new Intent(getApplicationContext(),
                                    MainScreening.class));
                            overridePendingTransition(0, 0);
                            finish();
                            break;

                    }

                    return false;
                }
            });

            // check if image is already saved in the memory, if yes then load it.

            loadInformation();

            // now making the imageView clickable
//        iv_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageClicked();
//            }
//        });

        }

        else{
            Toast.makeText(ProfileActivity.this,"Required login to access the Profile",Toast.LENGTH_SHORT).show();
        }




    }

    public void loadInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String phone = user.getPhoneNumber();
            if (name == null || email == null || photoUrl == null) {
                return;
            }

                tv_name.setText(name);
                tv_email.setText(email);

//                Toast.makeText(getApplicationContext(), photoUrl + "", Toast.LENGTH_LONG).show();
                Glide.with(this).load(photoUrl.toString()).into(iv_profile);


                // Check if user's email is verified
                boolean emailVerified = user.isEmailVerified();

                // The user's ID, unique to the Firebase project. Do NOT use this value to authenticate with your backend server, if you  have one. Use FirebaseUser.getToken() instead
                String uid = user.getUid();
            } else {
                Toast.makeText(getApplicationContext(), "SignIn Not complete" + "", Toast.LENGTH_LONG).show();
            }

        }

    public void imageClicked(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,19991);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 19991 && resultCode == RESULT_OK){
            imageUri = data.getData();

            // saving the profile image in the phone itself...

            iv_profile.setImageURI(imageUri);

//            iv_profile.setCropToPadding(true);
//            uploadImage();
            // Removes Uri Permission so that when you restart the device, it will be allowed to reload.
            this.grantUriPermission(this.getPackageName(), imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                this.getContentResolver().takePersistableUriPermission(imageUri, takeFlags);
            }

            // Saves image URI as string to Default Shared Preferences
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("image", String.valueOf(imageUri));
            editor.commit();

            // Sets the ImageView with the Image URI
            iv_profile.setImageURI(imageUri);
            iv_profile.invalidate();


        }
    }

//    private void uploadImage() {
//        final ProgressDialog progressDialog = new ProgressDialog(getAc());
//        progressDialog.setTitle("Saving your Display Picture");
//        progressDialog.show();
//
//        reference = FirebaseStorage.getInstance().getReference()
//                .child("UserAccountImages")
//                .child(userId+".jpeg");
//        reference.putFile(imageUri).
//                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getActivity(),"Profile Image Set Successfully",Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getActivity(),e.getCause().toString(),Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                double percentage = (snapshot.getBytesTransferred()*100/snapshot.getTotalByteCount());
//                progressDialog.setMessage("Progress"+ (int)percentage +"%");
//            }
//        });
//    }

public void onLogout(View view) {
    FirebaseAuth.getInstance().signOut();
    startActivity(new Intent(this, LoginActivity.class));
    finish();
    }

}