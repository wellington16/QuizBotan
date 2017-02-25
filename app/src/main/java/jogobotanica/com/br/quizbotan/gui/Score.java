package jogobotanica.com.br.quizbotan.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import jogobotanica.com.br.quizbotan.R;
import jogobotanica.com.br.quizbotan.dominio.Ranking;
import jogobotanica.com.br.quizbotan.infra.CustomImagemAdapter;
import jogobotanica.com.br.quizbotan.persistencia.DbHelper;


public class Score extends AppCompatActivity {
    ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        lstView = (ListView)findViewById(R.id.lstRanking);
        DbHelper db = new DbHelper(this);
        List<Ranking> lstRanking = db.getRanking();
        if(lstRanking.size() > 0)
        {
            CustomImagemAdapter adapter = new CustomImagemAdapter(this,lstRanking);
            lstView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        Intent irTelaIncial = new Intent( this, MainActivity.class);
        startActivity(irTelaIncial);
        finish();

    }
}
