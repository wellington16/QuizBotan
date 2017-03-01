package jogobotanica.com.br.quizbotan.dominio;

/**
 * Created by reale on 30/09/2016.
 */

public class Ranking {
    private int Id;
    private double Score;

    public Ranking(int id, double score) {
        Id = id;
        Score = score;
    }
    public Ranking(){
    }


    public final int getId() {
        return Id;
    }

    public final void setId(int id) {
        Id = id;
    }

    public final double getScore() {
        return Score;
    }

    public final void setScore(double score) {
        Score = score;
    }
}
