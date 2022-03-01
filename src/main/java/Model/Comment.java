package Model;

import java.util.Date;

public class Comment {
    public Comment(String email, int movieId, String text){
        submitDate = new Date(System.currentTimeMillis());
        userEmail = email;
        this.movieId = movieId;
        this.text = text;
    }
    public Comment(){}
    public int id;
    public Date submitDate;
    public String userEmail;
    public int movieId;
    public String text;
    public int like = 0;
    public int dislike = 0;
}