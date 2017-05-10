package ru.bupyc9.implictintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TakeActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take);

        mTextView = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();

        mTextView.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
    }
}
