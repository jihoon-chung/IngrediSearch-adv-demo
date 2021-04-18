package com.demo.ingredisearch.repository.sources.favorites;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.ingredisearch.models.Recipe;

import java.util.List;

public class SharedPreferencesFavoritesSource {

    public static final String FAVORITES_KEY = "FAVORITE_KEY";

    private SharedPreferences mPreferences;

    public SharedPreferencesFavoritesSource(Context context) {
        mPreferences = context.getSharedPreferences(FAVORITES_KEY, Context.MODE_PRIVATE);
    }

    public List<Recipe> getFavorites() {
        // TODO
        return null;
    }

    public void addFavorite(Recipe recipe) {
        // TODO
    }

    public void removeFavorite(Recipe recipe) {
        // TODO
    }

    public void clearFavorites() {
        // TODO
    }

}
