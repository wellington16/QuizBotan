package jogobotanica.com.br.quizbotan.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import jogobotanica.com.br.quizbotan.R;

import static java.lang.Thread.sleep;


public class SplashActivity extends AppCompatActivity {
    private static final int TEMPO_SPLASH = 3000;
    private final int[] tempPassado = {0};
    private boolean mbActive;
    private ProgressBar progressBar;

    public  final boolean isMbActive() {
        return mbActive;
    }

    public final  void setMbActive(boolean mbActives) {
        this.mbActive = mbActives;
    }

    public final ProgressBar getProgressBar() {
        return progressBar;
    }

    public final  void setProgressBar(ProgressBar progressBars) {
        this.progressBar = progressBars;
    }

    @Override
    protected  final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setProgressBar((ProgressBar)findViewById(R.id.barraProgresso));

        final Thread timer = new Thread(){
            @Override
            public void run(){
                setMbActive(true);
                try {
                    loopContagemTempo();
                } catch (InterruptedException e) {
                  //// ERRO se houver
                }
                finally {
                    //após terminar o try ele chama a tela login. Se não houver erro.
                    chamarLogin();
                }
            }
        };
        timer.start();
    }
    private void loopContagemTempo() throws InterruptedException {
        while(isMbActive() && (tempPassado[0] < TEMPO_SPLASH )){
            // enquanto o tempoPassado for menor que 1s
            // Soma 100 milisegundos
            final int time = 100;
            sleep(time);
            tempPassado[0] = getTempPassado(tempPassado[0]);
        }
    }
    private void chamarLogin() {
        Intent intent = new Intent(SplashActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    private int getTempPassado(int tempoMilesimo) {
        if(isMbActive()) {
            final int time = 100;
            tempoMilesimo += time;
            updateProgress(tempoMilesimo);
        }
        return tempoMilesimo;
    }

    private void updateProgress(int tempPassados) {
        if (getProgressBar() != null){
            final int progress = getProgressBar().getMax() * tempPassados / TEMPO_SPLASH;
            getProgressBar().setProgress(progress);
        }
    }

    @Override
    public final void onBackPressed() {
        finish();
    }

}
