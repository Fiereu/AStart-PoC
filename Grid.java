package de.Fiereu.PokeHook.Pathfinder;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class Grid {

    int w, h;
    Node[][] Array;

    public Grid(int w, int h)
    {
        this.w = w;
        this.h = h;
        Array = new Node[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Array[i][j] = new Node(i, j, this);
                Random r = new Random();
                if(r.nextInt(5) == 4)
                    Array[i][j].setColor(Node.Colors.BLOCKED);
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Array[i][j].InitNeighbors();
            }
        }
    }

    public void draw()
    {
        System.out.println("Grid:");
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                System.out.printf(" %s ",Array[i][j].getColor());
            }
            System.out.print("\n");
        }
    }

    public Optional<Node> GetNode(int x, int y)
    {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if(Array[i][j].x == x && Array[i][j].y == y)
                    return Optional.of(Array[i][j]);
            }
        }
        return Optional.empty();
    }

    public boolean IsNodeInBounds(Node node)
    {
        return node.x >= 0 && node.y >= 0 && node.x < this.w && node.y < this.h;
    }

    public void drawRoute(Node[] finalRoute) {
        for (Node node: finalRoute) {
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    if(Array[i][j].x == node.x && Array[i][j].y == node.y)
                        Array[i][j].setColor(Node.Colors.BESTROUTE);
                }
            }
        }
    }
}
