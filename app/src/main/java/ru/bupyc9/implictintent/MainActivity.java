package ru.bupyc9.implictintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        mContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, "test")
                    .putExtra(Intent.EXTRA_SUBJECT, "subject");

                i = Intent.createChooser(i, "выбери меня");

                startActivity(i);
            }
        });
    }
}
