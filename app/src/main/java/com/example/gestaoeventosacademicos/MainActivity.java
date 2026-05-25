package com.example.gestaoeventosacademicos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtUsuario;

    Button btnCadastrarEvento,
            btnListarEventos,
            btnPresenca,
            btnCertificado,
            btnFeedback,
            btnLimparEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentes();
        carregarUsuario();
        configurarCliques();
    }

    private void iniciarComponentes() {

        txtUsuario =
                findViewById(R.id.txtUsuario);

        btnCadastrarEvento =
                findViewById(R.id.btnCadastrarEvento);

        btnListarEventos =
                findViewById(R.id.btnListarEventos);

        btnPresenca =
                findViewById(R.id.btnPresenca);

        btnCertificado =
                findViewById(R.id.btnCertificado);

        btnFeedback =
                findViewById(R.id.btnFeedback);

        btnLimparEventos =
                findViewById(R.id.btnLimparEventos);
    }

    private void carregarUsuario() {

        SharedPreferences pref =
                getSharedPreferences(
                        "dados",
                        MODE_PRIVATE
                );

        String usuario =
                pref.getString(
                        "usuario",
                        "Usuário"
                );

        txtUsuario.setText(
                "Bem-vindo, " + usuario
        );
    }

    private void configurarCliques() {

        btnCadastrarEvento.setOnClickListener(v ->
                abrirTela(CadastroEventoActivity.class));

        btnListarEventos.setOnClickListener(v ->
                abrirTela(ListaEventosActivity.class));

        btnPresenca.setOnClickListener(v ->
                abrirTela(PresencaActivity.class));

        btnCertificado.setOnClickListener(v ->
                abrirTela(CertificadoActivity.class));

        btnFeedback.setOnClickListener(v ->
                abrirTela(FeedbackActivity.class));

        btnLimparEventos.setOnClickListener(v -> {

            DatabaseHelper db =
                    new DatabaseHelper(this);

            db.limparEventos();

            Toast.makeText(
                    this,
                    "Eventos apagados",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    private void abrirTela(Class<?> tela) {
        Intent intent =
                new Intent(
                        MainActivity.this,
                        tela
                );

        startActivity(intent);
    }
}