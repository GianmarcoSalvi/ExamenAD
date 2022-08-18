package com.example.examenad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.examenad.model.Company;
import com.example.examenad.model.User;

public class CargaUsuarioActivity1 extends AppCompatActivity {
    private User usuario;
    private EditText nameTxt, usernameTxt, emailTxt, phoneTxt, websiteTxt;
    private EditText companyNameTxt, catchPhraseTxt, bsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_usuario1);

        usuario = new User();
        // linking
        nameTxt = findViewById(R.id.nameTxt);
        usernameTxt = findViewById(R.id.usernameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        websiteTxt = findViewById(R.id.websiteTxt);

        companyNameTxt = findViewById(R.id.companyNameTxt);
        catchPhraseTxt = findViewById(R.id.companyCatchPhraseTxt);
        bsTxt = findViewById(R.id.companyBsTxt);
    }

    public void siguiente(View view){
        usuario.setName(nameTxt.getText().toString());
        usuario.setUsername(usernameTxt.getText().toString());
        usuario.setEmail(emailTxt.getText().toString());
        usuario.setPhone(phoneTxt.getText().toString());
        usuario.setWebsite(websiteTxt.getText().toString());

        Company c = new Company();
        c.setName(companyNameTxt.getText().toString());
        c.setCatchPhrase(catchPhraseTxt.getText().toString());
        c.setBs(bsTxt.getText().toString());

        usuario.setCompany(c);

        Intent intent = new Intent(this, CargaUsuarioActivity2.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }
}