package com.example.group13zoosearch;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.group13zoosearch.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchAcitivityAddTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void searchAcitivityAddTest() {
        ViewInteraction materialButton = onView(
allOf(withId(R.id.search_button), withText("Search exhibits"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()));
        materialButton.perform(click());
        
        ViewInteraction actionMenuItemView = onView(
allOf(withId(R.id.action_search), withContentDescription("search"),
childAtPosition(
childAtPosition(
withId(androidx.appcompat.R.id.action_bar),
1),
0),
isDisplayed()));
        actionMenuItemView.perform(click());
        
        ViewInteraction appCompatImageView = onView(
allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Search"),
childAtPosition(
allOf(withClassName(is("android.widget.LinearLayout")),
childAtPosition(
withId(R.id.action_search),
0)),
1),
isDisplayed()));
        appCompatImageView.perform(click());
        
        ViewInteraction searchAutoComplete = onView(
allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
childAtPosition(
allOf(withClassName(is("android.widget.LinearLayout")),
childAtPosition(
withClassName(is("android.widget.LinearLayout")),
1)),
0),
isDisplayed()));
        searchAutoComplete.perform(replaceText("koi"), closeSoftKeyboard());
        
        ViewInteraction materialTextView = onView(
allOf(withId(R.id.add_button), withText("+"),
childAtPosition(
childAtPosition(
withId(R.id.search_recycler),
0),
1),
isDisplayed()));
        materialTextView.perform(click());
        
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
allOf(withId(R.id.search_button), withText("Search exhibits"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()));
        materialButton3.perform(click());
        
        ViewInteraction materialTextView2 = onView(
allOf(withId(R.id.add_button), withText("+"),
childAtPosition(
childAtPosition(
withId(R.id.search_recycler),
0),
1),
isDisplayed()));
        materialTextView2.perform(click());
        
        ViewInteraction materialButton4 = onView(
allOf(withId(R.id.Go_Back_Button), withText("GO BACK"),
childAtPosition(
allOf(withId(R.id.relativeLayout),
childAtPosition(
withId(android.R.id.content),
0)),
1),
isDisplayed()));
        materialButton4.perform(click());
        
        ViewInteraction materialButton5 = onView(
allOf(withId(R.id.view_exhibits_button), withText("View exhibits list"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
1),
isDisplayed()));
        materialButton5.perform(click());
        
        ViewInteraction textView = onView(
allOf(withId(R.id.num_of_exhibits), withText("2 exhibits have been selected!"),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()));
        textView.check(matches(withText("2 exhibits have been selected!")));
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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
