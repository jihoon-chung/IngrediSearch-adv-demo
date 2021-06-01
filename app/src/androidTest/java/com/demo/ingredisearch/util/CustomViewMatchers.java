package com.demo.ingredisearch.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.is;

public interface CustomViewMatchers {

    static Matcher<View> withToolbarTitle(@IdRes int titleId) {
        Context context = ApplicationProvider.getApplicationContext();
        return withToolbarTitle(context.getString(titleId));
    }

    static Matcher<View> withToolbarTitle(String title) {
        return withToolbarTitle(is(title));
    }

    static Matcher<View> withToolbarTitle(Matcher<String> matcher) {
//        return new TypeSafeMatcher<View>() {
//            @Override
//            protected boolean matchesSafely(View item) {
//                Toolbar toolbar;
//                if (item instanceof Toolbar) {
//                    toolbar = (Toolbar) item;
//                    return matcher.matches(toolbar.getTitle().toString());
//                } else
//                    return false;
//            }
//
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("toolbar title ");
//                matcher.describeTo(description);
//            }
//        };

       return new BoundedMatcher<View, Toolbar>(Toolbar.class) {

           @Override
           public void describeTo(Description description) {
                description.appendText("toolbar title ");
                matcher.describeTo(description);
           }

           @Override
           protected boolean matchesSafely(Toolbar toolbar) {
               return matcher.matches(toolbar.getTitle().toString());
           }
       };
    }



    static RecyclerViewMatcher withRecyclerView(@IdRes final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    /*
     * Using the RecyclerViewMatcher, you can not only perform actions on an
     * item at a specific position in a RecyclerView, but also check that some
     * content is contained within a descendant of a specific item.
     */
    class RecyclerViewMatcher {
        private final int recyclerViewId;

        public RecyclerViewMatcher(@IdRes final int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        public Matcher<View> atPositionOnView(final int position, @IdRes final int targetViewId) {

            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;

                public void describeTo(Description description) {
                    int id = targetViewId == -1 ? recyclerViewId : targetViewId;
                    String idDescription = Integer.toString(recyclerViewId);
                    if (this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(id);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)", id);
                        }
                    }

                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {

                    this.resources = view.getResources();

                    if (childView == null) {
                        RecyclerView recyclerView = view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        }
                        else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }
                }
            };
        }
    }

}
