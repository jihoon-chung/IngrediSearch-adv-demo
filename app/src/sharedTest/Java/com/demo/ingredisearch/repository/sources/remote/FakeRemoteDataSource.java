package com.demo.ingredisearch.repository.sources.remote;

import com.demo.ingredisearch.Contants;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RemoteDataSource;
import com.demo.ingredisearch.repository.sources.ResponseCallback;
import com.demo.ingredisearch.util.AppExecutors;
import com.demo.ingredisearch.util.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeRemoteDataSource implements RemoteDataSource {
    public enum DataStatus {Success, NetworkError, HTTPError, AuthError}

    private final Map<String, Recipe> mRecipes = new LinkedHashMap<>();

    private final AppExecutors mAppExecutors;
    private DataStatus mDataStatus;

    public FakeRemoteDataSource(AppExecutors mAppExecutors) {
        this.mAppExecutors = mAppExecutors;
        this.mDataStatus = DataStatus.Success;
    }

    @Override
    public void searchRecipes(String query, ResponseCallback<List<Recipe>> callback) {
        mAppExecutors.networkIO().execute(() -> {
            try {
                Thread.sleep(Contants.FAKE_NETWORK_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (mDataStatus) {
                case Success:
                    callback.onDataAvailable(Resource.success(new ArrayList<>(mRecipes.values())));
                    break;
                case NetworkError:
                    callback.onError(Resource.error("Network Error", null));
                    break;
                case HTTPError:
                    callback.onError(Resource.error("HTTP Error", null));
                    break;
                case AuthError:
                    callback.onError(Resource.error("401 Unauthorized. Token may be invalid", null));
                    break;
            }
        });

    }

    @Override
    public void searchRecipe(String recipeId, ResponseCallback<Recipe> callback) {
        mAppExecutors.networkIO().execute(() -> {
            try {
                Thread.sleep(Contants.FAKE_NETWORK_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (mDataStatus) {
                case Success:
                    callback.onDataAvailable(Resource.success(mRecipes.get(recipeId)));
                    break;
                case NetworkError:
                    callback.onError(Resource.error("Network Error", null));
                    break;
                case HTTPError:
                    callback.onError(Resource.error("HTTP Error", null));
                    break;
                case AuthError:
                    callback.onError(Resource.error("401 Unauthorized. Token may be invalid", null));
                    break;
            }
        });
    }

    public void setDataStatus(DataStatus dataStatus) {
        this.mDataStatus = dataStatus;
    }

    public void addRecipes(List<Recipe> recipes) {
        recipes.forEach(recipe -> mRecipes.put(recipe.getRecipeId(), new Recipe(recipe)));
    }

    public void addRecipes(Recipe... recipes) {
        addRecipes(Arrays.asList(recipes));
    }

}


























