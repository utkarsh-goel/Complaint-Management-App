package com.example.utkarshgoel.cma;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
public class SignUp extends Fragment {

    ImageView check;
    EditText password1;
    EditText password2;
    EditText username;
    String password;

    //public static final String JSON_URL = "http://192.168.1.8/CMA/signup.php";
    String JSON_URL;

    public SignUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);

        JSON_URL = getResources().getString(R.string.URL_SERVER)+"signup.php";

        check=(ImageView)view.findViewById(R.id.check);
        password1=(EditText)view.findViewById(R.id.pswrd);
        password2=(EditText)view.findViewById(R.id.cpswrd);
        username=(EditText)view.findViewById(R.id.username);
        Button signup=(Button)view.findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty()==true ||
                        password1.getText().toString().isEmpty()==true ||
                            password2.getText().toString().isEmpty()==true)
                    Toast.makeText(SignUp.this.getActivity(),"Required field(s) missing",Toast.LENGTH_LONG).show();
                else if(password2.getText().toString().equals(password1.getText().toString()))
                    sendRequest();
                else
                    password2.setError("Passwords do not match");
            }
        });
    return view;
    }

    private void sendRequest(){
        // StringRequest constructor (in this case) is taking three parameters.
        // The first one is the URL from where we will get JSON String.
        // In the other two methods we have to create listeners.

        final StringRequest stringRequest=new StringRequest(Request.Method.POST,JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        if (response.toString().equals("SignUp Successful")){
                            Intent intent = new Intent(SignUp.this.getActivity(), Navigation_Drawer.class);
                            intent.putExtra("USERNAME",username.getText().toString());
                            startActivity(intent);
                            SignUp.this.getActivity().finish();
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
                params.put("password",password2.getText().toString());
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
