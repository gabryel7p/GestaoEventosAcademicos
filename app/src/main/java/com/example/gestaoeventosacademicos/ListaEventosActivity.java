package com.example.gestaoeventosacademicos;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaEventosActivity extends AppCompatActivity {

    private ListView listViewEventos;
    private DatabaseHelper db;

    private ArrayList<String> listaEventos;
    private ArrayList<String> nomesEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        listViewEventos = findViewById(R.id.listViewEventos);
        db = new DatabaseHelper(this);

        exibirEventos();
        configurarClique();
    }

    private void exibirEventos() {

        Cursor cursor = db.listarEventos();

        listaEventos = new ArrayList<>();
        nomesEventos = new ArrayList<>();

        if(cursor.moveToFirst()){

            do{

                String titulo =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("titulo")
                        );

                String data =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("data")
                        );

                String local =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("local")
                        );

                listaEventos.add(
                        titulo +
                                "\nData: " + data +
                                "\nLocal: " + local
                );

                nomesEventos.add(titulo);

            }while(cursor.moveToNext());
        }

        cursor.close();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        listaEventos
                );

        listViewEventos.setAdapter(adapter);
    }


    private void configurarClique(){

        listViewEventos.setOnItemClickListener((parent, view, position, id) -> {

            String eventoSelecionado =
                    nomesEventos.get(position);

            mostrarOpcaoInscricao(eventoSelecionado);

        });

    }


    private void mostrarOpcaoInscricao(String evento){

        new AlertDialog.Builder(this)
                .setTitle("Inscrição")
                .setMessage(
                        "Deseja se inscrever no evento?\n\n"
                                + evento
                )

                .setPositiveButton("Inscrever",
                        (dialog, which) -> fazerInscricao(evento))

                .setNegativeButton("Cancelar",null)

                .show();

    }


    private void fazerInscricao(String evento){

        SharedPreferences pref =
                getSharedPreferences("dados",MODE_PRIVATE);

        String usuario =
                pref.getString(
                        "usuario",
                        "Participante"
                );

        boolean sucesso =
                db.inserirInscricao(
                        usuario,
                        evento
                );

        if(sucesso){

            Toast.makeText(
                    this,
                    "Inscrição realizada!",
                    Toast.LENGTH_SHORT
            ).show();

        }
        else{

            Toast.makeText(
                    this,
                    "Erro ao inscrever",
                    Toast.LENGTH_SHORT
            ).show();

        }

    }

}