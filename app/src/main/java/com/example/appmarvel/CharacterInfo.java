package com.example.appmarvel;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterInfo extends Fragment {

    private EditText etSearch;
    private TextView tvCharacterName, tvCharacterId, tvCharacterStatus, tvCharacterOrigin, tvCharacterRank;
    private TextView tvStrengthVal, tvAgilityVal, tvIntelligenceVal, tvLeadershipVal;
    private ProgressBar pbStrength, pbAgility, pbIntelligence, pbLeadership;
    private RecyclerView rvMovies;
    private LinearLayoutManager layoutManager;
    private ViewGroup layoutPowersContainer;

    public CharacterInfo() {
        // Required empty public constructor
    }

    public static CharacterInfo newInstance() {
        return new CharacterInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar Views do HUD
        etSearch = view.findViewById(R.id.etSearch);
        tvCharacterName = view.findViewById(R.id.tvCharacterName);
        tvCharacterId = view.findViewById(R.id.tvCharacterId);
        tvCharacterStatus = view.findViewById(R.id.tvCharacterStatus);
        tvCharacterOrigin = view.findViewById(R.id.tvCharacterOrigin);
        tvCharacterRank = view.findViewById(R.id.tvCharacterRank);

        tvStrengthVal = view.findViewById(R.id.tvStrengthVal);
        tvAgilityVal = view.findViewById(R.id.tvAgilityVal);
        tvIntelligenceVal = view.findViewById(R.id.tvIntelligenceVal);
        tvLeadershipVal = view.findViewById(R.id.tvLeadershipVal);

        pbStrength = view.findViewById(R.id.pbStrength);
        pbAgility = view.findViewById(R.id.pbAgility);
        pbIntelligence = view.findViewById(R.id.pbIntelligence);
        pbLeadership = view.findViewById(R.id.pbLeadership);

        rvMovies = view.findViewById(R.id.rvMovies);
        layoutPowersContainer = view.findViewById(R.id.layoutPowersContainer);

        layoutManager = new LinearLayoutManager(getContext());
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setHasFixedSize(true);

        // Ouvinte de pesquisa no teclado (Enter/Search)
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || 
                (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                String query = etSearch.getText().toString().trim();
                if (!query.isEmpty()) {
                    performDualSearch(query);
                }
                return true;
            }
            return false;
        });

        // Executar uma busca inicial padrão elegante
        performDualSearch("CAPTAIN AMERICA");
    }

    private void performDualSearch(String characterName) {
        // Modificar textos informando processamento de HUD
        tvCharacterName.setText("ANALYZING SOURCE...");
        tvCharacterId.setText("SYS_REF // SEARCHING...");

        // 1. Chamar a SuperheroAPI para os atributos e identidade
        Client.getSuperheroClient().searchCharacter(characterName).enqueue(new Callback<SuperheroSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SuperheroSearchResponse> call, @NonNull Response<SuperheroSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getResponse())) {
                    List<SuperheroSearchResponse.SuperheroCharacter> results = response.body().getResults();
                    if (results != null && !results.isEmpty()) {
                        updateHUDWithCharacter(results.get(0));
                    } else {
                        showErrorState();
                    }
                } else {
                    showErrorState();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuperheroSearchResponse> call, @NonNull Throwable t) {
                showErrorState();
            }
        });

        // 2. Chamar a ComicVine API (Utilizando endpoint plural com filtragem obrigatório do projeto)
        String filter = "name:" + characterName;
        Client.getComicVineClient().getMovies(Client.COMICVINE_API_KEY, "json", filter, "name,release_date,deck")
                .enqueue(new Callback<ComicVineMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<ComicVineMovieResponse> call, @NonNull Response<ComicVineMovieResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
                    MovieAdapter adapter = new MovieAdapter(response.body().getResults());
                    rvMovies.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ComicVineMovieResponse> call, @NonNull Throwable t) {
                // Falhas silenciosas para manter a integridade visual da tela secundária
            }
        });
    }

    private void updateHUDWithCharacter(SuperheroSearchResponse.SuperheroCharacter character) {
        tvCharacterName.setText(character.getName().toUpperCase());
        tvCharacterId.setText("SYS_REF // ID_" + character.getId());
        
        if (character.getBiography() != null) {
            tvCharacterOrigin.setText(character.getBiography().getPublisher().toUpperCase());
            tvCharacterRank.setText(character.getBiography().getAlignment().toUpperCase());
        }

        tvCharacterStatus.setText("ACTIVE");

        // Atualizar Sensores Visuais de Atributos
        SuperheroSearchResponse.Powerstats stats = character.getPowerstats();
        if (stats != null) {
            int str = parseStat(stats.getStrength(), 85);
            int agi = parseStat(stats.getSpeed(), 75);
            int intel = parseStat(stats.getIntelligence(), 90);
            int lead = parseStat(stats.getCombat(), 80);

            pbStrength.setProgress(str);
            tvStrengthVal.setText(str + "%");

            pbAgility.setProgress(agi);
            tvAgilityVal.setText(agi + "%");

            pbIntelligence.setProgress(intel);
            tvIntelligenceVal.setText(intel + "%");

            pbLeadership.setProgress(lead);
            tvLeadershipVal.setText(lead + "%");
        }
    }

    private int parseStat(String value, int defaultValue) {
        try {
            if (value != null && !value.equals("null")) {
                return Integer.parseInt(value);
            }
        } catch (NumberFormatException ignored) {}
        return defaultValue;
    }

    private void showErrorState() {
        tvCharacterName.setText("UNKNOWN SUBJECT");
        tvCharacterId.setText("SYS_REF // ERROR_404");
        tvCharacterStatus.setText("OFFLINE");
    }
}
