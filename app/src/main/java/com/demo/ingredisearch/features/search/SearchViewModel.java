package com.demo.ingredisearch.features.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    public void search(String query) {
        if (query == null || query.isEmpty()) {
            mIsEmpty.setValue(true);
        } else {
            // in the near future ....
            mNavToSearchResults.setValue(query);
        }
    }

    private final MutableLiveData<String> mNavToSearchResults = new MutableLiveData<>();

    public LiveData<String> navToSearchResults() {
        return mNavToSearchResults;
    }

    private final MutableLiveData<Boolean> mIsEmpty = new MutableLiveData<>();

    public LiveData<Boolean> isEmpty() {
        return mIsEmpty;
    }

    public void doneIsEmpty() {
        mIsEmpty.setValue(false);
    }

    public void doneNavToSearchResults() {
        mNavToSearchResults.setValue(null);
    }
}
