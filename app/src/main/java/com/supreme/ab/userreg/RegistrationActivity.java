package com.supreme.ab.userreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.supreme.ab.userreg.ui.login.LoginActivity;

public class RegistrationActivity extends AppCompatActivity {
    Button registerButton, signIn;
    EditText fName, username, email, phoneNo, password1, password2;
    RadioGroup radioGroup;
    public static dataModelHelper dataModelHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fName= findViewById(R.id.editText2);
        username= findViewById(R.id.editText3);
        email=findViewById(R.id.editText4);
        phoneNo=findViewById(R.id.editText5);
        password1=findViewById(R.id.editText6);
        password2=  findViewById(R.id.editText7);
        registerButton=findViewById(R.id.button);
        signIn=findViewById(R.id.register);
        dataModelHelper= new dataModelHelper(getApplicationContext());
//        try {
//            dataModelHelper.queryData("CREATE TABLE IF NOT EXISTS Students(id INTEGER PRIMARY KEY AUTOINCREMENT, FullName Text, username varchar, Gender varchar, Email varchar, PhoneNo varchar, Password varchar)");
//        }

//        catch (Exception e){
//            e.printStackTrace();
//        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPass(password1.getText().toString(),password2.getText().toString())){
                    try {
                        dataModelHelper.insertRecord(
                                fName.getText().toString(),
                                username.getText().toString().trim(),
                                checkGender(),
                                email.getText().toString().trim(),
                                phoneNo.getText().toString(),
                                password1.getText().toString()

                        );
                        Log.d("Database Operation", "One row Affected");
                        Toast.makeText(getApplicationContext(),"Successfully Registered, Log IN",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        }



               // startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                }
                else
                    Toast.makeText(getApplicationContext(),"Password Don't Match",Toast.LENGTH_SHORT).show();
            }
        }

        );
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });

    }
   // @Override
    protected void onCreate(SQLiteDatabase db){
        String sql= "CREATE TABLE IF NOT EXISTS Students(id INTEGER PRIMARY KEY AUTOINCREMENT, FullName varchar, username varchar, Gender varchar, Email varchar, PhoneNo varchar, Password varchar)";
        db.execSQL(sql);

    }
    protected boolean checkPass(String p1, String p2){
        return p1.equals(p2);
    }
    public String checkGender(){
        radioGroup=findViewById(R.id.gender);
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.male:
                return "Male";
            case R.id.female:
                return "Female";
        }
        return null;
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
                break;
            default:
                //Do Nothing
        }
        return super.onOptionsItemSelected(item);
    }


}
