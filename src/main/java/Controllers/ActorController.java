package Controllers;

import Model.Actor;
import Model.Movie;
import Storage.Storage;
import io.javalin.http.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActorController {




    public static void GetActorById(Context context) throws IOException {

        int actorId = Integer.parseInt(context.pathParam("actor_id"));
        Actor actor = Storage.Database.GetActorById(actorId);
        ArrayList<Movie> movieActedList = Storage.Database.GetTotalMovieActedIn(actorId);
        File htmlResponse = new File("src\\main\\resources\\actor.html");
        Document doc = Jsoup.parse(htmlResponse, null);
        doc.getElementById("name").append(actor.name);
        doc.getElementById("birthDate").append(actor.birthDate);
        doc.getElementById("nationality").append(actor.nationality);
        doc.getElementById("tma").append(String.valueOf(movieActedList.size()));
        Element table = doc.getElementById("movie_table");


        for(var item:movieActedList){
            String row = "<tr>\n";

            row += "<td>" + item.name + "</td>\n";
            row += "<td>" + item.imdbRate + "</td>\n";
            row += "<td>" + item.rating + "</td>\n";
            row += "<td><a href=\"/movies/" + item.id + "\"> link </a></td>\n</tr>\n";
            table.append(row);
        }

        context.html(doc.toString());
    }
}
