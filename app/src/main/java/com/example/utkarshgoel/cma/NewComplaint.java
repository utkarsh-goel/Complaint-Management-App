package com.example.utkarshgoel.cma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewComplaint extends AppCompatActivity {

    Spinner department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);

        department=(Spinner)findViewById(R.id.department);
        ArrayAdapter<CharSequence> dept=ArrayAdapter.createFromResource(this,R.array.department, android.R.layout.simple_spinner_item);
        dept.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(dept);
    }

}
