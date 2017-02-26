package jogobotanica.com.br.quizbotan.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jogobotanica.com.br.quizbotan.dominio.Questoes;
import jogobotanica.com.br.quizbotan.dominio.Ranking;
import jogobotanica.com.br.quizbotan.infra.Enum;

public class DbHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "banco.db";
    private static String DB_PATH = "";
    private SQLiteDatabase mDataBase;
    private Context mContext = null;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);

        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        File file = new File(DB_PATH+DB_NAME);
        if(file.exists())
            openDataBase(); // Add this line to fix db.insert can't insert values
        this.mContext = context;
    }

    public void openDataBase() {
        String myPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void copiaBanco() throws IOException {
        try {
            InputStream abrirBanco = mContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream reescreverBanco = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = abrirBanco.read(buffer)) > 0)
                reescreverBanco.write(buffer, 0, length);

            reescreverBanco.flush();
            reescreverBanco.close();
            abrirBanco.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            String meuCaminho = DB_PATH + DB_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(meuCaminho, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        return sqLiteDatabase != null ? true : false;
    }

    public void criarBanco() throws IOException {
        boolean bancoExiste = checkDataBase();
        if (bancoExiste) {

        } else {
            this.getReadableDatabase();
            try {
                copiaBanco();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //CRUD For Table
    public List<Questoes> getAllQuestion() {
        List<Questoes> listQuestoes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Questoes ORDER BY Random()", null);
            if (c == null) return null;
            c.moveToFirst();
            do {
                int Id = c.getInt(c.getColumnIndex(Enum.ID));
                String Image = c.getString(c.getColumnIndex(Enum.IMAGEM));
                String AnswerA = c.getString(c.getColumnIndex(Enum.RESPOSTA_A));
                String AnswerB = c.getString(c.getColumnIndex(Enum.RESPOSTA_B));
                String AnswerC = c.getString(c.getColumnIndex(Enum.RESPOSTA_C));
                String AnswerD = c.getString(c.getColumnIndex(Enum.RESPOSTA_D));
                String CorrectAnswer = c.getString(c.getColumnIndex(Enum.RESPOSTA_CORRETA));

                Questoes questoes = new Questoes(Id, Image, AnswerA, AnswerB, AnswerC, AnswerD, CorrectAnswer);
                listQuestoes.add(questoes);
            }
            while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return listQuestoes;
    }

    //We need improve this function to optimize process from Jogadas
    public List<Questoes> getQuestionMode(String mode) {
        List<Questoes> listQuestoes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        int limiteQuestoes = getLimit(mode);

        try {
            c = db.rawQuery(String.format("SELECT * FROM "+Enum.QUESTOES+" ORDER BY Random() LIMIT %d", limiteQuestoes), null);
            if (c == null) return null;
            c.moveToFirst();
            do {
                int id = c.getInt(c.getColumnIndex(Enum.ID));
                String imagem = c.getString(c.getColumnIndex(Enum.IMAGEM));
                String respostaA = c.getString(c.getColumnIndex(Enum.RESPOSTA_A));
                String respostaB = c.getString(c.getColumnIndex(Enum.RESPOSTA_B));
                String respostaC = c.getString(c.getColumnIndex(Enum.RESPOSTA_C));
                String respostaD = c.getString(c.getColumnIndex(Enum.RESPOSTA_D));
                String respostaCorreta = c.getString(c.getColumnIndex(Enum.RESPOSTA_CORRETA));

                Questoes questoes = new Questoes(id, imagem, respostaA, respostaB, respostaC, respostaD, respostaCorreta);
                listQuestoes.add(questoes);
            }
            while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return listQuestoes;
    }

    private int getLimit(String mode) {
        int limit = 0;
        if (mode.equals(Enum.MODE.FÁCIL.toString()))
            limit = Enum.EASY_MODE_NUM;
        else if (mode.equals(Enum.MODE.MÉDIO.toString()))
            limit = Enum.MEDIUM_MODE_NUM;
        else if (mode.equals(Enum.MODE.AVANÇADO.toString()))
            limit = Enum.HARD_MODE_NUM;
        else if (mode.equals(Enum.MODE.MUITO_AVANÇADO.toString()))
            limit = Enum.HARDEST_MODE_NUM;
        return limit;
    }

    //Inserir Pontos na lista de Ranking
    public void inserirPontos(double pontos) {
        String query = "INSERT INTO "+Enum.RANKING+"("+Enum.PONTUACAO+") VALUES("+pontos+")";
        mDataBase.execSQL(query);
    }

    //Listar de Pontos e Ranking
    public List<Ranking> getRanking() {
        List<Ranking> listRanking = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM "+Enum.RANKING+" Order By "+ Enum.PONTUACAO+" DESC;", null);
            if (c == null) return null;
            c.moveToNext();
            do {
                int Id = c.getInt(c.getColumnIndex(Enum.ID));
                double Score = c.getDouble(c.getColumnIndex(Enum.PONTUACAO));

                Ranking ranking = new Ranking(Id, Score);
                listRanking.add(ranking);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return listRanking;

    }


    //Update version 2.0
    public int getPlayCount(int level)
    {
        int resultado = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        try{
            c = db.rawQuery("SELECT contadorJogadas FROM contadorJogadasUsuario WHERE nivel="+level+";",null);
            if(c == null) return 0;
            c.moveToNext();
            do{
                resultado  = c.getInt(c.getColumnIndex("contadorJogadas"));
            }while(c.moveToNext());
            c.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return resultado;
    }

    public void updatePlayCount(int level,int playCount)
    {
        String query = String.format("UPDATE contadorJogadasUsuario Set contadorJogadas = %d WHERE nivel = %d",playCount,level);
        mDataBase.execSQL(query);
    }
}
