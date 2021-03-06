package inspire2connect.inspire2connect.about;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

import in.srain.cube.views.GridViewWithHeaderAndFooter;
import inspire2connect.inspire2connect.R;
import inspire2connect.inspire2connect.utils.BaseActivity;
import inspire2connect.inspire2connect.utils.LocaleHelper;


@SuppressWarnings("SpellCheckingInspection")
public class aboutActivity extends BaseActivity implements View.OnClickListener {

    String currentUserID;
    aboutElem[] elems;
    Context ctx;
    private ImageView tavlab;
    private ImageView precog;
    private ImageView iiitd;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ctx = this;

        elems = new aboutElem[]{
                new aboutElem(this, R.string.akshat, R.string.tanuj_tag, R.string.akshatURL),
                new aboutElem(this, R.string.ayush, R.string.tanuj_tag, R.string.tanujURL),
                new aboutElem(this, R.string.bhavika, R.string.bhavika_tag, R.string.bhavikaURL),
                new aboutElem(this, R.string.chirag, R.string.chirag_tag, R.string.chiragURL),
                new aboutElem(this, R.string.harsh, R.string.harsh_tag, R.string.harshURL),
                new aboutElem(this, R.string.himanshu, R.string.himanshu_tag, R.string.himanshuURL),
                new aboutElem(this, R.string.kanav, R.string.kanav_tag, R.string.kanavURL),
                new aboutElem(this, R.string.priyanka, R.string.priyanka_tag, R.string.priyankaURL),
                new aboutElem(this, R.string.raunak, R.string.raunak_tag, R.string.raunakURL),
                new aboutElem(this, R.string.rishabh, R.string.tanuj_tag, R.string.rishabhURL),
                new aboutElem(this, R.string.rohan, R.string.rohan_tag, R.string.rohanURL),
                new aboutElem(this, R.string.tanuj, R.string.tanuj_tag, R.string.tanujURL),
                new aboutElem(this, R.string.vaibhav, R.string.vaibhav_tag, R.string.vaibhavURL),
                new aboutElem(this, R.string.vihaan, R.string.vihaan_tag, R.string.vihaanURL),
                new aboutElem(this, R.string.Pradeep, R.string.Pradeep_tag, R.string.PradeepURL),
                new aboutElem(this, R.string.Sudeep, R.string.Sudeep_tag, R.string.sudeepURL),
                new aboutElem(this, R.string.vrinda, R.string.vrinda_tag, R.string.vrindaURL),
                new aboutElem(this, R.string.yashwin, R.string.yashwin_tag, R.string.yashwinURL),
                new aboutElem(this, R.string.tav, R.string.tav_description, R.string.tavURL),
                new aboutElem(this, R.string.pk, R.string.pk_description, R.string.pkURL)
        };

        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.ca_activity_about);


        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle(getString(R.string.about_us));
            getActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.about_us));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        GridViewWithHeaderAndFooter gridView = findViewById(R.id.gridview);
        about_adapter aboutAdapter = new about_adapter(this, elems);
//        gridView.setAdapter(aboutAdapter);

        tavlab = findViewById(R.id.tavlabLogo);
        precog = findViewById(R.id.iiitdLogo);
        iiitd = findViewById(R.id.precogLogo);

        tavlab.setOnClickListener(this);
        precog.setOnClickListener(this);
        iiitd.setOnClickListener(this);

        // Firebase Analytics
        if (firebaseUser != null) {
            currentUserID = firebaseUser.getUid();
        }
        Bundle bundle = new Bundle();
        bundle.putString("UID", currentUserID);
        bundle.putString("Screen", "About Page");
        FirebaseAnalytics.getInstance(this).logEvent("CurrentScreen", bundle);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String url = elems[position].url;
                Intent i = new Intent(Intent.ACTION_VIEW);

                //Firebase Analytics
                if (firebaseUser != null) {
                    currentUserID = firebaseUser.getUid();
                }
                Bundle bundle1 = new Bundle();
                bundle1.putString("UID", currentUserID);
                bundle1.putString("Profile_Visited", elems[position].name);
                bundle1.putString("Profile_Visited_URL", Uri.encode(elems[position].url));
                FirebaseAnalytics.getInstance(ctx).logEvent("Profile_Visits", bundle1);

                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        LayoutInflater footerInflater = LayoutInflater.from(this);
        View footer = footerInflater.inflate(R.layout.references_view, null);
        footer.findViewById(R.id.tbassnindiaLogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = ctx.getString(R.string.tbassindia_web);
                Intent i = new Intent(Intent.ACTION_VIEW);

                //Firebase Analytics
                if (firebaseUser != null) {
                    currentUserID = firebaseUser.getUid();
                }
                Bundle bundle1 = new Bundle();
                bundle1.putString("UID", currentUserID);
                bundle1.putString("Reference_URL", url);
                FirebaseAnalytics.getInstance(ctx).logEvent("References_Visited", bundle1);

                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        footer.findViewById(R.id.tbcindia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = ctx.getString(R.string.tbcindia_web);
                Intent i = new Intent(Intent.ACTION_VIEW);

                //Firebase Analytics
                if (firebaseUser != null) {
                    currentUserID = firebaseUser.getUid();
                }
                Bundle bundle1 = new Bundle();
                bundle1.putString("UID", currentUserID);
                bundle1.putString("Reference_URL", url);
                FirebaseAnalytics.getInstance(ctx).logEvent("References_Visited", bundle1);

                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        gridView.addFooterView(footer);

        gridView.setAdapter(aboutAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return true;
        }
    }


    @Override
    public void onClick(View view) {
        String url = getString(R.string.iiitdURL);
        switch (view.getId()) {
            case R.id.iiitdLogo:
                url = getString(R.string.iiitdURL);
                break;
            case R.id.precogLogo:
                url = getString(R.string.precogURL);
                break;
            case R.id.tavlabLogo:
                url = getString(R.string.tavlabURL);
                break;
        }

        //Firebase Analytics
        if (firebaseUser != null) {
            currentUserID = firebaseUser.getUid();
        }
        Bundle bundle = new Bundle();
        bundle.putString("UID", currentUserID);
        bundle.putString("URL", url);
        FirebaseAnalytics.getInstance(this).logEvent("URLs_Visited", bundle);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}