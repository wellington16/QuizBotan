package jogobotanica.com.br.quizbotan.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import jogobotanica.com.br.quizbotan.R;
import jogobotanica.com.br.quizbotan.dominio.Ranking;
import jogobotanica.com.br.quizbotan.infra.CustomImagemAdapter;
import jogobotanica.com.br.quizbotan.persistencia.DbHelper;


public class PontosActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontos);

        listView = (ListView)findViewById(R.id.listaRanking);
        DbHelper db = new DbHelper(this);
        List<Ranking> listRanking = db.getRanking();
        if(listRanking.size() > 0)
        {
            CustomImagemAdapter adapter = new CustomImagemAdapter(this,listRanking);
            listView.setAdapter(adapter);
        }
    }

    public void voltar(View view){
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent irTelaIncial = new Intent( getApplicationContext(), PrincipalActivity.class);
        startActivity(irTelaIncial);
        finish();

    }
}
