package Model;

import java.util.Date;

public class Comment {
    public Comment(String email, int movieId, String text){
        SubmitDate = new Date(System.currentTimeMillis());
        UserEmail = email;
        MovieId = movieId;
        Text = text;
    }
    public int Id;
    public Date SubmitDate;
    public String UserEmail;
    public int MovieId;
    public String Text;
    public int like = 0;
    public int dislike = 0;
}