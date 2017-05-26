package id.firman.app.wisata;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Firman on 5/22/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private AppPreference appPreference;
    private DelayAsync mDelayAsync;
    public boolean isFirstStart;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        appPreference = new AppPreference(SplashScreenActivity.this);

        mDelayAsync = new DelayAsync();
        mDelayAsync.execute();
    }

    @Override
    protected void onDestroy() {
        mDelayAsync.cancel(true);
        super.onDestroy();
    }


    class DelayAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = null;
            if (appPreference.getUserId().equals("") && appPreference.getEmail().equals("")) {
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            } else {
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            }

            startActivity(intent);
            finish();
        }


    }


}
