package com.robicrufarm.camaron;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class RobicRufarm extends AppCompatActivity {
    private String TAG = this.getClass().getName();
    @VisibleForTesting
    public ProgressDialog mProgressDialog;
    public Double phValueDB;
    public Double doValueDB;
    public Double tempValueDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void startActivity(final Class activity, Context context) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("bundle", (Bundle) null);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public Double getPhValueDB() {
        return phValueDB;
    }

    public void setPhValueDB(Double phValueDB) {
        this.phValueDB = phValueDB;
    }

    public Double getDoValueDB() {
        return doValueDB;
    }

    public void setDoValueDB(Double doValueDB) {
        this.doValueDB = doValueDB;
    }

    public Double getTempValueDB() {
        return tempValueDB;
    }

    public void setTempValueDB(Double tempValueDB) {
        this.tempValueDB = tempValueDB;
    }

}
