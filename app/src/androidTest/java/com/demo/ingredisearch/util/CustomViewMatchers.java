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

    static Matcher<View> withToolbarTitle(@IdRes int id) {
        Context context = ApplicationProvider.getApplicationContext();
        return withToolbarTitle(context.getString(id));
    }

    static Matcher<View> withToolbarTitle(CharSequence title) {
        return withToolbarTitle(is(title));
    }

    static Matcher<View> withToolbarTitle(Matcher<? extends CharSequence> matcher) {
        return new ToolbarTitle(matcher);
    }

//    class ToolbarTitle extends TypeSafeMatcher<View> {
//        private Matcher<? extends CharSequence> matcher;
//
//        public ToolbarTitle(Matcher<? extends CharSequence> matcher) {
//            this.matcher = matcher;
//        }
//
//        @Override
//        protected boolean matchesSafely(View view) {
//            Toolbar toolbar;
//            if (view instanceof Toolbar) {
//                toolbar = (Toolbar) view;
//                return matcher.matches(toolbar.getTitle().toString());
//            }
//            return false;
//        }
//
//        @Override
//        public void describeTo(Description description) {
//            description.appendText("toolbar title: ");
//            matcher.describeTo(description);
//        }
//    }

    class ToolbarTitle extends BoundedMatcher<View, Toolbar> {
        private Matcher<? extends CharSequence> matcher;

        public ToolbarTitle(Matcher<? extends CharSequence> matcher) {
            super(Toolbar.class);
            this.matcher = matcher;
        }

        @Override
        protected boolean matchesSafely(Toolbar toolbar) {
            return matcher.matches(toolbar.getTitle().toString());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("toolbar title: ");
            matcher.describeTo(description);
        }
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
