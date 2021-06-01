package com.demo.ingredisearch;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;

import com.demo.ingredisearch.repository.RecipeRepository;
import com.demo.ingredisearch.repository.sources.favorites.FakeFavoritesSource;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSource;
import com.demo.ingredisearch.util.AppExecutors;
import com.demo.ingredisearch.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;

public class BaseUITest {
    protected FakeRemoteDataSource mRemoteDataSource;
    protected FakeFavoritesSource mFavoritesSource;
    protected RecipeRepository mRecipeRepository;

    private RecipeApplication app;

    @Before
    public void init() {
//        mRemoteDataSource = new FakeRemoteDataSource(new SingleExecutors());
        Contants.FAKE_NETWORK_DELAY = 2000L;
        mRemoteDataSource = new FakeRemoteDataSource(new AppExecutors());
        mFavoritesSource = new FakeFavoritesSource();
        mRecipeRepository = RecipeRepository.getInstance(mRemoteDataSource, mFavoritesSource);
        app = ApplicationProvider.getApplicationContext();
        app.getInjection().setRepository(mRecipeRepository);

        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @After
    public void wrapUp() {
        mRecipeRepository.destroy();

        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }
}
