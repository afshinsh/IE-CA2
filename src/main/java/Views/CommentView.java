package Views;

import Model.Comment;

public class CommentView {

    public CommentView(Comment comment, String nickname){
        Id = comment.id;
        UserEmail = comment.userEmail;
        Text = comment.text;
        like = comment.like;
        dislike = comment.dislike;
        nickName = nickname;
    }
    public int Id;
    public String UserEmail;
    public String Text;
    public int like;
    public int dislike;
    public String nickName;
}
