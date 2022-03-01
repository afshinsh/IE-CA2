package Views;

import Model.Comment;

import java.util.Date;

public class CommentView {

    public CommentView(Comment comment){
        Id = comment.Id;
        UserEmail = comment.UserEmail;
        Text = comment.Text;
        like = comment.like;
        dislike = comment.dislike;
    }
    public int Id;
    public String UserEmail;
    public String Text;
    public int like;
    public int dislike;
}
