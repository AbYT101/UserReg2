package com.supreme.ab.userreg;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConf {
    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String pref="com.supreme.ab.userreg.SharedPreference_LogInPreference";
    private static final String prefStaus="com.supreme.ab.userreg.SharedPreference_LogInStatusPreference";
    public SharedPreferenceConf(Context context){
        this.context=context;
        sharedPreferences= context.getSharedPreferences(pref, Context.MODE_PRIVATE);

    }
    public void writeLogInStatus(boolean bool){
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(prefStaus, bool);
        editor.commit();
    }
    public boolean readLogInStatus(){
        return sharedPreferences.getBoolean(prefStaus,false);
    }


}
