package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App 
{
    private Map<Integer, Node> column;
    private int sourceCell;
    private int result = COLUMNS*ROWS;
    private static final int COLUMNS = 3;
    private static final int ROWS = 2;
    private static final char TARGET = 't';
    private static final char SOURCE = 's';
    private static final char VALID_INDEX = '0';
    
    public App()
    {
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
            if (col.equals(SOURCE))
            {
                Map<Integer, List<Integer>> shortestPathResult = new HashMap<>(); 
                //goToLeft(index, 1, this.column.get(sourceCell), sourceCell, shortestPathResult);
                //goToRight(index, 1, this.column.get(sourceCell), sourceCell, shortestPathResult);
                //goUp();
                goDown(index, 1, this.column.get(sourceCell), sourceCell, shortestPathResult);
            }
            index++;
        }
    }

    private Map<Integer, List<Integer>> goToLeft(int index, int counter, Node column, int level, Map<Integer, List<Integer>> shortestPathResult)
    {
        Map<Integer, List<Integer>> shortestPathResultTemporary = new HashMap<>();
        shortestPathResultTemporary.putAll(shortestPathResult);
        if (index > 0) 
        {
            index--;
            char charAtIndex = column.getColumn().get(index);
            if (isIndexAtCharTarget(charAtIndex))
            {
                if (isTraverseLessThanShortestPath(counter))
                {
                    shortestPathResult.computeIfAbsent(level, k -> new ArrayList<>()).add(index);
                    result = counter;
                    System.out.println(result);
                    System.out.println(shortestPathResult);
                    return shortestPathResult;
                }
            }
            else if (isIndexValidAndTraverseLessThanShortestPath(charAtIndex, counter))
            {
                System.out.println("index: "+ index+"  level: "+level+"  counter: "+counter);
                shortestPathResultTemporary.computeIfAbsent(level, k -> new ArrayList<>()).add(index);
                Map<Integer, List<Integer>> left = goToLeft(index, counter+1, this.column.get(level), level, shortestPathResultTemporary);
                Map<Integer, List<Integer>> down = goDown(index, counter+1, this.column.get(level), level, shortestPathResultTemporary);
                if (!left.isEmpty())
                {
                    shortestPathResultTemporary.putAll(left);
                }
                if (!down.isEmpty())
                {
                    shortestPathResultTemporary.putAll(down);
                }
            }
        }
        return shortestPathResultTemporary;
    }

    private void goToRight(int index, int counter, Node column, int level, Map<Integer, List<Integer>> shortestPathResult)
    {
        if (index < COLUMNS) 
        {
            index++;
            char charAtIndex = column.getColumn().get(index);
            if (isIndexAtCharTarget(charAtIndex))
            {
                if (isTraverseLessThanShortestPath(counter))
                {
                    result = counter;
                    System.out.println(result);
                    System.out.println(shortestPathResult);
                }
            }
            else if (isIndexValidAndTraverseLessThanShortestPath(charAtIndex, counter))
            {
                shortestPathResult.computeIfAbsent(level, k -> new ArrayList<>()).add(index);
                goToRight(index, counter+1, column, level, shortestPathResult);
                goDown(index, counter+1, this.column.get(level), level, shortestPathResult);
            }
        }
    }

    private Map<Integer, List<Integer>> goDown(int index, int counter, Node column, int level, Map<Integer, List<Integer>> shortestPathResult)
    {
        Map<Integer, List<Integer>> shortestPathResultTemporary = new HashMap<>();
        shortestPathResultTemporary.putAll(shortestPathResult);
        if (level < ROWS)
        {   
            level++;
            char charAtIndex = this.column.get(level).getColumn().get(index);
            if (isIndexAtCharTarget(charAtIndex))
            {
                if (isTraverseLessThanShortestPath(counter))
                {
                    System.out.println("index: "+ index+"  level: "+level+"  counter: "+counter);
                    shortestPathResult.computeIfAbsent(level, k -> new ArrayList<>()).add(index);
                    result = counter;
                    System.out.println(result);
                    System.out.println(shortestPathResult);
                    return shortestPathResult;
                }
            }
            else if (isIndexValidAndTraverseLessThanShortestPath(charAtIndex, counter))
            {
                System.out.println("index: "+ index+"  level: "+level+"  counter: "+counter);
                shortestPathResultTemporary.computeIfAbsent(level, k -> new ArrayList<>()).add(index);
                Map<Integer, List<Integer>> down = goDown(index, counter+1, this.column.get(level), level, shortestPathResultTemporary);
                Map<Integer, List<Integer>> left = goToLeft(index, counter+1, this.column.get(level), level, shortestPathResultTemporary);
                if (!left.isEmpty())
                {
                    shortestPathResultTemporary.putAll(left);
                }
                if (!down.isEmpty())
                {
                    shortestPathResultTemporary.putAll(down);
                }
            }
        }
        return shortestPathResultTemporary;
    }

    private boolean isIndexAtCharTarget(char charAtIndex)
    {
       return charAtIndex == TARGET; 
    }

    private boolean isIndexValidAndTraverseLessThanShortestPath(char charAtIndex, int counter)
    {
       return charAtIndex == VALID_INDEX && isTraverseLessThanShortestPath(counter); 
    }

    private boolean isTraverseLessThanShortestPath(int counter)
    {
        return counter < result;
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
        test2.add('0');
        test2.add('0');

        List<Character> test3 = new ArrayList<>();
        test3.add('t');
        test3.add('*');
        test3.add('*');
        test3.add('t');

        Map<Integer, List<Character>> temp = new HashMap<>();

        temp.put(Integer.valueOf(0), test);
        temp.put(Integer.valueOf(1), test2);
        temp.put(Integer.valueOf(2), test3);

        App first = new App();

        first.createGrid(4, temp);

        first.printGrid();
        first.traverseGrid();
        first.printGrid();
    }
}
