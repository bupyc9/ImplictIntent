package ru.bupyc9.implictintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mContactsButton;
    private Button mSendTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactsButton = (Button) findViewById(R.id.button);
        mContactsButton = (Button) findViewById(R.id.button2);


    }
}
