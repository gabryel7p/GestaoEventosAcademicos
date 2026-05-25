package com.example.gestaoeventosacademicos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editUsuario;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsuario = findViewById(R.id.editUsuario);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(v -> {

            String usuario = editUsuario.getText().toString();

            if(usuario.isEmpty()) {
                Toast.makeText(this, "Digite o usuário", Toast.LENGTH_SHORT).show();
            } else {

                SharedPreferences preferences = getSharedPreferences("dados", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("usuario", usuario);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
