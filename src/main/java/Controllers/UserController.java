package Controllers;

import Model.Movie;
import Storage.Storage;
import io.javalin.http.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

public class UserController {
    public static int CurrentUserId = 0;
    public static void GetWatchList(Context context) throws IOException {
        var userId = context.pathParam("user_id");

        var user = Storage.Database.getUserById(Integer.parseInt(userId));
        CurrentUserId = user.id;
        if(user == null) {
            context.result("Not Found!");
            return;
        }
        File htmlResponse = new File("src\\main\\resources\\watchlist.html");
        Document doc = Jsoup.parse(htmlResponse, null);
        Element name = doc.getElementById("name");
        name.append(user.name);
        Element nickname = doc.getElementById("nickname");
        nickname.append(user.nickname);
        Element table = doc.getElementById("mtable");
        if(user.watchList == null){
            context.html(doc.toString());
            return;
        }


        for (var item : user.watchList){
            String row = "<tr>\n";
            row += "<td>" + item.name + "</td>\n";
            row += "<td>" + item.releaseDate + "</td>\n";
            row += "<td>" + item.director + "</td>\n";
            row += "<td>" + item.genres + "</td>\n";
            row += "<td>" + item.imdbRate + "</td>\n";
            row += "<td>" + item.rating + "</td>\n";
            row += "<td>" + item.duration + "</td>\n";
            row += "<td><a href=\"/movies/" + item.id + "\"> link </a></td>\n";
            row += "<td>\n<form action=\"" + "/removeWatchList" + "\" method=\"POST\" >\n" +
                    "<input id=\"form_movie_id\" type=\"hidden\" name=\"form_movie_id\" value=\"" +
                    item.id + "\">\n" + "<button type=\"submit\">Remove</button>\n</form>\n</td>\n</tr>\n";
            table.append(row);

        }
        context.html(doc.toString());
    }

    public static void AddToWatchList(Context context) {
        String user_id = context.formParam("user_id_wl");
        var user = Storage.Database.getUserById(Integer.parseInt(user_id));
        if(user == null) {
            context.result("Not Found!");
            return;
        }
        int movie_Id;
        String move_id = context.pathParam("movie_id");
        if(move_id != null)
            movie_Id = Integer.parseInt(move_id);
        else
            movie_Id = MovieController.MovieId;
        Movie movie = Storage.Database.getMovieById(movie_Id);
        if(movie == null) {
            context.result("Not Found!");
            return;
        }
        user.addToWatchList(movie);
        context.render("/200.html");
    }

    public static void RemoveFromWatchList(Context context) throws Exception {
        var user = Storage.Database.getUserById(CurrentUserId);
        String movie_id = context.formParam("form_movie_id");
        var movie = Storage.Database.getMovieById(Integer.parseInt(movie_id));
        user.RemoveFromWatchList(movie);
        context.render("/200.html");
    }

    }
