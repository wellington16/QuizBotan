package jogobotanica.com.br.quizbotan.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import jogobotanica.com.br.quizbotan.R;
import jogobotanica.com.br.quizbotan.infra.Enum;
import jogobotanica.com.br.quizbotan.persistencia.DbHelper;

public class FimJogoActivity extends AppCompatActivity {

    private Button btnTryAgain;
    private TextView txtResultScore, txtResultQuestion;
    private ProgressBar progressBarResult;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fimjogo);

        DbHelper db = new DbHelper(this);


        txtResultScore = (TextView) findViewById(R.id.txtTotalScore);
        txtResultQuestion = (TextView) findViewById(R.id.txtTotalQuestion);
        progressBarResult = (ProgressBar) findViewById(R.id.doneProgressBar);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            //Update 2.0
            int playCount = 0;
            if(totalQuestion == Enum.EASY_MODE_NUM) // EASY MODE
            {
                playCount = db.getPlayCount(0);
                playCount++;
                db.updatePlayCount(0,playCount); // Set PlayCount ++
            }
            else if(totalQuestion == Enum.MEDIUM_MODE_NUM) // MEDIUM MODE
            {
                final int level = 1;
                playCount = db.getPlayCount(level);
                playCount++;
                db.updatePlayCount(level,playCount); // Set PlayCount ++
            }
            else if(totalQuestion == Enum.HARD_MODE_NUM) // HARD MODE
            {
                final int level = 2;
                playCount = db.getPlayCount(level);
                playCount++;
                db.updatePlayCount(level,playCount); // Set PlayCount ++
            }
            else if(totalQuestion == Enum.HARDEST_MODE_NUM) // HARDEST MODE
            {
                final int level = 3;
                playCount = db.getPlayCount(level);
                playCount++;
                db.updatePlayCount(level,playCount); // Set PlayCount ++
            }

            final int porcento = 10;
            double porcentagem = ((score/totalQuestion)* porcento)  ;

            txtResultScore.setText(String.format("Pontos : %d(%.1f)%%", score, porcentagem));
            txtResultQuestion.setText(String.format("Acertos : %d/%d", correctAnswer, totalQuestion));

            progressBarResult.setMax(totalQuestion);
            progressBarResult.setProgress(correctAnswer);

            //save score
            db.inserirPontos(score);
        }
    }

    @Override
    public final void onBackPressed() {
        Intent irTelaIncial = new Intent( this, PrincipalActivity.class);
        startActivity(irTelaIncial);
        finish();

    }
}
