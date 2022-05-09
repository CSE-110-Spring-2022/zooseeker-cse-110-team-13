package com.example.group13zoosearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

public class SearchAdapterTest {
    private MainActivity activity;

    @Test
    public void checkValidityMain() {
        Button exhibit_button = (Button)activity.findViewById(R.id.view_exhibits_button);
        assertNotNull("Button cannot be found", exhibit_button);
        assertTrue("Button is working",
                "View exhibits list".equals(exhibit_button.getText().toString()));
    }
}
