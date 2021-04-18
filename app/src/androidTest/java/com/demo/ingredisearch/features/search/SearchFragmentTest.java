package com.demo.ingredisearch.features.search;

import org.junit.Test;

public class SearchFragmentTest {

    @Test
    public void searchFragmentInView() throws Exception {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
    }

    @Test
    public void search_validQuery_navigateToSearchResultsView() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
    }

    @Test
    public void search_emptyQuery_displayWarningSnackBar() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
        // check snackbar is displayed: search_query_required
    }

    @Test
    public void search_emptyQuery_displayWarningSnackBar_shouldNotDisplayAgain_whenRotated() {
        // Arrange (Given)

        // Act (When)

        // Assert (Then)
        // check snackbar is displayed

        // Rotate screen

        // check snackbar does not exist
    }
}