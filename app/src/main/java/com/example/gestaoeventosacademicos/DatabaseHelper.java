package com.example.gestaoeventosacademicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "eventos.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE eventos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT," +
                "descricao TEXT," +
                "data TEXT," +
                "local TEXT)");

        db.execSQL("CREATE TABLE inscricoes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomeAluno TEXT," +
                "evento TEXT)");

        db.execSQL("CREATE TABLE presencas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomeAluno TEXT," +
                "evento TEXT," +
                "presente INTEGER)");

        db.execSQL("CREATE TABLE feedbacks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "evento TEXT," +
                "comentario TEXT," +
                "nota INTEGER)");

        db.execSQL("CREATE TABLE certificados (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomeAluno TEXT," +
                "evento TEXT," +
                "dataEmissao TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS eventos");
        db.execSQL("DROP TABLE IF EXISTS inscricoes");
        db.execSQL("DROP TABLE IF EXISTS presencas");
        db.execSQL("DROP TABLE IF EXISTS feedbacks");
        db.execSQL("DROP TABLE IF EXISTS certificados");
        onCreate(db);
    }

    public boolean inserirEvento(String titulo, String descricao, String data, String local) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("titulo", titulo);
        values.put("descricao", descricao);
        values.put("data", data);
        values.put("local", local);

        long resultado = db.insert("eventos", null, values);
        return resultado != -1;
    }

    public Cursor listarEventos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM eventos ORDER BY id DESC", null);
    }

    public boolean inserirInscricao(String nomeAluno, String evento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nomeAluno", nomeAluno);
        values.put("evento", evento);

        long resultado = db.insert("inscricoes", null, values);
        return resultado != -1;
    }

    public Cursor listarInscricoes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM inscricoes ORDER BY id DESC", null);
    }

    public boolean marcarPresenca(String nomeAluno, String evento, int presente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nomeAluno", nomeAluno);
        values.put("evento", evento);
        values.put("presente", presente);

        long resultado = db.insert("presencas", null, values);
        return resultado != -1;
    }

    public boolean verificarPresenca(String nomeAluno, String evento) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM presencas WHERE nomeAluno = ? AND evento = ? AND presente = 1",
                new String[]{nomeAluno, evento}
        );

        boolean presente = cursor.getCount() > 0;
        cursor.close();

        return presente;
    }

    public boolean inserirFeedback(String evento, String comentario, int nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("evento", evento);
        values.put("comentario", comentario);
        values.put("nota", nota);

        long resultado = db.insert("feedbacks", null, values);
        return resultado != -1;
    }

    public boolean gerarCertificado(String nomeAluno, String evento, String dataEmissao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nomeAluno", nomeAluno);
        values.put("evento", evento);
        values.put("dataEmissao", dataEmissao);

        long resultado = db.insert("certificados", null, values);
        return resultado != -1;
    }

    public Cursor listarCertificados() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM certificados ORDER BY id DESC", null);
    }
    public void limparEventos() {

        SQLiteDatabase db =
                this.getWritableDatabase();

        db.delete(
                "eventos",
                null,
                null
        );

    }
}

