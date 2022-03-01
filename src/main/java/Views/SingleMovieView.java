package Views;

import java.util.ArrayList;
import java.util.List;

public class SingleMovieView {
    public int Id;
    public String Name;
    public String Summary;
    public String ReleaseDate;
    public String Director;
    public ArrayList<String> Writers;
    public ArrayList<String> Genres;
    public List<CastView> Cast;
    public double ImdbRate;
    public long Duration;
    public int AgeLimit;
    public List<CommentView> Comments;

}
