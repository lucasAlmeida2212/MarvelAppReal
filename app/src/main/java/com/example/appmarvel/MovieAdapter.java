package com.example.appmarvel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<ComicVineMovieResponse.MovieResult> movies;

    public MovieAdapter(List<ComicVineMovieResponse.MovieResult> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        ComicVineMovieResponse.MovieResult movie = movies.get(position);
        
        // Tratar o ano da data de lançamento (YYYY-MM-DD)
        String year = "N/A";
        if (movie.getReleaseDate() != null && movie.getReleaseDate().length() >= 4) {
            year = movie.getReleaseDate().substring(0, 4);
        }
        
        holder.tvMovieYear.setText(year);
        holder.tvMovieTitle.setText(movie.getName());
        
        // Se a descrição/deck for nula, coloca um texto alternativo elegante
        if (movie.getDeck() != null && !movie.getDeck().isEmpty()) {
            holder.tvMovieRole.setText(movie.getDeck());
        } else {
            holder.tvMovieRole.setText("CLASSIFIED CINEMATIC RECORD");
        }
        
        holder.tvMoviePhase.setText("SEC-OP // " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieYear, tvMovieTitle, tvMovieRole, tvMoviePhase;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieYear = itemView.findViewById(R.id.tvMovieYear);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvMovieRole = itemView.findViewById(R.id.tvMovieRole);
            tvMoviePhase = itemView.findViewById(R.id.tvMoviePhase);
        }
    }
}
