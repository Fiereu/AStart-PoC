package de.Fiereu.PokeHook.Pathfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    int x, y, f, g;
    int c;
    Grid grid;
    Colors Color;
    List<Node> Neighbours = new ArrayList<Node>();
    Node Parent;

    public Node(int x, int y, Grid grid)
    {
        this.x = x;
        this.y = y;
        this.Color = Colors.UNKNOWN;
        this.c = 1;
        this.grid = grid;
    }

    public void setColor(Colors c){ this.Color = c; }
    public String getColor(){ return this.Color.toString(); }

    public boolean IsSame(Node node) {
        return node.x == this.x && node.y == this.y;
    }

    public void InitNeighbors() {
        if(grid.GetNode(x-1, y).isPresent())
            Neighbours.add(grid.GetNode(x-1, y).get());
        if(grid.GetNode(x+1, y).isPresent())
            Neighbours.add(grid.GetNode(x+1, y).get());
        if(grid.GetNode(x, y-1).isPresent())
            Neighbours.add(grid.GetNode(x, y-1).get());
        if(grid.GetNode(x, y+1).isPresent())
            Neighbours.add(grid.GetNode(x, y+1).get());
    }

    enum Colors
    {
        UNKNOWN("."),
        OPEN("o"),
        CLOSED("x"),
        BLOCKED("#"),
        BESTROUTE("*");

        private final String text;

        Colors(String s) {
            this.text = s;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
