package Model;

import java.util.ArrayList;

public class Movie {
    public Movie(int id, String name, String summary, String releaseDate, String director, ArrayList<String> writers,
                 ArrayList<String> genres, ArrayList<Integer> cast, double imdbRate, long duration, int ageLimit){
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.director = director;
        this.writers = writers;
        this.genres = genres;
        this.cast = cast;
        this.imdbRate = imdbRate;
        this.duration = duration;
        this.ageLimit = ageLimit;
        rates = new ArrayList<>();
    }
    public Movie(){
        id = -1;
    }
    public int id;
    public String name;
    public String summary;
    public String releaseDate;
    public String director;
    public ArrayList<String> writers;
    public ArrayList<String> genres;
    public ArrayList<Integer> cast;
    public double imdbRate;
    public long duration;
    public int ageLimit;

    public ArrayList<Rate> rates;


    private double getImdbRate(){
        double sum = imdbRate;
        for (int i = 0; i < rates.size(); i++) {
            sum += rates.get(i).Score;
        }
        return sum / (rates.size() + 1) ;
    }
    private void updateImdbRate(int before , int after){
        double sum = imdbRate * (rates.size() + 1);
        sum = sum - before + after;
        imdbRate = sum / (rates.size() + 1);
    }

    public void RateMovie(Rate rate){

        for (Rate rt : rates)
            if(rt.UserEmail.equals(rate.UserEmail)){
                updateImdbRate(rt.Score, rate.Score);
                rt = rate;
                return;
            }
        rates.add(rate);
        imdbRate = getImdbRate();
    }

}