package com.example.gestaoeventosacademicos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CertificadoActivity extends AppCompatActivity {

    private EditText editNomeAluno, editNomeEvento;
    private Button btnGerarCertificado;
    private TextView txtCertificado;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado);

        db = new DatabaseHelper(this);

        editNomeAluno = findViewById(R.id.editNomeAluno);
        editNomeEvento = findViewById(R.id.editNomeEvento);
        btnGerarCertificado = findViewById(R.id.btnGerarCertificado);
        txtCertificado = findViewById(R.id.txtCertificado);

        btnGerarCertificado.setOnClickListener(v -> gerarCertificado());
    }

    private void gerarCertificado() {
        String nomeAluno = editNomeAluno.getText().toString().trim();
        String evento = editNomeEvento.getText().toString().trim();

        if (nomeAluno.isEmpty()) {
            editNomeAluno.setError("Informe o nome do participante");
            return;
        }

        if (evento.isEmpty()) {
            editNomeEvento.setError("Informe o nome do evento");
            return;
        }

        boolean temPresenca = db.verificarPresenca(nomeAluno, evento);

        if (!temPresenca) {
            Toast.makeText(this, "Certificado não liberado. Presença não confirmada.", Toast.LENGTH_LONG).show();
            return;
        }

        String dataAtual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        boolean sucesso = db.gerarCertificado(nomeAluno, evento, dataAtual);

        if (sucesso) {
            String certificado =
                    "CERTIFICADO\n\n" +
                            "Certificamos que\n\n" +
                            nomeAluno + "\n\n" +
                            "participou do evento\n\n" +
                            evento + "\n\n" +
                            "realizado pela plataforma Gestão de Eventos Acadêmicos.\n\n" +
                            "Data de emissão: " + dataAtual;

            txtCertificado.setText(certificado);

            Toast.makeText(this, "Certificado gerado com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao gerar certificado", Toast.LENGTH_SHORT).show();
        }
    }
}