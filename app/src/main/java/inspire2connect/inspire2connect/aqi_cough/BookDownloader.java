package inspire2connect.inspire2connect.aqi_cough;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.HashMap;

import inspire2connect.inspire2connect.R;

public class BookDownloader extends AppCompatActivity {
    Button downloadFileButton, proInterButton;
    RadioGroup radioGroup;
    String downloadName;
    HashMap<Integer,String> downloadlink;
    HashMap<Integer,String> DownloadName;
    private static final int STORAGE_PERMISSION_VAL = 987;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_downloader);

        final RadioButton iecChildCheck =  findViewById(R.id.iecChildCheck);
        final RadioButton standTBCheck =  findViewById(R.id.standTBCheck);
        final RadioButton natStatCheck =  findViewById(R.id.natStatCheck);
        final RadioButton nikPoshCheck =  findViewById(R.id.nikPoshCheck);
        final RadioButton whoRepCheck =  findViewById(R.id.whoRepCheck);

        DownloadName = new HashMap<>();
        DownloadName.put(R.id.iecChildCheck,iecChildCheck.getText().toString()+" (PDF)");
        DownloadName.put(R.id.standTBCheck,standTBCheck.getText().toString()+" (PDF)");
        DownloadName.put(R.id.natStatCheck,natStatCheck.getText().toString()+" (PDF)");
        DownloadName.put(R.id.nikPoshCheck,nikPoshCheck.getText().toString()+" (PDF)");
        DownloadName.put(R.id.whoRepCheck,whoRepCheck.getText().toString()+" (PDF)");

        radioGroup = findViewById(R.id.radiogrp);

        downloadlink = new HashMap<>();
        downloadlink.put(R.id.whoRepCheck,"https://apps.who.int/iris/bitstream/handle/10665/336069/9789240013131-eng.pdf");
        downloadlink.put(R.id.iecChildCheck,"https://challengetb.org/publications/tools/country/Viet_Nam_IEC_Materials_for_Childhood_TB_English.pdf");
        downloadlink.put(R.id.standTBCheck,"https://apps.who.int/iris/rest/bitstreams/926176/retrieve");
        downloadlink.put(R.id.natStatCheck,"https://tbcindia.gov.in/WriteReadData/NSP%20Draft%2020.02.2017%201.pdf");
        downloadlink.put(R.id.nikPoshCheck,"https://tbcindia.gov.in/WriteReadData/l892s/6851513623Nutrition%20support%20DBT%20Scheme%20details.pdf");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
            }
        });
        downloadFileButton = (Button) findViewById(R.id.downloadFileButton);
        proInterButton = (Button) findViewById(R.id.proInterButton);
        downloadFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!networkConnectVal()){
                    Toast.makeText(BookDownloader.this, "Network is NOT Connected! :(", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permissionArray = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissionArray, STORAGE_PERMISSION_VAL);
                        } else {
                            startDownload();
                        }
                    } else {
                        startDownload();
                    }
                }
            }
        });

        proInterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent booIntent = new Intent(BookDownloader.this, MainScreening.class);
                startActivity(booIntent);
            }
        });

    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_VAL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                }
                else {
                    Toast.makeText(BookDownloader.this, "STORAGE PERMISSION DENIED! :(", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public boolean networkConnectVal() {
        try {
            ConnectivityManager connectManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectManager.getActiveNetworkInfo();
            if(netInfo != null && netInfo.isAvailable() && netInfo.isConnected()) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            Log.e("NetConnect Exception", e.getMessage());
        }
        return false;
    }
    public void startDownload() {
        int selected = radioGroup.getCheckedRadioButtonId();
        String link = downloadlink.get(selected);
        downloadName = DownloadName.get(selected);
        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(link));
        downloadRequest.setTitle(downloadName);
        downloadRequest.setDescription("Download in Progress...");
        downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadName + ".pdf");
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(downloadRequest);
        Toast.makeText(BookDownloader.this, "Downloading File...", Toast.LENGTH_SHORT).show();
    }
}
