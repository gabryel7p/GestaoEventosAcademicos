package com.example.gestaoeventosacademicos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PresencaActivity extends AppCompatActivity {

    private EditText editNomeAluno, editNomeEvento;
    private Button btnMarcarPresenca;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenca);

        db = new DatabaseHelper(this);

        editNomeAluno = findViewById(R.id.editNomeAluno);
        editNomeEvento = findViewById(R.id.editNomeEvento);
        btnMarcarPresenca = findViewById(R.id.btnMarcarPresenca);

        btnMarcarPresenca.setOnClickListener(v -> marcarPresenca());
    }

    private void marcarPresenca() {
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

        boolean sucesso = db.marcarPresenca(nomeAluno, evento, 1);

        if (sucesso) {
            Toast.makeText(this, "Presença confirmada!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao confirmar presença", Toast.LENGTH_SHORT).show();
        }
    }
}