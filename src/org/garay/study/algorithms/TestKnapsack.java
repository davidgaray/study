package org.garay.study.algorithms;

import org.garay.study.datastructures.graph.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by technique on 11/22/15.
 */
public class TestKnapsack {


    private Knapsack knapsack;

    @Before
    public void setup() {
        this.knapsack = new Knapsack();
    }

    @Test
    public void verifyKnapsackValueWithSimpleFourItemCase() {
        List<Knapsack.Item> items = new ArrayList<>(4);
        items.add(new Knapsack.Item(1,2));
        items.add(new Knapsack.Item(3,3));
        items.add(new Knapsack.Item(5,5));
        items.add(new Knapsack.Item(7,7));
        Knapsack.KnapsackResult knapsackResult = knapsack.maximizeValue(items, 8);
        assertEquals("Expected result of value 9", 9, knapsackResult.getTotalValue());
    }

    @Test
    public void verifyKnapsackItemsWithSimpleFourItemCase() {
        List<Knapsack.Item> items = new ArrayList<>(4);
        items.add(new Knapsack.Item(1,2));
        items.add(new Knapsack.Item(3,3));
        items.add(new Knapsack.Item(5,5));
        items.add(new Knapsack.Item(7,7));
        Knapsack.KnapsackResult knapsackResult = knapsack.maximizeValue(items, 8);
        List<Knapsack.Item> selectedItems = knapsackResult.getItems();
        assertEquals("Expected result with only two items", 2, selectedItems.size());
        assertTrue("Expected result contains first item", selectedItems.contains(items.get(0)));
        assertTrue("Expected result contains last item", selectedItems.contains(items.get(3)));
    }

    @Test
    public void verifyKnapsackWithNoSelectableItems() {
        List<Knapsack.Item> items = new ArrayList<>(4);
        items.add(new Knapsack.Item(11,22));
        items.add(new Knapsack.Item(33,33));
        items.add(new Knapsack.Item(55,55));
        items.add(new Knapsack.Item(77,77));
        Knapsack.KnapsackResult knapsackResult = knapsack.maximizeValue(items, 10);
        assertEquals("Expected no value", 0, knapsackResult.getTotalValue());
        List<Knapsack.Item> selectedItems = knapsackResult.getItems();
        assertEquals("Expected result with only two items", 0, selectedItems.size());
    }




}
