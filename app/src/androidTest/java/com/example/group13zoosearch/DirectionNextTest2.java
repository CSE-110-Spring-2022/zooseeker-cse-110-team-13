package com.example.group13zoosearch;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DirectionNextTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void directionNextTest2() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_button), withText("Search exhibits"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.add_button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_recycler),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.add_button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_recycler),
                                        1),
                                1),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.add_button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_recycler),
                                        2),
                                1),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.add_button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_recycler),
                                        3),
                                1),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction materialTextView5 = onView(
                allOf(withId(R.id.add_button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_recycler),
                                        5),
                                1),
                        isDisplayed()));
        materialTextView5.perform(click());

        ViewInteraction materialTextView6 = onView(
                allOf(withId(R.id.add_button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_recycler),
                                        6),
                                1),
                        isDisplayed()));
        materialTextView6.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.Go_Back_Button), withText("GO BACK"),
                        childAtPosition(
                                allOf(withId(R.id.relativeLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.Directionbtn), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.nextDirections), withText("NEXT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.skip_btn), withText("SKIP"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.currentAnimaltxt), withText("Bali Mynah"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Bali Mynah")));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.previous_btn), withText("Prev."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.currentAnimaltxt), withText("Koi Fish"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("Koi Fish")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
