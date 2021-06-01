package com.demo.ingredisearch;

import android.content.Context;

import com.demo.ingredisearch.repository.RecipeRepository;
import com.demo.ingredisearch.repository.sources.favorites.SharedPreferencesFavoritesSource;
import com.demo.ingredisearch.repository.sources.remote.RecipeApiClient;

public class Injection {
    private final Context context;
    private RecipeRepository recipeRepository;

    public Injection(RecipeApplication context) {
        this.context = context;
    }

    public RecipeRepository getRepository() {
        if (recipeRepository == null) {
            recipeRepository = RecipeRepository.getInstance(
                    new RecipeApiClient(), new SharedPreferencesFavoritesSource(context)
            );
        }
        return recipeRepository;
    }

    public void setRepository(RecipeRepository repository) {
        this.recipeRepository = repository;
    }
}
