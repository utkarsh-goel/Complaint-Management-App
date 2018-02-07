package com.example.utkarshgoel.cma;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    //public static final String JSON_URL = "http://192.168.1.8/CMA/login.php";
    String JSON_URL;

    EditText username;
    EditText password;
    View rootView;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        JSON_URL=getResources().getString(R.string.URL_SERVER)+"login.php";

        password=(EditText)rootView.findViewById(R.id.password);
        ImageView showpassword=(ImageView)rootView.findViewById(R.id.showpassword);
        Button signin=(Button)rootView.findViewById(R.id.signin);
        username=(EditText)rootView.findViewById(R.id.username);

    signin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(username.getText().toString().isEmpty()==true ||
                    password.getText().toString().isEmpty()==true)
                Toast.makeText(Login.this.getActivity(),"Required field(s) missing",Toast.LENGTH_LONG).show();
            else
                sendRequest();
        }
    });

    showpassword.setOnTouchListener(new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case MotionEvent.ACTION_UP:
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
            }
            return true;
        }
    });
    return rootView;
    }

    private void sendRequest(){
        // StringRequest constructor (in this case) is taking three parameters.
        // The first one is the URL from where we will get JSON String.
        // In the other two methods we have to create listeners.

        final StringRequest stringRequest=new StringRequest(Request.Method.POST,JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "" + response.toString(), Toast.LENGTH_LONG).show();
                        if (response.toString().equals("LogIn Successful")){
                            Intent intent = new Intent(Login.this.getActivity(), Navigation_Drawer.class);
                            intent.putExtra("USERNAME",username.getText().toString());
                            startActivity(intent);
                            Login.this.getActivity().finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
        }) {
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                    params.put("username",username.getText().toString());
                    params.put("password",password.getText().toString());
                return params;
            }
    };
        //So we have added our listeners the onResponse method which will get the server response.
        // And we are passing the string to our showJSON() method.
        //And if we get any error then onErrorResponse method will execute. We are displaying the error in a Toast.

        //to make the above request work we need to add it on a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
