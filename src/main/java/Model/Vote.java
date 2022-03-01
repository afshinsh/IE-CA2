package Model;

public class Vote {
    public Vote(String userEmail, int commentId, int vote){
        UserEmail = userEmail;
        CommentId = commentId;
        Vote = vote;
    }
    public String UserEmail;
    public int CommentId;
    public int Vote;
}