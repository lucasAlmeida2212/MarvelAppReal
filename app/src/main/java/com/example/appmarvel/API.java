package com.example.appmarvel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    
    // Superhero API - Busca por nome
    @GET("search/{name}")
    Call<SuperheroSearchResponse> searchCharacter(@Path("name") String name);

    // ComicVine API - Aparições em Filmes (plural)
    @GET("movies")
    Call<ComicVineMovieResponse> getMovies(
        @Query("api_key") String apiKey,
        @Query("format") String format,
        @Query("filter") String filter,
        @Query("field_list") String fieldList
    );
}
