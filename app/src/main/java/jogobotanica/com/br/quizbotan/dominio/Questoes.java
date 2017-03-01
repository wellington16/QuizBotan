package jogobotanica.com.br.quizbotan.dominio;

/**
 * Created by reale on 30/09/2016.
 */

public class Questoes {
    private int iD;
    private String imagem;
    private String respostaA;
    private String respostaB;
    private String respostaC;
    private String respostaD;
    private String respostaCorreta;

    public Questoes(int id, String image, String answerA, String answerB, String answerC, String answerD, String correctAnswer) {
        this.iD = id;
        imagem = image;
        respostaA = answerA;
        respostaB = answerB;
        respostaC = answerC;
        respostaD = answerD;
        respostaCorreta = correctAnswer;
    }

    public final int getiD() {
        return iD;
    }

    public final void setiD(int id) {
        this.iD = id;
    }

    public final String getImage() {
        return imagem;
    }

    public final void setImage(String image) {
        imagem = image;
    }

    public final String getRespostaA() {
        return respostaA;
    }

    public final void setRespostaA(String respostaA) {
        this.respostaA = respostaA;
    }

    public final String getRespostaB() {
        return respostaB;
    }

    public final void setRespostaB(String respostaB) {
        this.respostaB = respostaB;
    }

    public final String getRespostaC() {
        return respostaC;
    }

    public final void setRespostaC(String respostaC) {
        this.respostaC = respostaC;
    }

    public final String getRespostaD() {
        return respostaD;
    }

    public final void setRespostaD(String respostaD) {
        this.respostaD = respostaD;
    }

    public final String getRespostaCorreta() {
        return respostaCorreta;
    }

    public final void setRespostaCorreta(String respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }
}
