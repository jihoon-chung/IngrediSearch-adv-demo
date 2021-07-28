package com;

import com.demo.ingredisearch.repository.sources.favorites.FakeFavoritesSourceTest;
import com.demo.ingredisearch.repository.sources.favorites.SharedPreferencesFavoritesSourceUnitTest;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSourceTest;
import com.demo.ingredisearch.repository.sources.remote.RecipeApiClientTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({
        RecipeApiClientTest.class,
        FakeRemoteDataSourceTest.class,
        SharedPreferencesFavoritesSourceUnitTest.class,
        FakeFavoritesSourceTest.class
})
@RunWith(Suite.class)
public class LocalUnitTests {
}
