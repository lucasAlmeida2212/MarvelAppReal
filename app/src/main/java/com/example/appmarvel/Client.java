package com.example.appmarvel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    // API 1: Superhero API (Altere "YOUR_API_TOKEN" para o seu token real da superheroapi se necessário)
    private static final String SUPERHERO_BASE_URL = "https://superheroapi.com/api/97bb748f570c67e7a071ac72b4098b01/";
    private static Retrofit retrofitSuperhero;

    // API 2: ComicVine API
    private static final String COMICVINE_BASE_URL = "https://comicvine.gamespot.com/api/";
    private static Retrofit retrofitComicVine;
    
    public static final String COMICVINE_API_KEY = "5805160887e0c1532509b2915b52fe6433b10c50";

    public static API getSuperheroClient() {
        if (retrofitSuperhero == null) {
            retrofitSuperhero = new Retrofit.Builder()
                    .baseUrl(SUPERHERO_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitSuperhero.create(API.class);
    }

    public static API getComicVineClient() {
        if (retrofitComicVine == null) {
            retrofitComicVine = new Retrofit.Builder()
                    .baseUrl(COMICVINE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitComicVine.create(API.class);
    }
}
