package com.demo.ingredisearch.repository.sources.remote;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.sources.ResponseCallback;
import com.demo.ingredisearch.util.Resource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeRemoteDataSource {
    public enum DataStatus { Success, NetworkError, HTTPError, AuthError }

    private final Map<String, Recipe> mRecipes = new LinkedHashMap<>();

    private DataStatus mDataStatus;

    public void searchRecipes(String query, ResponseCallback<List<Recipe>> callback) {
        // TODO
        callback.onDataAvailable(Resource.success(new ArrayList<>(mRecipes.values())));
    }

    public void searchRecipe(String recipeId, ResponseCallback<Recipe> callback) {
        // TODO
        callback.onDataAvailable(Resource.success(mRecipes.get(recipeId)));
    }

    public void setDataStatus(DataStatus dataStatus) {
        this.mDataStatus = dataStatus;
    }

    public void addRecipes(List<Recipe> recipes) {
        // TODO
    }

}
