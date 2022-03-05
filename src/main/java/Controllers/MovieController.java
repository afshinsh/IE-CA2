package Controllers;

import Model.Movie;
import Model.Rate;
import Model.Vote;
import Storage.Storage;
import io.javalin.http.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MovieController {
    public static int MovieId = 0;

    private static  String ListOfMovieToHtml(List<Movie> movies) throws IOException {
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
            var cast = Storage.Database.GetMovieCast(item.id);
            String CastName = "";
            for(int i = 0; i < cast.size(); i++){
                CastName += " " + cast.get(i).Name;
                if(i != cast.size() - 1)
                    CastName += ",";

            }
            row += "<td>" + CastName + "</td>\n";
            row += "<td>" + item.imdbRate + "</td>\n";
            row += "<td>" + item.imdbRate + "</td>\n";
            row += "<td>" + item.duration + "</td>\n";
            row += "<td>" + item.ageLimit + "</td>\n";
            row += "<td><a href=\"/movies/" + item.id + "\"> link </a></td>\n</tr>\n";
            table.append(row);

        }
        return doc.toString();
    }

    public static void GetAllMovie(Context context) throws IOException {
        List<Movie> movies = Storage.Database.GetAllMovies();
        var response = ListOfMovieToHtml(movies);
        context.html(response);
    }

    public static void GetMovieById(Context context) throws Exception {
        var movieId = context.pathParam("movie_id");
        MovieId = Integer.parseInt(movieId);
        var result =  Storage.Database.GetMovie(Integer.parseInt(movieId));
        if(result == null){
            File htmlResponse = new File("src\\main\\resources\\404.html");
            Document doc = Jsoup.parse(htmlResponse, null);
            context.html(doc.toString());
            return;
        }
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
            CastName += " " + result.Cast.get(i).Name;
            if(i != result.Cast.size() - 1)
                CastName += ",";

        }
        doc.getElementById("cast").append(CastName);
        doc.getElementById("imdbRate").append(String.valueOf(result.ImdbRate));
        doc.getElementById("rating").append(String.valueOf(result.Rating));
        doc.getElementById("duration").append(String.valueOf(result.Duration));
        doc.getElementById("ageLimit").append(String.valueOf(result.AgeLimit));



        Element table = doc.getElementById("cmTable");
        for (var item : result.Comments){
            String row = "<tr>\n";

            row += "<td>" + item.nickName + "</td>\n";
            row += "<td>" + item.Text + "</td>\n";
            var like = "<form action=\"/like\" method=\"POST\">\n" +
                    "            <label for=\"\">" + item.like +  "</label>\n" +
                    "            <input " +
                    "              id=\"form_comment_id\" " +
                    "              type=\"hidden\" " +
                    "              name=\"form_comment_id\"  " +
                    "              value=\"" + item.Id + "\"" +
                    "            />\n" +
                    "<label>Your ID:</label>\n" +
                    "      <input type=\"text\" id = \"user_id\" name=\"user_id\" />" +
                    "            <button type=\"submit\">like</button>\n" +
                    "          </form>";

            row += "<td>" + like  + "</td>\n";
            var dislike = "<form action=\"/dislike\" method=\"POST\">\n" +
                    "            <label for=\"\">" + item.dislike +  "</label>\n" +
                    "            <input\n" +
                    "              id=\"form_comment_id\"\n" +
                    "              type=\"hidden\"\n" +
                    "              name=\"form_comment_id\"\n" +
                    "              value=\"" + item.Id + "\"\n" +
                    "            />\n" +
                    "<label>Your ID:</label>\n" +
                    "      <input type=\"text\" id = \"user_id\" name=\"user_id\"  />" +
                    "            <button type=\"submit\">dislike</button>\n" +
                    "          </form>";
            row += "<td>" + dislike  + "</td>\n";
            row += "</tr>";
            table.append(row);
        }

        context.html(doc.toString());
    }

    public static void RateMovie(Context context) throws IOException {
        var userId = context.pathParam("user_id");
        var movieId = context.pathParam("movie_id");
        var rate = context.pathParam("rate");

        var userEmail = Storage.Database.getUserById(Integer.parseInt(userId)).email;
        if(Integer.valueOf(rate) > 10 || Integer.valueOf(rate) < 0){
            File htmlResponse = new File("src\\main\\resources\\403.html");
            Document doc = Jsoup.parse(htmlResponse, null);
            context.html(doc.toString());
            return;
        }
        try {

            Rate rating = new Rate(
                    userEmail,
                    Integer.valueOf(movieId),
                    Integer.valueOf(rate));

            Storage.Database.AddRateMovie(rating);
            File htmlResponse = new File("src\\main\\resources\\200.html");
            Document doc = Jsoup.parse(htmlResponse, null);
            context.html(doc.toString());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    public static void RateMovieFromMoviePage(Context context) throws IOException {
        String quantity = context.formParam("quantity");
        String user_id = context.formParam("user_id");
        var userEmail = Storage.Database.getUserById(Integer.parseInt(user_id)).email;

        try {
            Rate rating = new Rate(
                    userEmail,
                    Integer.valueOf(MovieId),
                    Integer.valueOf(quantity));

            Storage.Database.AddRateMovie(rating);
            File htmlResponse = new File("src\\main\\resources\\200.html");
            Document doc = Jsoup.parse(htmlResponse, null);
            context.html(doc.toString());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    public static void SearchMovieByYear(Context context) throws IOException {
        var startDate = context.pathParam("start_year");
        var endDate = context.pathParam("end_year");
        List<Movie> movies = Storage.Database.GetMovieByYear(Integer.valueOf(startDate), Integer.valueOf(endDate));
        var response = ListOfMovieToHtml(movies);
        context.html(response);
    }


    public static void LikeAComment(Context context) throws IOException {
        String form_comment_id = context.formParam("form_comment_id");
        String user_id = context.formParam("user_id");
        var email = Storage.Database.getUserById(Integer.valueOf(user_id)).email;
        try{

            if(!Storage.Database.UserExists(email)){
                File htmlResponse = new File("src\\main\\resources\\404.html");
                Document doc = Jsoup.parse(htmlResponse, null);
                context.html(doc.toString());
            }

            if(!Storage.Database.CommentExists(Integer.valueOf(form_comment_id))){
                File htmlResponse = new File("src\\main\\resources\\403.html");
                Document doc = Jsoup.parse(htmlResponse, null);
                context.html(doc.toString());
            }

            Vote vote = new Vote(email, Integer.valueOf(form_comment_id), 1);
            Storage.Database.AddVote(vote);
            File htmlResponse = new File("src\\main\\resources\\200.html");
            Document doc = Jsoup.parse(htmlResponse, null);
            context.html(doc.toString());

        }
        catch (Exception e){
            File htmlResponse = new File("src\\main\\resources\\404.html");
            Document doc = Jsoup.parse(htmlResponse, null);
            context.html(doc.toString());
        }
    }

    public static void Dislike(Context context) throws IOException {
        String form_comment_id = context.formParam("form_comment_id");
        String user_id = context.formParam("user_id");
        var email = Storage.Database.getUserById(Integer.valueOf(user_id)).email;
        try{

            if(!Storage.Database.UserExists(email)){
                File htmlResponse = new File("src\\main\\resources\\404.html");
                Document doc = Jsoup.parse(htmlResponse, null);
                context.html(doc.toString());
            }

            if(!Storage.Database.CommentExists(Integer.valueOf(form_comment_id))){
                File htmlResponse = new File("src\\main\\resources\\403.html");
                Document doc = Jsoup.parse(htmlResponse, null);
                context.html(doc.toString());
            }


            Vote vote = new Vote(email, Integer.valueOf(form_comment_id), -1);
            Storage.Database.AddVote(vote);
            File htmlResponse = new File("src\\main\\resources\\200.html");
            Document doc = Jsoup.parse(htmlResponse, null);
            context.html(doc.toString());

        }
        catch (Exception e){
            File htmlResponse = new File("src\\main\\resources\\404.html");
            Document doc = Jsoup.parse(htmlResponse, null);
            context.html(doc.toString());
        }
    }

    public static void SearchByGenre(Context context) throws IOException {
        var genre = context.pathParam("genre");
        var moviesList = Storage.Database.GetMoviesListByGenre(genre);
        var response = ListOfMovieToHtml(moviesList);
        context.html(response);
    }
}
