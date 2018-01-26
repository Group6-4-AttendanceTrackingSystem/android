package com.example.android.attendancetrackingsystem6_4;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectWeekActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String weekSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Select the week for attendance token");
        setContentView(R.layout.activity_select_week);
        Spinner spinner = (Spinner) findViewById(R.id.week_spinner);
        Button button = (Button) findViewById(R.id.confirm_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sessionKey = getIntent().getStringExtra("sessionKey");
                String email = getIntent().getStringExtra("email");

                Intent intent = new Intent(getBaseContext(), MainActivity.class);

                intent.putExtra("sessionKey",sessionKey);
                intent.putExtra("email",email);
                intent.putExtra("week",weekSelected);
                startActivity(intent);
            }
        });
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.week_spinner_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
        String[] weeks = getResources().getStringArray(R.array.week_spinner_array);
        String week = weeks[pos];
        weekSelected = String.valueOf(Integer.parseInt(week)-1);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}