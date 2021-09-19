package com.example;

import java.util.List;

public class Node {

    private List<Character> column;
    private int distanceToSource;

    public Node(List<Character> column, int distance)
    {
        this.column = column;
        this.distanceToSource = distance;
    }

    public void printColumn()
    {
        for (Character chr : this.column)
        {
            System.out.print(chr+ " ");
        }
        System.out.println("\n");
    }

    public List<Character> getColumn()
    {
        return this.column;
    }

    public void setColumn(int index, int steps)
    {
        this.column.set(index, String.valueOf(steps).charAt(0));
    }

    public boolean getInfoByIndex(int index)
    {
        if (this.column.get(index) != '*' && Integer.valueOf(this.column.get(index)) < 1)
        {
            return true;
        }
        return false;
    }
}