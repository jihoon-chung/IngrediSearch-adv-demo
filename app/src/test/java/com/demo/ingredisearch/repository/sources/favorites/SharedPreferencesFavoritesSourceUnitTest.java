package com.demo.ingredisearch.repository.sources.favorites;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(AndroidJUnit4.class)
public class SharedPreferencesFavoritesSourceUnitTest {

    // SUT
    SharedPreferencesFavoritesSource mFavoritesSource;

    @Before
    public void init() {
        mFavoritesSource = new SharedPreferencesFavoritesSource(
                ApplicationProvider.getApplicationContext()
        );

        mFavoritesSource.clearFavorites();
    }

    @After
    public void wrapUp() {
        mFavoritesSource.clearFavorites();
    }


    @Test
    public void getFavorites_noFavorites_returnEmptyList() {
        // Arrange (Given)

        // Act (When)
        List<Recipe> favorites = mFavoritesSource.getFavorites();

        // Assert (Then)
        assertThat(favorites, hasSize(0));
    }

    @Test
    public void getFavorites_someFavorites_returnAll() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);
        mFavoritesSource.addFavorite(TestData.recipe2);

        // Act (When)
        List<Recipe> favorites = mFavoritesSource.getFavorites();

        // Assert (Then)
        assertThat(favorites, hasSize(2));
    }

    @Test
    public void addFavorites_noDuplicateId_addToFavoritesWithFavoriteStatusAsTrue() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Act (When)
        mFavoritesSource.addFavorite(TestData.recipe2);

        // Assert (Then)
        List<Recipe> favorites = mFavoritesSource.getFavorites();
        assertThat(favorites, hasSize(2));
    }

    @Test
    public void addFavorites_recipeWithSameIdAlreadyExists_rejectAddition() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Act (When)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Assert (Then)
        List<Recipe> favorites = mFavoritesSource.getFavorites();
        assertThat(favorites, hasSize(1));
    }

    @Test
    public void removeFavorite_removesRecipeFromFavorites() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);
        mFavoritesSource.addFavorite(TestData.recipe2);

        // Act (When)
        mFavoritesSource.removeFavorite(TestData.recipe1);

        // Assert (Then)
        List<Recipe> favorites = mFavoritesSource.getFavorites();
        assertThat(favorites, hasSize(1));
    }

    @Test
    public void clearFavorites_removeAllFavorites() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);
        mFavoritesSource.addFavorite(TestData.recipe2);

        // Act (When)
        mFavoritesSource.clearFavorites();

        // Assert (Then)
        List<Recipe> favorites = mFavoritesSource.getFavorites();
        assertThat(favorites, hasSize(0));
    }

}
