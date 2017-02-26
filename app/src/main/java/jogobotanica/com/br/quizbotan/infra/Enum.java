package jogobotanica.com.br.quizbotan.infra;

public class Enum {
    // NUMÉRO DE QUESTÕES
    public static final int EASY_MODE_NUM = 5;
    public static final int MEDIUM_MODE_NUM = 10;
    public static final int HARD_MODE_NUM = 15;
    public  static final int HARDEST_MODE_NUM = 25;

    //VARIAVEIS PERMANENTE QUESTOES
    public  static final String QUESTOES = "Questoes";
    public  static final String ID = "id";
    public  static final String IMAGEM = "imagem";
    public  static final String RESPOSTA_A = "respostaA";
    public  static final String RESPOSTA_B = "respostaB";
    public  static final String RESPOSTA_C = "respostaC";
    public  static final String RESPOSTA_D = "respostaD";
    public  static final String RESPOSTA_CORRETA ="respostaCorreta";


    //VARIAVEIS PERMANENTE RANKING
    public  static final String RANKING = "Ranking";
    public  static final String PONTUACAO = "pontuacao";


    public enum MODE{
        FÁCIL,
        MÉDIO,
        AVANÇADO,
        MUITO_AVANÇADO
    }

}
