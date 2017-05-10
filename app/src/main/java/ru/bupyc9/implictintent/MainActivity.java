package ru.bupyc9.implictintent;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button mContactsButton;
    private Button mSendTextButton;
    private Button mCallButton;
    private TextView mContactView;

    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_CONTACT_PHONE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactsButton = (Button) findViewById(R.id.button);
        mSendTextButton = (Button) findViewById(R.id.button2);
        mCallButton = (Button) findViewById(R.id.call);
        mContactView = (TextView) findViewById(R.id.textView);

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        final Intent callContact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);

        PackageManager packageManager = getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mContactsButton.setEnabled(false);
        }
        if (packageManager.resolveActivity(callContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mCallButton.setEnabled(false);
        }

        mContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        mSendTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = ShareCompat.IntentBuilder.from(MainActivity.this)
                    .setType("text/plain")
                    .setText("test")
                    .setSubject("subject")
                    .getIntent();

                i = Intent.createChooser(i, "выбери меня");

                startActivity(i);
            }
        });

        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(callContact, REQUEST_CONTACT_PHONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CONTACT:
                processRequestContact(data);
                break;
            case REQUEST_CONTACT_PHONE:
                processRequestContactPhone(data);
        }
    }

    private void processRequestContact(Intent data) {
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

    private void processRequestContactPhone(Intent data) {
        Uri contactUri = data.getData();

        String[] queryFields = new String[] {
            ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor c = getContentResolver().query(contactUri, queryFields, null, null, null);

        try {
            if (c.getCount() == 0) {
                return;
            }

            c.moveToFirst();
            String phone = c.getString(0);
            callPhone(phone);
        } finally {
            c.close();
        }
    }

    private void callPhone(String phone) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(i);
    }
}
