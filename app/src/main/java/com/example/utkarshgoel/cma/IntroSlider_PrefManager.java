package com.example.utkarshgoel.cma;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Utkarsh Goel on 21-Jun-16.
 */
public class IntroSlider_PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;    //will be used to edit the preferences
    Context _context;                   //retrieves the current context

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "IntroSlider";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public IntroSlider_PrefManager(Context _context) {
        this._context = _context;
        pref=_context.getSharedPreferences(PREF_NAME,PRIVATE_MODE); //Preferences of file name PREF_NAME are stored in default mode operation
       editor=pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime);
    }

    public boolean isFirstTimeLaunch()
    {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);//true in case the parameter doesn't exist.
    }
}
