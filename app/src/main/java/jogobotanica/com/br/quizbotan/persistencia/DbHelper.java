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

import jogobotanica.com.br.quizbotan.dominio.Question;
import jogobotanica.com.br.quizbotan.dominio.Ranking;
import jogobotanica.com.br.quizbotan.infra.Enum;


/**
 * Created by reale on 30/09/2016.
 */

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

    public void copyDataBase() throws IOException {
        try {
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
                myOutput.write(buffer, 0, length);

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }

    public void createDataBase() throws IOException {
        boolean isDBExists = checkDataBase();
        if (isDBExists) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
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
    public List<Question> getAllQuestion() {
        List<Question> listQuestion = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Questoes ORDER BY Random()", null);
            if (c == null) return null;
            c.moveToFirst();
            do {
                int Id = c.getInt(c.getColumnIndex("id"));
                String Image = c.getString(c.getColumnIndex("imagem"));
                String AnswerA = c.getString(c.getColumnIndex("respostaA"));
                String AnswerB = c.getString(c.getColumnIndex("respostaB"));
                String AnswerC = c.getString(c.getColumnIndex("respostaC"));
                String AnswerD = c.getString(c.getColumnIndex("respostaD"));
                String CorrectAnswer = c.getString(c.getColumnIndex("respostaCorreta"));

                Question question = new Question(Id, Image, AnswerA, AnswerB, AnswerC, AnswerD, CorrectAnswer);
                listQuestion.add(question);
            }
            while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return listQuestion;
    }

    //We need improve this function to optimize process from Playing
    public List<Question> getQuestionMode(String mode) {
        List<Question> listQuestion = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        int limit = getLimit(mode);

        try {
            c = db.rawQuery(String.format("SELECT * FROM Questoes ORDER BY Random() LIMIT %d", limit), null);
            if (c == null) return null;
            c.moveToFirst();
            do {
                int Id = c.getInt(c.getColumnIndex("id"));
                String Image = c.getString(c.getColumnIndex("imagem"));
                String AnswerA = c.getString(c.getColumnIndex("respostaA"));
                String AnswerB = c.getString(c.getColumnIndex("respostaB"));
                String AnswerC = c.getString(c.getColumnIndex("respostaC"));
                String AnswerD = c.getString(c.getColumnIndex("respostaD"));
                String CorrectAnswer = c.getString(c.getColumnIndex("respostaCorreta"));

                Question question = new Question(Id, Image, AnswerA, AnswerB, AnswerC, AnswerD, CorrectAnswer);
                listQuestion.add(question);
            }
            while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return listQuestion;
    }

    private int getLimit(String mode) {
        int limit = 0;
        if (mode.equals(Enum.MODE.EASY.toString()))
            limit = Enum.EASY_MODE_NUM;
        else if (mode.equals(Enum.MODE.MEDIUM.toString()))
            limit = Enum.MEDIUM_MODE_NUM;
        else if (mode.equals(Enum.MODE.HARD.toString()))
            limit = Enum.HARD_MODE_NUM;
        else if (mode.equals(Enum.MODE.HARDEST.toString()))
            limit = Enum.HARDEST_MODE_NUM;
        return limit;
    }

    //Insert Score to Ranking table
    public void insertScore(double score) {
        String query = "INSERT INTO Ranking(pontuacao) VALUES("+score+")";
        mDataBase.execSQL(query);
    }

    //Get Score and sort ranking
    public List<Ranking> getRanking() {
        List<Ranking> listRanking = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Ranking Order By pontuacao DESC;", null);
            if (c == null) return null;
            c.moveToNext();
            do {
                int Id = c.getInt(c.getColumnIndex("id"));
                double Score = c.getDouble(c.getColumnIndex("pontuacao"));

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
        int result = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        try{
            c = db.rawQuery("SELECT contadorJogadas FROM contadorJogadasUsuario WHERE nivel="+level+";",null);
            if(c == null) return 0;
            c.moveToNext();
            do{
                result  = c.getInt(c.getColumnIndex("contadorJogadas"));
            }while(c.moveToNext());
            c.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    public void updatePlayCount(int level,int playCount)
    {
        String query = String.format("UPDATE contadorJogadasUsuario Set contadorJogadas = %d WHERE nivel = %d",playCount,level);
        mDataBase.execSQL(query);
    }
}
