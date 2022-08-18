package com.example.examenad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.examenad.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText usernameTxt, emailTxt;
    private Retrofit retrofit;
    private CheckBox recordarme;
    private User usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking XML to Java
        usernameTxt = findViewById(R.id.username);
        emailTxt = findViewById(R.id.email);
        recordarme = (CheckBox) findViewById(R.id.recordarme);


        // Handling shared preferences (remember me)
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        usernameTxt.setText(preferences.getString("username", ""));
        emailTxt.setText(preferences.getString("email", ""));
        recordarme.setChecked(preferences.getBoolean("recordarme", false));

        // retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rememberMe();
    }


    public void rememberMe() {
        if (recordarme.isChecked()) {
            SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", usernameTxt.getText().toString());
            editor.putString("email", emailTxt.getText().toString());
            editor.putBoolean("recordarme", recordarme.isChecked());
            editor.commit();
        } else {
            deleteSharedPreferences("data");
        }
    }

    public void aceptar(View view){
        Context context = view.getContext();
        // control de los campos
        String username = usernameTxt.getText().toString();
        String email = emailTxt.getText().toString();
        if(username.equals("") || email.equals("")) {
            showAlert(context,"CAMPOS VACIOS", "Por favor, controle que todos los campos sean llenos");
            return;
        }

        ServiceAPI service = retrofit.create(ServiceAPI.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("email", email);
        Call<List<User>> userResponseCall = service.getUser(params);

        userResponseCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() != 0){
                        usuario = response.body().get(0);
                        Intent intent = new Intent(context, CargaUsuarioActivity1.class);
                        startActivity(intent);
                    }
                    else {
                        showAlert(context,"DATOS INGRESADOS INVALIDOS", "Por favor, controle los datos y reintente");
                        return;
                    }
                }
                else {
                    showAlert(context,"MALA RESPUESTA DEL SERVIDOR", "Por favor, espere y reintente mas tarde");
                    return;
                }


            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                showAlert(context,"CONEXION NO DISPONIBLE", "Por favor, verifique tu conexion y reintente");
                return;
            }
        });



    }

    public void configuracion(View view){
        Intent intent = new Intent(view.getContext(), ConfiguracionActivity.class);
        startActivity(intent);
    }

    public static void showAlert(Context context, String title, String msg){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

}