package com.supreme.ab.userreg.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.supreme.ab.userreg.DataModelAdapter;
import com.supreme.ab.userreg.MainActivity;
import com.supreme.ab.userreg.R;
import com.supreme.ab.userreg.RecyclerView;
import com.supreme.ab.userreg.RegistrationActivity;
import com.supreme.ab.userreg.SharedPreferenceConf;
import com.supreme.ab.userreg.ui.login.LoginViewModel;
import com.supreme.ab.userreg.ui.login.LoginViewModelFactory;
import com.supreme.ab.userreg.dataModelHelper;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    SharedPreferenceConf sharedPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button RegisterButton = findViewById(R.id.register);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        sharedPreference= new SharedPreferenceConf(getApplicationContext());
        if(sharedPreference.readLogInStatus())
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
                dataModelHelper dm= new dataModelHelper(getApplicationContext());
                Cursor cr=dm.getAllData();
                while (cr.moveToNext()) {
                    String usr = cr.getString(2);
                    String pass = cr.getString(6);

                    if (usernameEditText.getText().toString().equals(usr) && passwordEditText.getText().toString().equals(pass)) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        sharedPreference.writeLogInStatus(true);
                    }
                    if(cr.moveToNext()){
                        continue;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Invalid UserName or Password", Toast.LENGTH_SHORT).show();
                    }
                }




                //startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_2, menu);
        return true;
    }



    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        System.exit(1);
    }
}
