package jogobotanica.com.br.quizbotan.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jogobotanica.com.br.quizbotan.R;

public class AjudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);
    }

    @Override
    public void onBackPressed() {
        Intent irTelaIncial = new Intent( this, PrincipalActivity.class);
        startActivity(irTelaIncial);
        finish();

    }
}
