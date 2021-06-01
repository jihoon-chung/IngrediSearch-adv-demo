package com.demo.ingredisearch.features.searchresults;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.demo.ingredisearch.BaseUnitTest;
import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSource.DataStatus;
import com.demo.ingredisearch.util.Event;
import com.demo.ingredisearch.util.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class SearchResultsViewModelTest extends BaseUnitTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    // SUT
    SearchResultsViewModel mViewModel;

    @Before
    public void init() {
        super.init();
        mViewModel = new SearchResultsViewModel(mRecipeRepository);
    }

    @Test
    public void searchRecipes_allNonFavorites_displayRecipesAsTheyAre() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        // Act (When)
        mViewModel.searchRecipes("eggs");

        // Assert (Then)
        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(recipes, is(TestData.mRecipes));
    }

    @Test
    public void searchRecipes_emptyRecipes_displayNoRecipes() throws Exception {
        // Arrange (Given)

        // Act (When)
        mViewModel.searchRecipes("eggs");

        // Assert (Then)
        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(recipes, is(emptyList()));
    }

    @Test
    public void searchRecipes_networkError_displayRetry() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(DataStatus.NetworkError);

        // Act (When)
        mViewModel.searchRecipes("eggs");

        // Assert (Then)
        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(recipes, is(nullValue()));
        Event<Object> isError = LiveDataTestUtil.getOrAwaitValue(mViewModel.isError());
        assertThat(isError.getContentIfNotHandled(), is(not(nullValue())));
    }

    @Test
    public void searchRecipes_withSomeFavorites_displayRecipesAsDecorated() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);
        mFavoritesSource.addFavorites(TestData.recipe1, TestData.recipe2);

        // Act (When)
        mViewModel.searchRecipes("eggs");

        // Assert (Then)
        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
//        assertThat(recipes, hasItems(TestData.recipe1_favored, TestData.recipe2_favored,
//                TestData.recipe3, TestData.recipe4));
        assertThat(getFavStatus(recipes), is(Arrays.asList(true, true, false, false)));
    }

    private List<Boolean> getFavStatus(List<Recipe> recipes) {
        return recipes.stream().map(Recipe::isFavorite).collect(Collectors.toList());
    }

    @Test
    public void markFavorite_reloadUpdatedRecipes() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);
        mViewModel.searchRecipes("eggs");

        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(getFavStatus(recipes), is(Arrays.asList(false, false, false, false)));

        // Act (When)
        mViewModel.markFavorite(TestData.recipe1);

        // Assert (Then)
        recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(getFavStatus(recipes), is(Arrays.asList(true, false, false, false)));
    }

    @Test
    public void unMarkFavorite_reloadUpdatedRecipes() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);
        mFavoritesSource.addFavorites(TestData.recipe1);
        mViewModel.searchRecipes("eggs");

        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(getFavStatus(recipes), is(Arrays.asList(true, false, false, false)));

        // Act (When)
        mViewModel.unmarkFavorite(TestData.recipe1_favored);

        // Assert (Then)
        recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(getFavStatus(recipes), is(Arrays.asList(false, false, false, false)));
    }

    @Test
    public void requestToRecipeDetails_shouldTriggerNavToRecipeDetails() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.recipe1, TestData.recipe2);
        mViewModel.searchRecipes("eggs");

        // Act (When)
        mViewModel.requestNavToDetails(TestData.recipe1);

        // Assert (Then)
        Event<String> response = LiveDataTestUtil.getOrAwaitValue(mViewModel.navToDetails());
        assertThat(response.getContentIfNotHandled(), is(TestData.recipe1.getRecipeId()));
    }

    @Test
    public void requestToFavorites_shouldTriggerNavToFavorites() throws Exception {
        // Arrange (Given)

        // Act (When)
        mViewModel.requestNavToFavorites();

        // Assert (Then)
        Event<Object> response = LiveDataTestUtil.getOrAwaitValue(mViewModel.navToFavorites());
        assertThat(response.getContentIfNotHandled(), is(not(nullValue())));
    }

}
