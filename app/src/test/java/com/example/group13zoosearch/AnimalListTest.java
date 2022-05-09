package com.example.group13zoosearch;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import android.content.Context;
import static org.junit.Assert.*;

import androidx.test.core.app.ApplicationProvider;


public class AnimalListTest {

    //Test the testAnimalList respective classes to check if adding works as expected
    @Test
    public void testAnimalList()
    {
        AnimalNode node = new AnimalNode("asdf","monkey","King Kong", Arrays.asList("King","Big Monke"));
        AnimalList a = new AnimalList();
        a.addToSelectAnimal(node);
        assertEquals(a.getFirstValue().getID(),"King Kong");
    }
    @Test
    public void testAnimalListSetup()
    {

        List<AnimalNode> animalNodes = AnimalNode.loadNodeInfoJSON(ApplicationProvider.getApplicationContext(), "sample_node_info.json");
        assertEquals(animalNodes.get(0).getName(),"" );
    }

}
