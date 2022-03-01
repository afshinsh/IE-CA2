package Model;

public class Rate {
    public Rate(String userEmail, int movieId, int score){
        UserEmail = userEmail;
        MovieId = movieId;
        Score = score;
    }

    public String UserEmail;
    public int MovieId;
    public int Score;
}
