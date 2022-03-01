package Views;

import Model.Movie;

import java.util.ArrayList;

public class MovieListView {
    public MovieListView(Movie movie) {
        Id = movie.id;
        Name = movie.name;
        Director = movie.director;
        Genres = movie.genres;
        rating = movie.imdbRate;
    }

    public int Id;
    public String Name;
    public String Director;
    public ArrayList<String> Genres;
    public double rating;
}
