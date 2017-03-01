package jogobotanica.com.br.quizbotan.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jogobotanica.com.br.quizbotan.R;

public class AjudaActivity extends AppCompatActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);
    }


    public final void voltar(View view){
        this.onBackPressed();
    }

    @Override
    public final void onBackPressed() {
        Intent irTelaIncial = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(irTelaIncial);
        finish();

    }
}
