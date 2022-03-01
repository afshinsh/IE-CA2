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

    public static void GetMovieById(Context context) throws IOException {
        var movieId = context.pathParam("movie_id");
        var result =  Storage.Database.GetMovie(Integer.parseInt(movieId));

        File htmlResponse = new File("src\\main\\resources\\movie.html");
        Document doc = Jsoup.parse(htmlResponse, null);

        doc.getElementById("name").append(result.Name);
        doc.getElementById("summary").append(result.Summary);
        doc.getElementById("releaseDate").append(result.ReleaseDate);
        doc.getElementById("director").append(result.Director);
        doc.getElementById("writers").append(String.valueOf(result.Writers));
        doc.getElementById("genres").append(String.valueOf(result.Genres));
        String CastName = "";
        for(int i = 0; i < result.Cast.size(); i++){
            CastName +=  result.Cast.get(i).Name;
            if(i != result.Cast.size() - 1)
                CastName += ",";

        }
        doc.getElementById("cast").append(CastName);
        doc.getElementById("imdbRate").append(String.valueOf(result.ImdbRate));
        doc.getElementById("rating").append(String.valueOf(result.ImdbRate));
        doc.getElementById("duration").append(String.valueOf(result.Duration));
        doc.getElementById("ageLimit").append(String.valueOf(result.AgeLimit));



        Element table = doc.getElementById("cmTable");
        for (var item : result.Comments){
            String row = "<tr>\n";

            row += "<td>" + item.nickName + "</td>\n";
            row += "<td>" + item.Text + "</td>\n";
            var like = "<form action=\"\" method=\"POST\">\n" +
                    "            <label for=\"\">" + item.like +  "</label>\n" +
                    "            <input\n" +
                    "              id=\"form_comment_id\"\n" +
                    "              type=\"hidden\"\n" +
                    "              name=\"comment_id\"\n" +
                    "              value= " + item.like + "\n" +
                    "            />\n" +
                    "            <button type=\"submit\">like</button>\n" +
                    "          </form>";

            row += "<td>" + like  + "</td>\n";
            var dislike = "<form action=\"\" method=\"POST\">\n" +
                    "            <label for=\"\">" + item.dislike +  "</label>\n" +
                    "            <input\n" +
                    "              id=\"form_comment_id\"\n" +
                    "              type=\"hidden\"\n" +
                    "              name=\"comment_id\"\n" +
                    "              value=" + item.dislike + "\n" +
                    "            />\n" +
                    "            <button type=\"submit\">dislike</button>\n" +
                    "          </form>";
            row += "<td>" + dislike  + "</td>\n";
            row += "</tr>";
            table.append(row);
        }

        context.html(doc.toString());
    }
}
