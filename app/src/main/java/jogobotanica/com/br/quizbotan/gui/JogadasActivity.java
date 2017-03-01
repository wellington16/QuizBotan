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

import java.util.ArrayList;
import java.util.List;

import jogobotanica.com.br.quizbotan.R;
import jogobotanica.com.br.quizbotan.dominio.Questoes;
import jogobotanica.com.br.quizbotan.persistencia.DbHelper;


public class JogadasActivity extends AppCompatActivity implements View.OnClickListener {

    private static final  long INTERVAL = 1000; // 1 second
    private static final  long TIMEOUT = 5000; // 10 sconds
    private int progressValue = 0;

    private CountDownTimer mCountDown; // for progressbar
    private List<Questoes> questoesPlay = new ArrayList<>(); //total Questoes
    private DbHelper db;
    private int index=0,thisQuestion=0,totalQuestion = 0,correctAnswer = 0;
    private String mode="";
    private int score;

    //Control
    private ProgressBar progressBar;
    private ImageView imageView;
    private Button btnA,btnB,btnC,btnD;
    private TextView txtScore,txtQuestion;

    public final int getScore() {
        return score;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogadas);

        //Get Data from PrincipalActivity
        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            mode = extra.getString("MODE");
        }

        db = new DbHelper(this);

        txtScore = (TextView)findViewById(R.id.txtScore);
        txtQuestion = (TextView)findViewById(R.id.txtQuestion);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        imageView = (ImageView)findViewById(R.id.question_flag);
        btnA=(Button)findViewById(R.id.btnAnswerA);
        btnB=(Button)findViewById(R.id.btnAnswerB);
        btnC=(Button)findViewById(R.id.btnAnswerC);
        btnD=(Button)findViewById(R.id.btnAnswerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

    }

    @Override
    protected final void onResume() {
        super.onResume();

        questoesPlay = db.getQuestionMode(mode);
        totalQuestion = questoesPlay.size();

        mCountDown = new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                    progressBar.setProgress(progressValue);
                    progressValue = progressValue + 1;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }

    private final void showQuestion(int index) {
        if(index < totalQuestion){
            thisQuestion++;
            txtQuestion.setText(String.format("%d/%d",thisQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;

            int imageId=this.getResources().getIdentifier(questoesPlay.get(index).getImage().toLowerCase(),"drawable",getPackageName());
            imageView.setBackgroundResource(imageId);
            btnA.setText(questoesPlay.get(index).getRespostaA());
            btnB.setText(questoesPlay.get(index).getRespostaB());
            btnC.setText(questoesPlay.get(index).getRespostaC());
            btnD.setText(questoesPlay.get(index).getRespostaD());

            mCountDown.start();
        }
        else{
            Intent intent = new Intent(this,FimJogoActivity.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public final void onClick(View v) {

        mCountDown.cancel();
        if(index < totalQuestion){
            Button clickedButton = (Button)v;
            if(clickedButton.getText().equals(questoesPlay.get(index).getRespostaCorreta()))
            {
                final int pontos = 10;
                score += pontos; // increase score
                correctAnswer++ ; //increase correct answer
                showQuestion(++index);
            }
            else {
                showQuestion(++index); // If choose right , just go to next question
                txtScore.setText(String.format("%d", getScore()));
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
