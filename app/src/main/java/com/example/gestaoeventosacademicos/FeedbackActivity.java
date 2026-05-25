package com.example.gestaoeventosacademicos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    private EditText editNomeEvento, editComentario;
    private RatingBar ratingNota;
    private Button btnEnviarFeedback;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        db = new DatabaseHelper(this);

        iniciarComponentes();

        btnEnviarFeedback.setOnClickListener(v -> enviarFeedback());
    }

    private void iniciarComponentes() {
        editNomeEvento = findViewById(R.id.editNomeEvento);
        editComentario = findViewById(R.id.editComentario);
        ratingNota = findViewById(R.id.ratingNota);
        btnEnviarFeedback = findViewById(R.id.btnEnviarFeedback);
    }

    private void enviarFeedback() {
        String evento = editNomeEvento.getText().toString().trim();
        String comentario = editComentario.getText().toString().trim();
        int nota = (int) ratingNota.getRating();

        if (evento.isEmpty()) {
            editNomeEvento.setError("Informe o nome do evento");
            return;
        }

        if (comentario.isEmpty()) {
            editComentario.setError("Digite seu comentário");
            return;
        }

        if (nota == 0) {
            Toast.makeText(this, "Escolha uma nota para o evento", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean sucesso = db.inserirFeedback(evento, comentario, nota);

        if (sucesso) {
            Toast.makeText(this, "Feedback enviado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao enviar feedback", Toast.LENGTH_SHORT).show();
        }
    }
}