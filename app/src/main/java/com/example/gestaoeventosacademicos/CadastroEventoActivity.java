package com.example.gestaoeventosacademicos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroEventoActivity extends AppCompatActivity {

    private EditText editTitulo, editDescricao, editData, editLocal;
    private Button btnSalvar;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        db = new DatabaseHelper(this);

        iniciarComponentes();

        btnSalvar.setOnClickListener(v -> salvarEvento());
    }

    private void iniciarComponentes() {
        editTitulo = findViewById(R.id.editTitulo);
        editDescricao = findViewById(R.id.editDescricao);
        editData = findViewById(R.id.editData);
        editLocal = findViewById(R.id.editLocal);
        btnSalvar = findViewById(R.id.btnSalvar);
    }

    private void salvarEvento() {
        String titulo = editTitulo.getText().toString().trim();
        String descricao = editDescricao.getText().toString().trim();
        String data = editData.getText().toString().trim();
        String local = editLocal.getText().toString().trim();

        if (titulo.isEmpty()) {
            editTitulo.setError("Informe o título do evento");
            return;
        }

        if (descricao.isEmpty()) {
            editDescricao.setError("Informe a descrição");
            return;
        }

        if (data.isEmpty()) {
            editData.setError("Informe a data");
            return;
        }

        if (local.isEmpty()) {
            editLocal.setError("Informe o local");
            return;
        }

        boolean sucesso = db.inserirEvento(titulo, descricao, data, local);

        if (sucesso) {
            Toast.makeText(this, "Evento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

            editTitulo.setText("");
            editDescricao.setText("");
            editData.setText("");
            editLocal.setText("");

            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar evento", Toast.LENGTH_SHORT).show();
        }
    }
}