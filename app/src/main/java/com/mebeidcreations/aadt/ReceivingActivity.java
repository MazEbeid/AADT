package com.mebeidcreations.aadt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ReceivingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving);

        TextView receivedDataTextView = findViewById(R.id.recieved_data_textview);

        String receivedData = getIntent().getStringExtra("data");
        receivedDataTextView.setText(receivedDataTextView.getText().toString()+" "+receivedData);
    }
}
