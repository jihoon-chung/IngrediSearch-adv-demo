package com.demo.ingredisearch.repository.sources.favorites;

import org.junit.Test;

public class SharedPreferencesFavoritesSourceTest {

    // SUT
    SharedPreferencesFavoritesSource mFavoritesSource;

    @Test
    public void getFavorites_noFavorites_returnEmptyList() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
    }

    @Test
    public void getFavorites_someFavorites_returnAll() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
    }

    @Test
    public void addFavorites_noDuplicateId_addToFavoritesWithFavoriteStatusAsTrue() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
    }

    @Test
    public void addFavorites_recipeWithSameIdAlreadyExists_rejectAddition() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
    }

    @Test
    public void removeFavorite_removesRecipeFromFavorites() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
    }

    @Test
    public void clearFavorites_removeAllFavorites() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
    }

}
