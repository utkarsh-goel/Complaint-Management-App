package com.example.utkarshgoel.cma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    public String JSON_URL;

    Spinner hostel;
    Spinner room;
    TextView username;
    EditText fname;
    EditText mname;
    EditText lname;
    EditText mnum;
    String uname;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        JSON_URL = getResources().getString(R.string.URL_SERVER)+"profile.php";
        username=(TextView)findViewById(R.id.uname);
        update=(Button)findViewById(R.id.update);
        fname=(EditText)findViewById(R.id.fname);
        mname=(EditText)findViewById(R.id.mname);
        lname=(EditText)findViewById(R.id.lname);
        mnum=(EditText)findViewById(R.id.mnum);

        hostel=(Spinner)findViewById(R.id.hostel);
        ArrayAdapter<CharSequence> hstl=ArrayAdapter.createFromResource(this,R.array.hostel, android.R.layout.simple_spinner_item);
        hstl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostel.setAdapter(hstl);

        room=(Spinner)findViewById(R.id.roomnum);
        ArrayAdapter<CharSequence> roomno=ArrayAdapter.createFromResource(this,R.array.room_num, android.R.layout.simple_spinner_item);
        roomno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        room.setAdapter(roomno);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            uname = extras.getString("USERNAME");
        username.setText(uname);

    update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (username.getText().toString().isEmpty() == true ||
                    fname.getText().toString().isEmpty() == true ||
                    lname.getText().toString().isEmpty() == true ||
                    mnum.getText().toString().isEmpty() == true)
                Toast.makeText(Profile.this, "Required field(s) missing", Toast.LENGTH_LONG).show();
            else
                sendRequest();
        }
    });
    }

    private void sendRequest(){
        // StringRequest constructor (in this case) is taking three parameters.
        // The first one is the URL from where we will get JSON String.
        // In the other two methods we have to create listeners.

        final StringRequest stringRequest=new StringRequest(Request.Method.POST,JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Profile.this,response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",username.getText().toString());
                params.put("fname",fname.getText().toString());
                params.put("mname",mname.getText().toString());
                params.put("lname",lname.getText().toString());
                params.put("contact",mnum.getText().toString());
                params.put("hostel",hostel.getSelectedItem().toString());
                params.put("room",room.getSelectedItem().toString());
                return params;
            }
        };
        //So we have added our listeners the onResponse method which will get the server response.
        // And we are passing the string to our showJSON() method.
        //And if we get any error then onErrorResponse method will execute. We are displaying the error in a Toast.

        //to make the above request work we need to add it on a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);
        requestQueue.add(stringRequest);
    }

}
