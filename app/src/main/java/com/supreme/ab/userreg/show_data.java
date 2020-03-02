package com.supreme.ab.userreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class show_data extends AppCompatActivity {
    TextView name, username, gender, email, phoneNo;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        Bundle b=getIntent().getExtras();
        name=findViewById(R.id.fullName2);
        username=findViewById(R.id.username3);
        gender=findViewById(R.id.gend);
        email=findViewById(R.id.email);
        phoneNo=findViewById(R.id.phoneNo);
        btn=findViewById(R.id.backToHome);
        try {
            name.setText(b.get("name22").toString());
            phoneNo.setText(b.get("phoneN").toString());
            email.setText(b.get("email").toString());
            gender.setText(b.get("gender").toString());
            username.setText(b.get("username").toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutdev:
                //
            case R.id.exitApp:
                System.exit(1);
                android.os.Process.killProcess(getTaskId());
                break;
            default:
                //Do Nothing
        }
        return super.onOptionsItemSelected(item);
    }
}
