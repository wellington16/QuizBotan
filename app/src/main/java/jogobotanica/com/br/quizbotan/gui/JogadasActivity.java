package jogobotanica.com.br.quizbotan.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jogobotanica.com.br.quizbotan.R;
import jogobotanica.com.br.quizbotan.dominio.Questoes;
import jogobotanica.com.br.quizbotan.persistencia.DbHelper;


public class JogadasActivity extends AppCompatActivity implements View.OnClickListener {

    private static final  long INTERVALO = 6500; // 10 second
    private static final  long TEMPOTROCA = 40000; // 40 seconds
    private int progressValor = 0;

    private CountDownTimer mCountDown; // for progressbar
    private List<Questoes> questoesPlay = new ArrayList<>(); //total Questoes
    private DbHelper db;
    private int indeces =0, Questoes =0, totalQuestoes = 0, respostasCorreta = 0;
    private String modo ="";
    private int pontos;

    //Control
    private ProgressBar progressBar;
    private ImageView imageView;
    private Button btnAlternativaA, btnAlternativaB, btnAlternativaC, btnAlternativaD;
    private TextView txtPontos, txtQuestoes;

    public final int getPontos() {
        return pontos;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogadas);

        //Get Data from PrincipalActivity
        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            modo = extra.getString("MODE");
        }

        db = new DbHelper(this);

        txtPontos = (TextView)findViewById(R.id.txtScore);
        txtQuestoes = (TextView)findViewById(R.id.txtQuestion);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        imageView = (ImageView)findViewById(R.id.question_flag);
        btnAlternativaA =(Button)findViewById(R.id.btnAnswerA);
        btnAlternativaB =(Button)findViewById(R.id.btnAnswerB);
        btnAlternativaC =(Button)findViewById(R.id.btnAnswerC);
        btnAlternativaD =(Button)findViewById(R.id.btnAnswerD);

        btnAlternativaA.setOnClickListener(this);
        btnAlternativaB.setOnClickListener(this);
        btnAlternativaC.setOnClickListener(this);
        btnAlternativaD.setOnClickListener(this);

    }

    @Override
    protected final void onResume() {
        super.onResume();

        questoesPlay = db.getQuestionMode(modo);
        totalQuestoes = questoesPlay.size();

        mCountDown = new CountDownTimer(TEMPOTROCA, INTERVALO) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValor);
                progressValor = progressValor + 1;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++indeces);
            }
        };
        showQuestion(indeces);
    }

    private final void showQuestion(int index) {
        if(index < totalQuestoes){
            Questoes++;
            txtQuestoes.setText(String.format("%d/%d", Questoes, totalQuestoes));
            progressBar.setProgress(0);
            progressValor = 0;

            int imageId=this.getResources().getIdentifier(questoesPlay.get(index).getImage().toLowerCase(),"drawable",getPackageName());
            imageView.setBackgroundResource(imageId);
            btnAlternativaA.setText(questoesPlay.get(index).getRespostaA());
            btnAlternativaB.setText(questoesPlay.get(index).getRespostaB());
            btnAlternativaC.setText(questoesPlay.get(index).getRespostaC());
            btnAlternativaD.setText(questoesPlay.get(index).getRespostaD());

            mCountDown.start();
        }
        else{
            Intent intent = new Intent(this,FimJogoActivity.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", pontos);
            dataSend.putInt("TOTAL", totalQuestoes);
            dataSend.putInt("CORRECT", respostasCorreta);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public final void onClick(View v) {

        mCountDown.cancel();
        if(indeces < totalQuestoes){
            Button clickedButton = (Button)v;
            if(clickedButton.getText().equals(questoesPlay.get(indeces).getRespostaCorreta()))
            {
                Toast.makeText(getApplicationContext(),"Muito bem, reposta correta\n"+ questoesPlay.get(indeces).getRespostaCorreta(),Toast.LENGTH_SHORT).show();
                final int pontos = 10;
                this.pontos += pontos; // increase pontos
                respostasCorreta++ ; //increase correct answer
                showQuestion(++indeces);
            }
            else {
                Toast.makeText(getApplicationContext(),"A resposta correta seria:\n" + questoesPlay.get(indeces).getRespostaCorreta(),Toast.LENGTH_SHORT).show();
                showQuestion(++indeces); // If choose right , just go to next question
                txtPontos.setText(String.format("%d", getPontos()));
            }

        }

    }

    @Override
    public final void onBackPressed() {
        Intent irTelaIncial = new Intent( this, PrincipalActivity.class);
        startActivity(irTelaIncial);
        finish();

    }
}