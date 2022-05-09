package com.example.group13zoosearch;

import org.junit.Test;

import java.util.Arrays;
import static org.junit.Assert.*;
public class AnimalListTest {
    @Test
    public void testAnimalList()
    {
        AnimalNode node = new AnimalNode("asdf","monkey","King Kong", Arrays.asList("King","Big Monke"));
        AnimalList a = new AnimalList();
        a.addToSelectAnimal(node);
        assertEquals(a.getFirstValue().getID(),"King Kong");
    }


}
