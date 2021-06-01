package com.demo.ingredisearch.repository.sources.remote;

import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RemoteDataSource;
import com.demo.ingredisearch.repository.sources.ResponseCallback;
import com.demo.ingredisearch.util.Resource;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeApiClientTest {

    private class MyRecipeApiClient extends RecipeApiClient {
        @NotNull
        @Override
        protected ServiceGenerator getServiceGenerator() {
            return mServiceGenerator;
        }
    }

    @Mock
    ServiceGenerator mServiceGenerator;

    @Mock
    Call<RecipeSearchResponse> mRecipeSearchResponseCall;

    @Mock
    Response<RecipeSearchResponse> mRecipeSearchResponseResponse;

    @Mock
    Call<RecipeResponse> mRecipeResponseCall;

    @Mock
    Response<RecipeResponse> mRecipeResponseResponse;

    // SUT
    RemoteDataSource mRemoteDataSource;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

        when(mServiceGenerator.getRecipesService(anyString())).thenReturn(mRecipeSearchResponseCall);
        when(mServiceGenerator.getRecipeService(anyString())).thenReturn(mRecipeResponseCall);

        mRemoteDataSource = new MyRecipeApiClient();
    }

    @Test
    public void searchRecipes_whenFailedByNetworkError_returnsErrorResponse() {
        // Arrange (Given)
        doAnswer(invocation -> {
            Callback<RecipeSearchResponse> callback = invocation.getArgument(0);
            callback.onFailure(mRecipeSearchResponseCall, new IOException("Network Error"));
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                fail("should not be called");
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response, is(Resource.error("Network Error", null)));
            }
        });

    }

    @Test
    public void searchRecipes_whenFailedWithHTTPError_returnsErrorResponse() {
        // Arrange (Given)
        when(mRecipeSearchResponseResponse.isSuccessful()).thenReturn(false);
        when(mRecipeSearchResponseResponse.message()).thenReturn("HTTP Error");

        doAnswer(invocation -> {
            Callback<RecipeSearchResponse> callback = invocation.getArgument(0);
            callback.onResponse(mRecipeSearchResponseCall, mRecipeSearchResponseResponse);
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                fail("should not be called");
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response, is(Resource.error("HTTP Error", null)));
            }
        });
    }

    @Test
    public void searchRecipes_whenFailedWithAuthError_returnsErrorResponse() {
        // Arrange (Given)
        when(mRecipeSearchResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeSearchResponseResponse.code()).thenReturn(401);

        doAnswer(invocation -> {
            Callback<RecipeSearchResponse> callback = invocation.getArgument(0);
            callback.onResponse(mRecipeSearchResponseCall, mRecipeSearchResponseResponse);
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                fail("should not be called");
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response, is(Resource.error("401 Unauthorized. Token may be invalid", null)));
            }
        });
    }

    @Test
    public void searchRecipes_whenSucceedWithNullResult_returnsEmptyList() {
        // Arrange (Given)
        when(mRecipeSearchResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeSearchResponseResponse.body()).thenReturn(null);

        doAnswer(invocation -> {
            Callback<RecipeSearchResponse> callback = invocation.getArgument(0);
            callback.onResponse(mRecipeSearchResponseCall, mRecipeSearchResponseResponse);
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response, is(Resource.success(emptyList())));
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                fail("should not be called");
            }
        });
    }

    @Test
    public void searchRecipes_whenSucceed_returnsRecipesList() {
        // Arrange (Given)
        when(mRecipeSearchResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeSearchResponseResponse.body()).thenReturn(new RecipeSearchResponse(TestData.mRecipes));

        doAnswer(invocation -> {
            Callback<RecipeSearchResponse> callback = invocation.getArgument(0);
            callback.onResponse(mRecipeSearchResponseCall, mRecipeSearchResponseResponse);
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response, is(Resource.success(TestData.mRecipes)));
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                fail("should not be called");
            }
        });

    }

    @Test
    public void searchRecipe_whenFailedByNetworkError_returnsErrorResponse() {
        // Arrange (Given)
        doAnswer(invocation -> {
                    Callback<RecipeResponse> callback = invocation.getArgument(0);
                    callback.onFailure(mRecipeResponseCall, new IOException("Network Error"));
                    return null;
                }
        ).when(mRecipeResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipe("some query id", new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                fail("should not be called");
            }

            @Override
            public void onError(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response, is(Resource.error("Network Error", null)));
            }
        });
    }

    @Test
    public void searchRecipe_whenFailedWithHTTPError_returnsErrorResponse() {
        // Arrange (Given)
        when(mRecipeResponseResponse.isSuccessful()).thenReturn(false);
        when(mRecipeResponseResponse.message()).thenReturn("HTTP Error");

        doAnswer(invocation -> {
                    Callback<RecipeResponse> callback = invocation.getArgument(0);
                    callback.onFailure(mRecipeResponseCall, new IOException("HTTP Error"));
                    return null;
                }
        ).when(mRecipeResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipe("some query id", new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                fail("should not be called");
            }

            @Override
            public void onError(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response, is(Resource.error("HTTP Error", null)));
            }
        });
    }

    @Test
    public void searchRecipe_whenFailedWithAuthError_returnsErrorResponse() {
        // Arrange (Given)
        when(mRecipeResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeResponseResponse.code()).thenReturn(401);
        when(mRecipeResponseResponse.message()).thenReturn("401 Unauthorized. Token may be invalid");

        doAnswer(invocation -> {
                    Callback<RecipeResponse> callback = invocation.getArgument(0);
                    callback.onResponse(mRecipeResponseCall, mRecipeResponseResponse);
                    return null;
                }
        ).when(mRecipeResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipe("some query id", new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                fail("should not be called");
            }

            @Override
            public void onError(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response, is(Resource.error("401 Unauthorized. Token may be invalid", null)));
            }
        });
    }

    @Test
    public void searchRecipe_whenSucceedWithNullResult_returnsNull() {
        // Arrange (Given)
        when(mRecipeResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeResponseResponse.body()).thenReturn(null);

        // Act (When)
        mRemoteDataSource.searchRecipe("some query id", new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response, is(Resource.success(null)));
            }

            @Override
            public void onError(Resource<Recipe> response) {
                fail("should not be called");
            }
        });
    }
    @Test
    public void searchRecipe_whenSucceed_returnsRecipe() {
        // Arrange (Given)
        when(mRecipeResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeResponseResponse.body()).thenReturn(new RecipeResponse(TestData.recipeDetails01));

        doAnswer(invocation -> {
            Callback<RecipeResponse> callback = invocation.getArgument(0);
            callback.onResponse(mRecipeResponseCall, mRecipeResponseResponse);
            return null;
        }).when(mRecipeResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipe(TestData.recipe1.getRecipeId(), new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response, is(Resource.success(TestData.recipeDetails01)));
            }

            @Override
            public void onError(Resource<Recipe> response) {
                fail("should not be called");
            }
        });
    }

    @Mock
    ResponseCallback<Recipe> mRecipeResponseCallback;

    @Test
    public void searchRecipe_whenSucceed_returnsRecipe2() {
        // Arrange (Given)
        when(mRecipeResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeResponseResponse.body()).thenReturn(new RecipeResponse(TestData.recipeDetails01));

        doAnswer(invocation -> {
            Callback<RecipeResponse> callback = invocation.getArgument(0);
            callback.onResponse(mRecipeResponseCall, mRecipeResponseResponse);
            return null;
        }).when(mRecipeResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mRemoteDataSource.searchRecipe(TestData.recipe1.getRecipeId(), mRecipeResponseCallback);

        verify(mRecipeResponseCallback).onDataAvailable(Resource.success(TestData.recipeDetails01));
//        verifyNoMoreInteractions(mRecipeResponseCallback);
    }

}
