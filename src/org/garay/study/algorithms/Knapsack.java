package org.garay.study.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by technique on 11/22/15.
 */
public class Knapsack {

    public KnapsackResult maximizeValue(List<Item> items, int bagCapacity) {
        KnapsackResult result = new KnapsackResult();
        int[][] table = new int[bagCapacity+1][items.size()+1];
        for(int setIndex=1; setIndex <= items.size(); setIndex++) {
            Item curItem = items.get(setIndex-1);
            for(int curBagWeight=1; curBagWeight <= bagCapacity; curBagWeight++) {
                if(curBagWeight < curItem.getWeight()) {
                    //can't fit current item, use value of previous set
                    table[curBagWeight][setIndex] = table[curBagWeight][setIndex-1];
                    continue;
                }
                int valueIncludingItem = table[curBagWeight-curItem.getWeight()][setIndex-1] + curItem.getValue();
                table[curBagWeight][setIndex] = Math.max(valueIncludingItem, table[curBagWeight][setIndex-1]);
            }
        }
        result.totalValue = table[bagCapacity][items.size()];
        result.items = determineItemsSelected(table, items);
        return result;
    }

    private List<Item> determineItemsSelected(int[][] table, List<Item> availableItems) {
        int weightIndex = table.length - 1;
        int setIndex = table[0].length - 1;

        List<Item> selectedItems = new ArrayList<>();
        while(setIndex > 0 && weightIndex > 0) {
            if(table[weightIndex][setIndex] != table[weightIndex][setIndex-1]) {
                //we selected the current item (the value is greater with the current item selected)
                Item curItem = availableItems.get(setIndex-1);
                selectedItems.add(curItem);
                //reduce the set and weight accordingly
                setIndex -= 1;
                weightIndex -= curItem.getWeight();
            } else {
                //this item not used, reduce set by one
                setIndex -= 1;
            }
        }

        return selectedItems;
    }

    public static class KnapsackResult {
        protected int totalValue;
        protected List<Item> items = new ArrayList<>();

        public int getTotalValue() {
            return totalValue;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    public static class Item {
        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        protected int weight;
        protected int value;

        public int getWeight() {
            return weight;
        }

        public int getValue() {
            return value;
        }
    }
}
