package jogobotanica.com.br.quizbotan.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import jogobotanica.com.br.quizbotan.R;
import jogobotanica.com.br.quizbotan.gui.AjudaActivity;
import jogobotanica.com.br.quizbotan.gui.JogadasActivity;
import jogobotanica.com.br.quizbotan.gui.PontosActivity;
import jogobotanica.com.br.quizbotan.infra.Enum;
import jogobotanica.com.br.quizbotan.persistencia.DbHelper;


public class PrincipalActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView txtMode;
    Button btnPlay,btnScore,btnSair;
    ImageView flags;
    DbHelper db;
    FloatingActionButton fabSobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        txtMode = (TextView)findViewById(R.id.txtMode);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnScore = (Button)findViewById(R.id.btnScore);
        btnSair = (Button) findViewById(R.id.btnSair);
        fabSobre = (FloatingActionButton) findViewById(R.id.fabSobre);

        flags = (ImageView) findViewById(R.id.flags);

        db = new DbHelper(this);
        try{
            db.criarBanco();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        //Event
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0)
                    txtMode.setText(Enum.MODE.FÁCIL.toString());
                else if(progress == 1)
                    txtMode.setText(Enum.MODE.MÉDIO.toString());
                else if(progress == 2)
                    txtMode.setText(Enum.MODE.AVANÇADO.toString());
                else if(progress == 3)
                    txtMode.setText(Enum.MODE.MUITO_AVANÇADO.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instrucoes();
            }
        });

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PontosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fabSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getApplicationContext(),AjudaActivity.class));
                finish();
            }
        });

        flags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity( new Intent(getApplicationContext(),AjudaActivity.class));
//                finish();
            }
        });

    }

    private String getPlayMode() {
        if (seekBar.getProgress() == 0){
            return Enum.MODE.FÁCIL.toString();
        }else if(seekBar.getProgress()==1) {
            return Enum.MODE.MÉDIO.toString();
        }else if(seekBar.getProgress()==2) {
            return Enum.MODE.AVANÇADO.toString();
        }else {
            return Enum.MODE.MUITO_AVANÇADO.toString();
        }
    }

    public final void finalizar(View v){
        confirmarSaida();
    }

    @Override
    public void onBackPressed() {
        confirmarSaida();
    }


    private void confirmarSaida(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sair");
        builder.setMessage("Deseja Realmente Sair?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                DbHelper dbHelper = new DbHelper(getApplicationContext());
//                dbHelper.deletar();
                finish();
            }
        });
        builder.setNegativeButton("Não", null);
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void instrucoes(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Intruções");
        builder.setMessage("Nas próximas telas você terá que observar a foto e escolher a alternativa correta. Lembrando que você tem um tempo para tal.");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),JogadasActivity.class);
                intent.putExtra("MODE",getPlayMode()); // Send Mode to JogadasActivity page
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Não", null);
        AlertDialog alerta = builder.create();
        alerta.show();
    }
}