package com.example.examenad;

import static com.example.examenad.MainActivity.showAlert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.examenad.model.Address;
import com.example.examenad.model.Geo;
import com.example.examenad.model.User;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CargaUsuarioActivity2 extends AppCompatActivity {
    private EditText streetTxt, suiteTxt, cityTxt, zipcodeTxt;
    private User usuario;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_usuario2);

        // link
        streetTxt = findViewById(R.id.streetTxt);
        suiteTxt = findViewById(R.id.suitetTxt);
        cityTxt = findViewById(R.id.cityTxt);
        zipcodeTxt = findViewById(R.id.zipcodeTxt);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void subir(View view){
        Context context = view.getContext();

        usuario = (User) getIntent().getSerializableExtra("usuario");

        Geo geo = new Geo();
        geo.setLat("-41.5352");
        geo.setLng("12.2312");

        Address address = new Address();
        address.setStreet(streetTxt.getText().toString());
        address.setSuite(suiteTxt.getText().toString());
        address.setCity(cityTxt.getText().toString());
        address.setZipcode(zipcodeTxt.getText().toString());
        address.setGeo(geo);

        usuario.setAddress(address);
        usuario.setId(11);

        // post
        ServiceAPI service = retrofit.create(ServiceAPI.class);
        Call<User> postResponse = service.postUser(usuario);

        postResponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    showAlert(context,"RESPUESTA EXITOSA", "Usuario subido correctamente");
                    return;
                }
                else {
                    showAlert(context,"MALA RESPUESTA DEL SERVIDOR", "Por favor, espere y reintente mas tarde");
                    return;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showAlert(context,"CONEXION NO DISPONIBLE", "Por favor, verifique tu conexion y reintente");
                return;
            }
        });




    }
}