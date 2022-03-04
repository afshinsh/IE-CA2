package Storage;

import Main.Mapper;
import Main.Response;
import Model.*;
import Views.CastView;
import Views.CommentView;
import Views.MovieListView;
import Views.SingleMovieView;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

public class Storage {

    static public class Database{
        public static List<Movie> Movies = new ArrayList<>();
        public static List<User> Users = new ArrayList<>();
        public static List<Actor> Actors = new ArrayList<>();
        public static List<Comment> Comments = new ArrayList<Comment>();
        public static List<Vote> Votes = new ArrayList<Vote>();
        public static int UserId = 1;
        public static int CommentId = 1;
        public static void AddActor(Actor actor){
            for (Actor act: Actors) {
                if(act.id == actor.id){
                    act = actor;
                    return;
                }
            }
            Actors.add(actor);
        }

        public static void AddMovie(Movie movie) throws Exception {
            var arr = movie.cast.toArray();
            for(int i = 0; i < movie.cast.size(); i++) {
                int actorId = Integer.valueOf(arr[i].toString());
                if(!ActorExists(actorId))
                    throw new Exception("ActorNotFound");
            }

            for (Movie mve: Movies) {
                if(mve.id == movie.id){
                    mve = movie;
                    return;
                }
            }
            Movies.add(movie);
        }

        private static boolean ActorExists(int actorId) {
            for (Actor actor : Actors)
                if(actor.id == actorId)
                    return true;
            return false;
        }

        public static void AddUser(User user){
            user.id = UserId++;
            Users.add(user);
        }

        public static Movie getMovieById(int id){
            for (Movie mve : Movies)
                if(mve.id == id)
                    return mve;
            return null;

        }

        public static User getUserByEmail(String email){
            for (User user : Users)
                if(user.email.equals(email))
                    return user;
            return null;
        }
        public static User getUserById(int id){
            for (User user : Users)
                if(user.id == id)
                    return user;
            return null;
        }

        public static void AddRateMovie(Rate rate) throws Exception{
            if(rate.Score > 10 || rate.Score < 1)
                throw new Exception("InvalidRateScore");
            if(getUserByEmail(rate.UserEmail) == null)
                throw new Exception("UserNotFound");
            Movie movie = getMovieById(rate.MovieId);
            if(movie == null)
                throw new Exception("MovieNotFound");
            movie.RateMovie(rate);
        }

        public static int GetNumOfRates(int id){
            for (Movie mve : Movies)
                if(mve.id == id)
                    return mve.rates.size();
            return -1;
        }

        public static void AddWatchList(WatchList watchList) throws Exception {
            User user = getUserByEmail(watchList.UserEmail);
            Movie movie = getMovieById(watchList.MovieId);
            NotFoundExceptions(user, movie);
            if(!user.canWatch(movie))
                throw new Exception("AgeLimitError");
            if(!user.addToWatchList(movie))
                throw new Exception("MovieAlreadyExists");

        }

        public static void RemoveFromWatchList(WatchList watchList) throws Exception {
            User user = getUserByEmail(watchList.UserEmail);
            Movie movie = getMovieById(watchList.MovieId);
            NotFoundExceptions(user, movie);
            user.RemoveFromWatchList(movie);
        }

        public static void NotFoundExceptions(User user, Movie movie) throws Exception {
            if(user == null)
                throw new Exception("UserNotFound");
            if(movie == null)
                throw new Exception("MovieNotFound");

        }

        public static void GetUserWatchList(String userEmail) throws Exception {
            User user = getUserByEmail(userEmail);
            if(user == null)
                throw new Exception("UserNotFound");
            System.out.println("{\"data\":{\"WatchList\": " + Mapper.MapWatchList(user) + "}}");
        }

        public static void GetMoviesByGenre(String genre) throws JsonProcessingException {
            System.out.println("{\"data\":{\"MoviesListByGenre\": " + Mapper.MapGenreMovies(genre) + "}}");
        }
        public static List<Movie> GetMoviesListByGenre(String genre){
            List<Movie> list = new ArrayList<>();
            for(Movie movie : Storage.Database.Movies)
                if(movie.genres.contains(genre))
                    list.add(movie);

            return list;
        }

        public static boolean MovieExists(int id){
            for (Movie movie: Movies) {
                if(movie.id == id)
                    return true;
            }
            return false;
        }
        public static boolean UserExists(String email){
            for (User user: Users) {
                if(Objects.equals(user.email, email))
                    return true;
            }
            return false;
        }
        public static boolean CommentExists(int id){
            for (Comment comment: Comments) {
                if(comment.id == id)
                    return true;
            }
            return false;
        }

        public static boolean AddComment(Comment comment){
            if(!UserExists(comment.userEmail)){
                Response.CreateResponse(false, "UserNotFound");
                return false;
            }
            if(!MovieExists(comment.movieId)){
                Response.CreateResponse(false, "MovieNotFound");
                return false;
            }
            comment.id = CommentId++;
            Comments.add(comment);
            Response.CreateResponse(true, "Comment Added Successfully");
            return true;
        }

        public static void AddVote(Vote vote){
            Votes.add(vote);
            for(Comment cm : Comments){
                if(cm.id == vote.CommentId){
                    if(vote.Vote == 1)
                        cm.like += 1;
                    if(vote.Vote == -1)
                        cm.dislike += 1;
                }
            }
            Response.CreateResponse(true, "Vote Added Successfully");
        }

        public static List<Movie> GetAllMovies(){
            return Movies;
        }
        public static SingleMovieView GetMovie(int id){
            Movie movie = new Movie();

            for (Movie mve: Movies) {
                if(mve.id == id)
                    movie = mve;
            }
            if(movie.id == -1){
                Response.CreateResponse(false, "MovieNotFound");
                return null;
            }
            SingleMovieView view = new SingleMovieView();
            view.Id = movie.id;
            view.Name = movie.name;
            view.Summary = movie.summary;
            view.ReleaseDate = movie.releaseDate;
            view.Director = movie.director;
            view.Writers = movie.writers;
            view.Genres = movie.genres;
            view.ImdbRate = movie.imdbRate;
            view.Rating = movie.rating;
            view.Duration = movie.duration;
            view.AgeLimit = movie.ageLimit;
            view.Comments = GetMovieComments(id);
            view.Cast = GetMovieCast(id);

            return view;
        }

        public static List<CastView> GetMovieCast(int id) {
            List<CastView> castViews = new ArrayList<CastView>();
            for(Movie mve : Movies){
                if(mve.id == id){
                    castViews = GetCastView(mve.cast);
                }
            }
            return castViews;
        }

        private static ArrayList<CastView> GetCastView(ArrayList<Integer> castIds) {
            ArrayList<CastView> result = new ArrayList<CastView>();
            var arr = castIds.toArray();
            for (int i = 0; i < castIds.size(); i++){
                try {
                    Actor actor = GetActorById(Integer.valueOf(arr[i].toString()));
                    CastView cv = new CastView(actor.id, actor.name);
                    result.add(cv);
                }catch (Exception ex){
                }

            }
            return result;
        }

        public static Actor GetActorById(Integer actorId) {
            for(Actor actor : Actors){
                if(actor.id == actorId)
                    return actor;
            }
            return null;
        }


        private static List<CommentView> GetMovieComments(int id) {
            List<CommentView> comments = new ArrayList<CommentView>();

            for(Comment cm : Comments){
                if(cm.movieId == id){
                    var user = getUserByEmail(cm.userEmail);
                    if(user != null)
                        comments.add(new CommentView(cm, user.nickname));
                }

            }
            return comments;
        }

        public static int GetCommentLike(int commentId){

            for (Comment comment: Comments) {
                if(comment.id == commentId)
                    return comment.like;
            }
            return -1;
        }
        public static ArrayList<Movie> GetTotalMovieActedIn(int actorId) {
            ArrayList<Movie> movieList = new ArrayList<>();
            for (Movie movie : Movies) {
                var arr = movie.cast.toArray();
                for (int i = 0; i < movie.cast.size(); i++) {
                    if (Integer.valueOf(arr[i].toString()) == actorId){
                        movieList.add(movie);
                        break;
                    }
                }
            }
            return movieList;

        }

        public static void AssignIdToUsers(){
            int i = 0;
            for(User user : Users){
                i++;
                user.id = i;
            }
        }

        public static void AssignIdToCommnet(){
            int i = 0;
            for(Comment cm : Comments){
                i++;
                cm.id = i;
            }
        }
        public static List<Movie> GetMovieByYear(int startDate, int endDate){
            List<Movie> movieList = new ArrayList<>();
            for(Movie movie : Movies){
                var year = movie.releaseDate.substring(0, 4);
                if(Integer.valueOf(year) <= endDate && Integer.valueOf(year) >= startDate)
                    movieList.add(movie);
            }
            return movieList;
        }
    }

}
