package Views;

import Model.Movie;

import java.util.ArrayList;

public class MovieView {
    public MovieView(Movie movie){
        Id = movie.id;
        Name = movie.name;
        Genres = movie.genres;
        Director = movie.director;
        ImdbRate = movie.imdbRate;
    }
    public int Id;
    public String Name;
    public ArrayList<String> Genres;
    public String Director;
    public double ImdbRate;

}
