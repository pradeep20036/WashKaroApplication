package inspire2connect.inspire2connect.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;

import inspire2connect.inspire2connect.R;
import inspire2connect.inspire2connect.aqi_cough.aqi_activity;
import inspire2connect.inspire2connect.mythGuidelineUpdates.UpdateActivity;
import inspire2connect.inspire2connect.quiz.quizActivity;
import inspire2connect.inspire2connect.tweets.tweetActivity;

@SuppressWarnings("SpellCheckingInspection")
public class BaseActivity extends AppCompatActivity {

    // Firebase References
    public static final DatabaseReference infographicReference = FirebaseDatabase.getInstance("https://washkaro-infographic.firebaseio.com").getReference();
    public static final DatabaseReference guidelinesReference = FirebaseDatabase.getInstance("https://washkaro-guidelines.firebaseio.com").getReference();
    public static final DatabaseReference governmentReference = FirebaseDatabase.getInstance("https://washkaro-government.firebaseio.com").getReference();
    public static final DatabaseReference mythReference = FirebaseDatabase.getInstance("https://washkaro-myth.firebaseio.com").getReference();
    public static final DatabaseReference whoReference = FirebaseDatabase.getInstance("https://washkaro-who.firebaseio.com").getReference();
    public static final DatabaseReference statsReference = FirebaseDatabase.getInstance("https://washkaro-stats.firebaseio.com").getReference();
    public static final DatabaseReference successStoriesReference = FirebaseDatabase.getInstance("https://washkaro-success.firebaseio.com").getReference();
    public static final DatabaseReference faqsReference = FirebaseDatabase.getInstance("https://washkaro-faq.firebaseio.com").getReference();
    public static final DatabaseReference tweetsReference = FirebaseDatabase.getInstance("https://washkaro-twitter.firebaseio.com").getReference();
    public static final DatabaseReference quizReference = FirebaseDatabase.getInstance("https://washkaro-quiz.firebaseio.com").getReference();
    public static final DatabaseReference aqiReference = FirebaseDatabase.getInstance("https://washkaro-aqi.firebaseio.com").getReference();
    // mythGuidelineUpdatesKey
    public static final String TYPE = "T";
    public static final String GUIDELINES = "0";
    public static final String UPDATES = "1";
    public static final String MYTH = "2";
    public static final String FAQ = "3";
    public static final String SUCCESS_STORIES = "4";
    public static final String TWEETS = "5";
    public static final String QUIZ = "6";
    public static final String AQI = "7";
    public static final String DATE = "D";
    public static final String DATE_YES = "1";
    public static final String DATE_NO = "0";
    // TODO: Add Code for New Language Here
    public static final String englishCode = "en";
    public static final String hindiCode = "hi";
    // TTS
    public static final int SPEECH_REQUEST_CODE = 1500;
    // TODO: Add Firebase Key for Language Here
    private static final String englishKey = "english";
    private static final String hindiKey = "hindi";
    private static final HashMap<String, String> langKeyMap = new HashMap<String, String>() {
        {
            // TODO: Add put entry here
            put(englishCode, englishKey);
            put(hindiCode, hindiKey);
        }
    };
    // Firebase Analytics
    public static FirebaseAnalytics firebaseAnalytics;
    // Firebase Authenticaiton
    public static FirebaseAuth firebaseAuth;
    public static FirebaseUser firebaseUser;
    public static DatabaseReference usersDB = FirebaseDatabase.getInstance().getReference("users");

    public static final void Logv(String TAG, String message) {
        Log.v(TAG, message);
    }

    public static final void Loge(String TAG, String message) {
        Log.e(TAG, message);
    }

    public static final void Logd(String TAG, String message) {
        Log.d(TAG, message);
    }

    public static void toggleLang(Activity mActivity) {

        String curLang = Locale.getDefault().getLanguage();

        switch (curLang) {
            case englishCode:
                LocaleHelper.setLocale(mActivity, hindiCode);
                break;
            case hindiCode:
                LocaleHelper.setLocale(mActivity, englishCode);
                break;
            default:
                Logv("language-washkaro", "wrong language: " + curLang);
                break;
        }
        mActivity.startActivity(mActivity.getIntent());
        mActivity.finish();
    }

    public static String getCurLang() {
        return Locale.getDefault().getLanguage();
    }

    public static String getCurLangKey() {
        return langKeyMap.get(getCurLang());
    }

    public static void openPrivacyPolicy(Activity activity) {
        Intent i = new Intent(activity, urlActivity.class);
        i.putExtra("url", activity.getString(R.string.privacy_policy_url));
        i.putExtra("name", activity.getString(R.string.privacy_policy));
        activity.startActivity(i);
    }

    public static String getURLEncoding(String surl) throws Exception {
        URL u = new URL(surl);
        return new URI(u.getProtocol(), u.getAuthority(), u.getPath(), u.getQuery(), u.getRef()).toString();
    }

    public static Intent getGovernmentIntent(Activity activity) {
        Intent i = new Intent(activity, UpdateActivity.class);
        i.putExtra(TYPE, UPDATES);
        i.putExtra(DATE, DATE_YES);
        return i;
    }

    public static Intent getSocialAnalysisIntent(Activity activity) {
        Intent i = new Intent(activity, tweetActivity.class);
        i.putExtra(TYPE, GUIDELINES);
        i.putExtra(DATE, DATE_NO);
        return i;
    }

    public static Intent getMythIntent(Activity activity) {
        Intent i = new Intent(activity, UpdateActivity.class);
        i.putExtra(TYPE, MYTH);
        i.putExtra(DATE, DATE_NO);
        return i;
    }

    public static Intent getTwitterIntent(Activity activity) {
        Intent i = new Intent(activity, tweetActivity.class);
        i.putExtra(TYPE, TWEETS);
        i.putExtra(DATE, DATE_NO);
        return i;
    }

    public static Intent getQuizIntent(Activity activity) {
        Intent i = new Intent(activity, quizActivity.class);
        i.putExtra(TYPE, QUIZ);
        i.putExtra(DATE, DATE_NO);
        return i;
    }


    public static Intent getSuccessStoriesIntent(Activity activity) {
        Intent i = new Intent(activity, UpdateActivity.class);
        i.putExtra(TYPE, SUCCESS_STORIES);
        i.putExtra(DATE, DATE_NO);
        return i;
    }

    public static Intent getFAQsIntent(Activity activity) {
        Intent i = new Intent(activity, UpdateActivity.class);
        i.putExtra(TYPE, FAQ);
        i.putExtra(DATE, DATE_NO);
        return i;
    }

    public static Intent getAqiIntent(Activity activity) {
        Intent i = new Intent(activity, aqi_activity.class);
        i.putExtra(TYPE, AQI);
        i.putExtra(DATE, DATE_NO);
        return i;
    }

    public void getReferralLink(){
        Log.d("Referral", "getReferralLink: Working ");
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("http://tavlab.iiitd.edu.in/"))
                .setDomainUriPrefix("washkaro.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String shareLinkText = "https://washkaro.inspire2connect.com/?invitedby=" + uid
                +"link=http://tavlab.iiitd.edu.in/"
                +"&apn=" + "WashKaro-TB"
                +"&st=" + "My Referral Link"
                +"&sp=" + "Reward Points = 20"
                +"&si=" + "http://tavlab.iiitd.edu.in/WashKaro/assets/img/washkaro.png"
                ;

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse(shareLinkText))
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT,  shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);
                        } else {
                            Log.d("Dynamic Link", "Error:"+task.getException().getMessage());
                        }
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.gradient_effect);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(4,5,6));
//            window.setNavigationBarColor(Color.TRANSPARENT);
            window.setBackgroundDrawable(background);
        }
    }

}
