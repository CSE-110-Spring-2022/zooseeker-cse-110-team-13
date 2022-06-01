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
public class BriefDirectionTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void briefDirectionTest2() {
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
                                        8),
                                1),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.add_button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_recycler),
                                        6),
                                1),
                        isDisplayed()));
        materialTextView2.perform(click());

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

        ViewInteraction textView = onView(
                allOf(withId(R.id.currentAnimaltxt), withText("Hippos"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Hippos")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.direction_txt), withText("3. Head 1900.0ft on Hippo Trail towards Treetops Way / Fern Canyon Trail"),
                        withParent(withParent(withId(R.id.direction_items))),
                        isDisplayed()));
        textView2.check(matches(withText("3. Head 1900.0ft on Hippo Trail towards Treetops Way / Fern Canyon Trail")));
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
