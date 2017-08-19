package pounpong.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashscreenActivity extends Activity {

    private static final String TAG = "SplashscreenActivity";
    //TODO add delay to show splash screen
    private final long DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*LinearLayout layout = (LinearLayout) RelativeLayout.inflate(this, R.layout.activity_splashscreen, null);
        setContentView(layout);
*/
        try{

            new Handler().postDelayed(new Runnable(){
                                          @Override
                                          public void run(){
                                              Intent mainIntent = new Intent(SplashscreenActivity.this, MainActivity.class);
                                              startActivity(mainIntent);
                                              finish();
                                          }
                                      }
                    , DELAY);
        }
        catch(Exception e){
            Log.e(TAG, e.toString());
        }
    }
}
