package Main;

import Model.Movie;
import Model.User;
import Storage.Storage;
import Views.MovieListView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static String MapWatchList(User user) throws JsonProcessingException {
        List<MovieListView> list = new ArrayList<>();
        for(Movie movie : user.watchList)
            list.add(new MovieListView(movie));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(list);
    }

    public static String MapGenreMovies(String genre) throws JsonProcessingException {
        List<MovieListView> list = new ArrayList<>();
        for(Movie movie : Storage.Database.Movies)
            if(movie.genres.contains(genre))
                list.add(new MovieListView(movie));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(list);
    }
}
