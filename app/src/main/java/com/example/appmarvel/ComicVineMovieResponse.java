package com.example.appmarvel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ComicVineMovieResponse {
    @SerializedName("status_code")
    private int statusCode;
    
    @SerializedName("results")
    private List<MovieResult> results;

    public int getStatusCode() { return statusCode; }
    public List<MovieResult> getResults() { return results; }

    public static class MovieResult {
        private String name;
        
        @SerializedName("release_date")
        private String releaseDate;
        
        private String deck;

        public String getName() { return name; }
        public String getReleaseDate() { return releaseDate; }
        public String getDeck() { return deck; }
    }
}
