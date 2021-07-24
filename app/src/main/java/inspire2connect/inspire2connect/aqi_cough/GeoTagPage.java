package inspire2connect.inspire2connect.aqi_cough;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import inspire2connect.inspire2connect.R;

import static inspire2connect.inspire2connect.aqi_cough.UserInfoScreen.currLat;
import static inspire2connect.inspire2connect.aqi_cough.UserInfoScreen.currLong;

public class GeoTagPage extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map2;
    public String finalAQI;
    public String myLocURL;

    double actualLat;
    double actualLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geotagpage);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        double prevLat = getIntent().getExtras().getDouble("actLat");
        double prevLong = getIntent().getExtras().getDouble("actLong");
        finalAQI = getIntent().getExtras().getString("aqiVal");
        actualLat = prevLat;
        actualLong = prevLong;
//        if (currLat < 0.2){
//            actualLat = currLat;
//            actualLong = currLong;
//        }
//        else if (prevLat < 0.2) {
//            actualLat = prevLat;
//            actualLong = prevLong;
//        }
//        else {
//            actualLat = 28.5456;
//            actualLong = 77.2732;
//        }

        Button coughButton = (Button) findViewById(R.id.coughButton);

        coughButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent coughPage = new Intent(GeoTagPage.this, MainScreening.class);
                startActivity(coughPage);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map2 = googleMap;
        map2.addMarker(new MarkerOptions().position(new LatLng(actualLat, actualLong)).title("Your Location").snippet("AQI = " + finalAQI + ", Coordinates (" + actualLat + ", " + actualLong + ")").icon(BitmapDescriptorFactory.defaultMarker(270)));
        map2.addMarker(new MarkerOptions().position(new LatLng(28.639464,77.239105)).title("New Delhi Tuberculosis Centre").snippet("NDTBC From a modest beginning in 1940 as a Model TB Clinic, NDTBC has now grown into a fully functional National level institute for TB and Chest Diseasesimparting health care, training and education are being met in an integrated form here. From the day of its inception, NDTBC nurtured a dream of becoming a National level institute for TB & chest Diseases.").icon(BitmapDescriptorFactory.defaultMarker(30)));
        map2.addMarker(new MarkerOptions().position(new LatLng(28.5287201,77.1891771)).title("National Institute of TB and Respiratory Diseases").snippet("We aim to act as an apex Institute in the country for prevention, control and treatment of Tuberculosis and Allied Diseases. To promote National Tuberculosis Control Programme in the country and to formulate strategies which are socially acceptable and economically feasible in order to assist and strengthen the programme.").icon(BitmapDescriptorFactory.defaultMarker(30)));
        map2.addMarker(new MarkerOptions().position(new LatLng(28.5654877,77.2083398)).title("AIIMS DOTS Centre").snippet("In 1993, Government of India started the Revised National Tuberculosis Control Programme (RNTCP). A model Directly Observed Therapy, Short-Course (DOTS) centre was established at the All India Institute of Medical Sciences (AIIMS) to (i) identify the challenges and opportunities in establishing DOTS centres at tertiary care facilities, (ii) to teach the strategies of RNTCP to medical and paramedical staff, and (iii) to undertake relevant operational research connected with tuberculosis (TB) treatment and control.").icon(BitmapDescriptorFactory.defaultMarker(30)));
        map2.addMarker(new MarkerOptions().position(new LatLng(28.5290115,77.1892954)).title("Lala Ram Saroop T.B Hospital").snippet("This is a specialist hospital and walk-in centre in New Delhi. Amongst other initiatives, it operates the DOTS (directly observed therapy) system and provides much needed health education and support for TB patients in the city.").icon(BitmapDescriptorFactory.defaultMarker(30)));
        map2.addMarker(new MarkerOptions().position(new LatLng(28.5361255,77.2555526)).title("Saans Foundation").snippet("Super Speciality Centre & Nursing Home in South Delhi for Respiratory, Critical Care, Sleep, Allergy, Rehabilitation & Home Care").icon(BitmapDescriptorFactory.defaultMarker(30)));
        map2.addMarker(new MarkerOptions().position(new LatLng(28.6416491,77.2053679)).title("Delhi Heart & Lung Institute").snippet("Delhi Heart & Lung Institute offers a comprehensive Executive Health Check Program which includes an array of heart and lung diagnostics as well as laboratory investigations. The institute’s cutting-edge core competencies are well sustained by an efficient collaboration with support services like Emergency Medicine, Biochemistry, Clinical Pathology, Blood Transfusion, Telemedicine and Facilities Management adhering to world-class modern standards, making the institute comparable with any other setup of its kind in the world.").icon(BitmapDescriptorFactory.defaultMarker(30)));
        map2.addMarker(new MarkerOptions().position(new LatLng(28.6458378,77.1961842)).title("Ramakrishna Mission Medical Centre").snippet("Besides giving treatment to TB patients and chest symptomatics, this clinic also imparts health education to patients and to the community at large.").icon(BitmapDescriptorFactory.defaultMarker(30)));
        map2.addMarker(new MarkerOptions().position(new LatLng(28.67237,77.1865113)).title("Gulabi Bagh Chest Clinic").snippet("DOT CUM MICROSCOPY CENTRE in North Delhi").icon(BitmapDescriptorFactory.defaultMarker(30)));
        LatLng userLoc = new LatLng(actualLat, actualLong);
        map2.moveCamera(CameraUpdateFactory.newLatLng(userLoc));
        map2.animateCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 10));
    }
}
