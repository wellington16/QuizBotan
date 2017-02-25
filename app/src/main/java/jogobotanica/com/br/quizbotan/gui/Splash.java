package jogobotanica.com.br.quizbotan.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import jogobotanica.com.br.quizbotan.R;


public class Splash extends AppCompatActivity {
    private static final int TEMPO_SPLASH = 1000;
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

                    while(isMbActive() && (tempPassado[0] < TEMPO_SPLASH )){
                        // enquanto o tempoPassado for menor que 1s
                        // Soma 100 milisegundos
                        final int time = 100;
                        sleep(time);
                        tempPassado[0] = getTempPassado(tempPassado[0]);
                    }
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

    private void chamarLogin() {
        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private int getTempPassado(int tempoPassadoMilesimo) {
        if(isMbActive()) {
            final int time = 100;
            tempoPassadoMilesimo += time;
            updateProgress(tempoPassadoMilesimo);
        }
        return tempoPassadoMilesimo;
    }

    private void updateProgress(int tempPassados) {
        if (getProgressBar() != null){
            final int progress = getProgressBar().getMax() * tempPassados / TEMPO_SPLASH;
            getProgressBar().setProgress(progress);
        }
    }

}
