
import Controllers.MovieController;
import Model.Rate;
import Storage.Storage;
import io.javalin.http.Context;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCA2 {

    @Test
    public void testRateMovieSuccess() {
        try {
            var before = Storage.Database.GetNumOfRates(1);
            Rate rating = new Rate("sara@ut.ac.ir", 1, 8);
            Storage.Database.AddRateMovie(rating);
            var after = Storage.Database.GetNumOfRates(1);
            assertEquals(after - before, 1);
        }
        catch (Exception e){
        }
    }
    @Test
    public void testRateMovieFailure() {
        try {
            Rate rating = new Rate("sara@ut.ac.ir", 1, 21);
            Storage.Database.AddRateMovie(rating);
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "InvalidRateScore");
        }
    }

    @Test
    public void testGetMoviesByYear() {
        try {
            var movies = Storage.Database.GetMovieByYear(2000, 2020);
            boolean success = true;
            for (var mve : movies){
                var year = mve.releaseDate.substring(0, 4);
                if (Integer.valueOf(year) > 2020 || Integer.valueOf(year) < 2000)
                    success = false;
            }
            assertEquals(success, true);
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "InvalidRateScore");
        }
    }

}
