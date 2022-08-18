package com.example.examenad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;

public class ConfiguracionActivity extends AppCompatActivity {
    private RadioGroup opcionesColor, opcionesLetra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configuracion);
        opcionesColor = findViewById(R.id.opciones_color);
        opcionesLetra = findViewById(R.id.opciones_letra);
    }


    public void grabar(View view){

    }
}