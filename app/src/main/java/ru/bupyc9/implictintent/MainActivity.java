package ru.bupyc9.implictintent;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button mContactsButton;
    private Button mSendTextButton;
    private TextView mContactView;

    private static final int REQUEST_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactsButton = (Button) findViewById(R.id.button);
        mSendTextButton = (Button) findViewById(R.id.button2);
        mContactView = (TextView) findViewById(R.id.textView);

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        mContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        mSendTextButton.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();

            String[] queryFields = new String[] {
                ContactsContract.Contacts.DISPLAY_NAME
            };

            Cursor c = getContentResolver().query(contactUri, queryFields, null, null, null);

            try {
                if (c.getCount() == 0) {
                    return;
                }

                c.moveToFirst();
                String suspect = c.getString(0);
                mContactView.setText(suspect);
            } finally {
                c.close();
            }

        }
    }
}
