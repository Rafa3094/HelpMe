package com.helpme.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.helpme.ConnectionSQLiteHelper;
import com.helpme.RequestPermissionActivity;
import com.helpme.R;
import com.helpme.User;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static android.support.v4.content.ContextCompat.checkSelfPermission;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LocationListener {
    Button btnHelp;
    ArrayList<HashMap<String, String>> contactsList;
    String message = "";
    LocationManager locMgr;
    String locProvider;
    double lat;
    double lng;
    long minTime;
    float minDistance;
    RequestPermissionActivity requestPermission = new RequestPermissionActivity();

    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Check permissions
        requestPermission.verifyMessagePermissions(this.getContext(), this.getActivity());


        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        contactsList = connectionSQLiteHelper.getContactsList(this.getContext());

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = Objects.requireNonNull(getActivity()).getIntent().getExtras();
        assert bundle != null;


        btnHelp = view.findViewById(R.id.btnHelp);
        btnHelp.setEnabled(false);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (contactsList.size() != 0) {
                        for (int i = 0; i < contactsList.size(); i++) {
                            HashMap<String, String> hashmapData = contactsList.get(i);
                            String name = hashmapData.get("name");
                            String phone = hashmapData.get("phoneNumber");
                            getLocation(name, phone);
                        }

                    } else {
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "You do not have a help contact yet",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

        });

        Switch switchEnableButton = view.findViewById(R.id.switch_enable_button);

        switchEnableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnHelp.setEnabled(true);
                } else {
                    btnHelp.setEnabled(false);
                }
            }
        });

        return view;

    }


    private void sendSMS(String name, String phone, String message) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Message to " + name + " Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    private boolean gpsOn(){
        LocationManager location=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        return (( (location.isProviderEnabled(LocationManager.GPS_PROVIDER)==false) &&
                (location.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==false))?false:true);
    }

    private void getLocation(String name, String phone) {
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        User user = connectionSQLiteHelper.getUserData(this.getContext());

        try {
            if(gpsOn()==true){
                locMgr = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                locProvider = LocationManager.NETWORK_PROVIDER;

                if (checkSelfPermission(Objects.requireNonNull(this.getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(this.getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getActivity(), "You have to accept the permissions first", Toast.LENGTH_LONG).show();

                    return;
                }

                locMgr.requestLocationUpdates(locProvider,2000,1,this);//Actualiza y recupera la ubicación


                    Location lastKnownLocation = locMgr.getLastKnownLocation(locProvider);
                    lat = lastKnownLocation.getLatitude();
                    lng = lastKnownLocation.getLongitude();


                Criteria cr = new Criteria();
                cr.setAccuracy(Criteria.ACCURACY_FINE);

                locProvider = locMgr.getBestProvider(cr, false);
                minTime = 60 * 1000;
                minDistance = 1;

                String googleUrl = "https://maps.google.com/?q=" + lat + "," + lng;

                if (user.getName() == null) {
                    message = "I need your help!\nPlease open this link " + googleUrl + " to know my position";
                } else {
                    message = "I am " + user.getName() + " " + user.getLastName() + " and need your help!\nMy position is " +googleUrl + "\nMy information is\n" + user.getPersonalId() + "\n" + user.getBitrhDate() + "\n" + user.getBlood();
                }
            }else{
                if (user.getName() == null) {
                    message = "I need your help!\nMy GPS is off, I can not send you my location\n";
                } else {
                    message = "I am " + user.getName() + " " + user.getLastName() + " and need your help!\nMy information is\n" + user.getPersonalId() + "\n" + user.getBitrhDate() + "\n" + user.getBlood() + "\n" + "\nMy GPS is off, I can not send you my location";
                }
            }

            message = validateMessage(message);

        sendSMS(name, phone, message);
        }catch(Exception e) {
            Toast.makeText(this.getContext(), "Wait 2 seconds and try again", Toast.LENGTH_LONG).show();
        }
    }



    public String validateMessage(String message) {
        try {
            if (message.length() > 160) {
                message = message.substring(0, 159);
            }
            String normalize = Normalizer.normalize(message, Normalizer.Form.NFD);
            message = normalize.replaceAll("[^\\p{ASCII}]", "");
        }catch(Exception e) {
            Toast.makeText(this.getContext(), "ERROR: " + e, Toast.LENGTH_LONG).show();
        }
        return message;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
