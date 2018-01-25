package be.heh.homeband.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.heh.homeband.R;
import be.heh.homeband.app.HomebandApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Action Bar
        getSupportActionBar().hide();

        // Hide Navigation Bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        // Set ContentView
        setContentView(R.layout.activity_login);


    }

    public void onClickConnexion(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost/homeband-api/")
                .build();

        HomebandApi service = retrofit.create(HomebandApi.class);
        service.connexion("carole","testtest",1).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()) {
                    try{
                        JSONObject res = new JSONObject(response.body().toString());

                       boolean status = (Boolean) res.get("status");
                        CharSequence message;
                        if(status==true){
                            message="connexion reussie";
                        }else{
                            message="echec de la connexion";
                        }

                        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }catch(JSONException e){
                    }

                    Log.d("MainActivity", "posts loaded from API");
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
               // showErrorMessage();
                Log.d("MainActivity", "error loading from API");

            }
        });
    }
}
