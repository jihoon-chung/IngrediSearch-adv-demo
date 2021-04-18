package com.demo.ingredisearch.repository;

import com.demo.ingredisearch.repository.sources.favorites.FakeFavoritesSource;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSource;

import org.junit.Test;

public class RecipeRepositoryTest {
    // SUT
    RecipeRepository mRecipeRepository;

    FakeRemoteDataSource mRemoteDataSource;
    FakeFavoritesSource mFavoritesSource;

    @Test
    public void searchRecipes_whenFailedByNetworkError_returnsErrorResponse() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)

    }

    @Test
    public void searchRecipes_whenFailedWithHTTPError_returnsErrorResponse() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)

    }

    @Test
    public void searchRecipes_whenFailedWithAuthError_returnsErrorResponse() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)

    }

    @Test
    public void searchRecipes_whenSucceedWithNullResult_returnsEmptyList() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)

    }

    @Test
    public void searchRecipes_whenSucceed_returnsRecipesList() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
    }

    @Test
    public void searchRecipe_whenFailedByNetworkError_returnsErrorResponse() throws InterruptedException {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)

    }

    @Test
    public void searchRecipe_whenFailedWithHTTPError_returnsErrorResponse() throws InterruptedException {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)

    }

    @Test
    public void searchRecipe_whenFailedWithAuthError_returnsErrorResponse() throws InterruptedException {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)

    }

    @Test
    public void searchRecipe_whenSucceedWithNullResult_returnsNull() throws InterruptedException {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)

    }

    @Test
    public void searchRecipe_whenSucceed_returnsRecipe() throws InterruptedException {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)

    }

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
        /// Arrange (Given)

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