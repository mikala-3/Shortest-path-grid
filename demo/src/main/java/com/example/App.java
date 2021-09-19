package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    private Map<Integer, Node> column;
    private int rowsAndColumns;
    private int sourceCell;

    public App(int rowsAndColumns)
    {
        this.rowsAndColumns = rowsAndColumns;
        this.column = new HashMap<Integer, Node>();
    }

    public void createGrid(int rowsAndColumns, Map<Integer, List<Character>> grid)
    {   
        checkForSourceCell(grid);
        for (Map.Entry<Integer, List<Character>> col : grid.entrySet())
        {
            int distanceToSource = Math.abs(sourceCell - col.getKey());
            this.column.put(col.getKey(), new Node(col.getValue(), distanceToSource));
        }
    }

    private void checkForSourceCell(Map<Integer, List<Character>> grid)
    {
        for (Map.Entry<Integer, List<Character>> column : grid.entrySet())
        {
               if (column.getValue().contains('s'))
               {
                   this.sourceCell = column.getKey();
               } 
        }
    }

    private void traverseGrid()
    {   int index = 0;
        for (Character col : this.column.get(sourceCell).getColumn())
        {
            if (col.equals('s'))
            {
                goToLeft(index, 1, this.column.get(sourceCell));
                //goToRight();
                //goUp();
                //goDown();
            }
            index++;
        }
    }

    private void goToLeft(int index, int counter, Node column)
    {
        if (index > 0)
        {
            index--;
            column.setColumn(index, counter);
        }
        //call left, right down up
        if (index != 0)
        {
            goToLeft(index, counter+1, column);
        }
    }
    private void printGrid()
    {
        for (Map.Entry<Integer, Node> col : this.column.entrySet())
        {
            col.getValue().printColumn();
        }
    }
    public static void main( String[] args )
    {
        System.out.println( "Creating new instance2");
        List<Character> test = new ArrayList<>();
        test.add('0');
        test.add('0');
        test.add('s');
        test.add('0');
        
        List<Character> test2 = new ArrayList<>();
        test2.add('0');
        test2.add('*');
        test2.add('*');
        test2.add('0');

        List<Character> test3 = new ArrayList<>();
        test3.add('0');
        test3.add('t');
        test3.add('0');
        test3.add('0');

        Map<Integer, List<Character>> temp = new HashMap<>();

        temp.put(Integer.valueOf(1), test);
        temp.put(Integer.valueOf(2), test2);
        temp.put(Integer.valueOf(4), test3);

        App first = new App(4);

        first.createGrid(4, temp);

        first.traverseGrid();
        first.printGrid();
    }
}
