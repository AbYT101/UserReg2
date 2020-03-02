package com.supreme.ab.userreg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.supreme.ab.userreg.R;
import com.supreme.ab.userreg.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List userDataList;
    dataModelHelper modelHelper;
    SharedPreferenceConf sharedPreference;
    SQLiteDatabase db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        modelHelper = new dataModelHelper(getApplicationContext());
        userDataList = new ArrayList<dataModel>();
        sharedPreference = new SharedPreferenceConf(getApplicationContext());

        getData();
        new Intent(this, show_data.class);
        final DataModelAdapter dataModelAdapter = new DataModelAdapter(getApplicationContext(), userDataList);
        recyclerView.setAdapter(dataModelAdapter);
//        dataModelAdapter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//        });
        dataModelAdapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Cursor res = modelHelper.query("Select * from Students", null);
                String name=v.getTag().toString();
                modelHelper.deleteData(name);
                Toast.makeText(getApplicationContext(), name+" Deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                sharedPreference.writeLogInStatus(false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.aboutdev:
                //
            case R.id.exitApp:

                android.os.Process.killProcess(getTaskId());
                System.exit(1);
                break;
            default:
                //Do Nothing
        }
        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public void onBackPressed(){
//        Toast.makeText(getApplicationContext(),"You pressed Back - Application Exited", Toast.LENGTH_SHORT).show();
//        System.exit(1);
//    }

    public void getData() {
        try {
            Cursor res = modelHelper.getAllData();
            if (res.getCount() == 0) {
                return;
            } else {
                while (res.moveToNext()){
                    String id= res.getString(0);
                    String fullName = res.getString(1);
                    String userName = res.getString(2);
                    String gender = res.getString(3);
                    String email = res.getString(4);
                    String phoneNo = res.getString(5);
                    String Password = res.getString(6);
                    userDataList.add(new dataModel(id,fullName, userName, gender, email, phoneNo, Password));
                }
            }
        } catch (Exception E) {
            android.widget.Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }

    }
    public void onClick(View v) {
        dataModel dm=(dataModel)v.getTag();
        intent.putExtra("name", dm.fullName );
        intent.putExtra("phoneN", dm.phoneNo );
        intent.putExtra("email", dm.email );
        Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        System.exit(1);
    }
}
