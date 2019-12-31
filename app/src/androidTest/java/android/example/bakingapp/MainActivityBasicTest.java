package android.example.bakingapp;


import android.widget.Toast;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
//import androidx.test.runner.AndroidJUnit4;

//import android.support.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

    private IdlingResource mIdlingResource;

    public static final String BAKING_NAME = "Nutella Pie";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule=new ActivityTestRule<>( MainActivity.class );



    @Test
    public void clickGridViewItem_OpenRecipe() {


      Espresso.registerIdlingResources( mActivityTestRule.getActivity().getIdlingResource());


        onView(allOf(withId(R.id.Rname),withText( BAKING_NAME ))).check( matches(isDisplayed()  ) );






                        Espresso.unregisterIdlingResources( mActivityTestRule.getActivity().getIdlingResource());
    }



    @After


    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }}

}
