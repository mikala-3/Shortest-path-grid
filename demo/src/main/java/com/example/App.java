package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Hello world!
 *
 */
public class App 
{
    private Map<Integer, Node> column;
    private int rowsAndColumns;
    private int sourceCell;
    private static final int COLUMNS = 3;
    private static final int ROWS = 2;

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
        this.sourceCell = grid.entrySet().stream().filter(column -> column.getValue().contains('s')).mapToInt(column -> column.getKey()).sum();
    }

    private void traverseGrid()
    {   int index = 0;
        for (Character col : this.column.get(sourceCell).getColumn())
        {
            if (col.equals('s'))
            {
                Multimap<Integer, Integer> shortestPathResult = ArrayListMultimap.create(); 
                goToLeft(index, 1, this.column.get(sourceCell), sourceCell, shortestPathResult);
                //goToRight(index, 1, this.column.get(sourceCell), sourceCell);
                //goUp();
                //goDown(index, 1, this.column.get(sourceCell), sourceCell);
            }
            index++;
        }
    }

    private void goToLeft(int index, int counter, Node column, int level, Multimap<Integer, Integer> shortestPathResult)
    {
        if (index > 0) 
        {
            index--;
            char charAtIndex = column.getColumn().get(index);
            if (charAtIndex == '0'|| charAtIndex < counter)
            {
                column.setColumn(index, counter);
                shortestPathResult.add()
                //call left, down up
                goToLeft(index, counter+1, column, level, shortestPathResult);
                if (level < ROWS)
                {
                    goDown(index, counter+1, this.column.get(level), level);
                }

            }
        }
    }

    private void goToRight(int index, int counter, Node column, int level)
    {
        if (index < COLUMNS)
        {
            index++;
            char charAtIndex = column.getColumn().get(index);
            if (charAtIndex != '*')
            {
                column.setColumn(index, counter);
                //call right down up
                goToRight(index, counter+1, column, level);
            }
        }
    }

    private void goDown(int index, int counter, Node column, int level)
    {
        if (level < ROWS)
        {   
            level++;
            char charAtIndex = this.column.get(level).getColumn().get(index);
            if (charAtIndex == '0' || charAtIndex < counter)
            {
                this.column.get(level).setColumn(index, counter);
                //call right down up
                System.out.println("hej2"+index+counter);
                goToLeft(index, counter+1, this.column.get(level), level);
                //goToRight(index+1, counter+1, this.column.get(level+1), level+1);
                goDown(index, counter+1, this.column.get(level), level);

            }
        }
        if(level == ROWS)
        {
            System.out.println("hej");
            goToLeft(index, counter+1, this.column.get(level), level);
            //goToRight(index+1, counter+1, this.column.get(level), level);
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
        test.add('*');
        test.add('0');
        test.add('s');
        test.add('*');
        
        List<Character> test2 = new ArrayList<>();
        test2.add('0');
        test2.add('0');
        test2.add('*');
        test2.add('0');

        List<Character> test3 = new ArrayList<>();
        test3.add('0');
        test3.add('t');
        test3.add('0');
        test3.add('0');

        Map<Integer, List<Character>> temp = new HashMap<>();

        temp.put(Integer.valueOf(0), test);
        temp.put(Integer.valueOf(1), test2);
        temp.put(Integer.valueOf(2), test3);

        App first = new App(4);

        first.createGrid(4, temp);

        first.traverseGrid();
        first.printGrid();
    }
}
