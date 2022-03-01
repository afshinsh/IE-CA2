package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.Movie;
import Storage.Storage;
import Views.MovieView;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CMD(resource = "getMoviesList")
public class GetMoviesList {
    @EXC
    public void getMovieList(String JsonInput) {

        var MoviesList = new ArrayList<MovieView>();
        JSONArray arr = new JSONArray();
        JSONObject obj=new JSONObject();

        try{
            List<Movie> movies = Storage.Database.GetAllMovies();
            for(Movie mve : movies){
                MovieView view = new MovieView(mve);
                MoviesList.add(view);

            }
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("{\"data\":{\"MoviesList\": " + mapper.writeValueAsString(MoviesList) + "}}");

        }
        catch (Exception e){

            Response.CreateResponse(false, "InvalidCommand");

        }
    }


    public static void GetHtmlResponse(Context context) throws IOException {
        List<Movie> movies = Storage.Database.GetAllMovies();
        File htmlResponse = new File("C:\\Users\\kaafj\\IdeaProjects\\IE-CA2\\src\\main\\resources\\movies.html");
        Document doc = Jsoup.parse(htmlResponse, "UTF-8");
        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        Elements dom = doc.children();

        var t = dom.select("#movies tbody");
        t.append("<tr><td>fuck</td></tr>");

        BufferedWriter bw = new BufferedWriter(new FileWriter(htmlResponse));
        bw.write(dom.toString());
        bw.close();
        context.render("/movies.html");
/*

        StringBuilder response = new StringBuilder();
        response.append("<html lang=\"en\"> \n" +
                " <head> \n" +
                "  <meta charset=\"UTF-8\"> \n" +
                "  <title>Movies</title> \n" +
                "  <style>\n" +
                "        table{\n" +
                "            width: 100%;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style> \n" +
                " </head> \n" +
                " <body> \n");
        response.append("  <table>\n" +
                "   <tbody> \n" +
                "    <tr> \n" +
                "     <th>name</th> \n" +
                "     <th>summary</th> \n" +
                "     <th>releaseDate</th> \n" +
                "     <th>director</th> \n" +
                "     <th>writers</th> \n" +
                "     <th>genres</th> \n" +
                "     <th>cast</th> \n" +
                "     <th>imdb Rate</th> \n" +
                "     <th>rating</th> \n" +
                "     <th>duration</th> \n" +
                "     <th>ageLimit</th> \n" +
                "     <th>Links</th> \n" +
                "    </tr> \n");
        for (var item : movies){
            response.append("<tr>");
            response.append(getTd(item.name));
            response.append(getTd(item.summary));
            response.append(getTd(item.releaseDate));
            response.append(getTd(item.director));
            response.append(getTd(item.writers));
            response.append(getTd(item.genres));
            response.append(getTd(item.cast));
            response.append(getTd(item.imdbRate));
            response.append(getTd(item.imdbRate));
            response.append(getTd(item.duration));
            response.append(getTd(item.ageLimit));
            var link = "<td><a href=\"/movies/" + item.id + "\"></td></tr>";
            response.append(link);
        }
        response.append("\n" +
                "   </tbody> \n" +
                "  </table>  \n" +
                " </body>\n" +
                "</html>");
        context.html(String.valueOf(response));
*/

    }
    private static String getTd(Object obj){
        return "<td>" + obj.toString() + "</td>";
    }
}
