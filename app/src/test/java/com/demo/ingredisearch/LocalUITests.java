package com.demo.ingredisearch;

import com.demo.ingredisearch.features.details.RecipeDetailsViewModelTest;
import com.demo.ingredisearch.features.favorites.FavoritesViewModelTest;
import com.demo.ingredisearch.features.search.SearchViewModelTest;
import com.demo.ingredisearch.features.searchresults.SearchResultsViewModelTest;
import com.demo.ingredisearch.repository.RecipeRepositoryTest;
import com.demo.ingredisearch.repository.sources.favorites.FakeFavoritesSourceTest;
import com.demo.ingredisearch.repository.sources.favorites.SharedPreferencesFavoritesSourceUnitTest;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSourceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({
        FakeRemoteDataSourceTest.class,
        FakeFavoritesSourceTest.class,
        RecipeRepositoryTest.class,
        RecipeDetailsViewModelTest.class,
        FavoritesViewModelTest.class,
        SearchViewModelTest.class,
        SearchResultsViewModelTest.class,
        SharedPreferencesFavoritesSourceUnitTest.class
})
@RunWith(Suite.class)
public class LocalUITests {
}
