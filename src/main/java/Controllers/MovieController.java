package Controllers;

import Model.Movie;
import Storage.Storage;
import io.javalin.http.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MovieController {

    public static void GetAllMovie(Context context) throws IOException {
        List<Movie> movies = Storage.Database.GetAllMovies();
        File htmlResponse = new File("src\\main\\resources\\movies.html");
        Document doc = Jsoup.parse(htmlResponse, null);
        Element table = doc.getElementById("table");
        for (var item : movies){
            String row = "<tr>\n";

            row += "<td>" + item.name + "</td>\n";
            row += "<td>" + item.summary + "</td>\n";
            row += "<td>" + item.releaseDate + "</td>\n";
            row += "<td>" + item.director + "</td>\n";
            row += "<td>" + item.writers + "</td>\n";
            row += "<td>" + item.genres + "</td>\n";
            row += "<td>" + item.cast + "</td>\n";
            row += "<td>" + item.imdbRate + "</td>\n";
            row += "<td>" + item.imdbRate + "</td>\n";
            row += "<td>" + item.duration + "</td>\n";
            row += "<td>" + item.ageLimit + "</td>\n";
            row += "<td><a href=\"/movies/" + item.id + "\"> link </a></td>\n</tr>\n";
            table.append(row);
        }
        context.html(doc.toString());
    }
}
