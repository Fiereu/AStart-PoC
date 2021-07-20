package de.Fiereu.PokeHook.Pathfinder;

import java.util.ArrayList;
import java.util.List;

public class AStar {

    public static void main(String[] args) {
        int w = 5;
        int h = 5;
        Grid grid = new Grid(w, h);
        grid.draw();
        AStar astar = new AStar(grid, grid.GetNode(0,0).get(), grid.GetNode(w-1,h-1).get());
        astar.solve();
    }

    Grid grid;
    List<Node> OpenSet = new ArrayList<Node>();
    List<Node> ClosedSet = new ArrayList<Node>();
    Node StartNode, DestinationNode;

    public AStar(Grid grid, Node StartNode, Node DestinationNode)
    {
        this.grid = grid;
        this.StartNode = StartNode;
        this.DestinationNode = DestinationNode;
    }

    public Node[] solve()
    {
        OpenSet.add(StartNode);
        while (OpenSet.size() > 0)
        {
            Node CurrentNode = OpenSet.get(0);

            for (Node n: OpenSet) {
                if(n.f > CurrentNode.f)
                    CurrentNode = n;
            }

            if(CurrentNode.Color == Node.Colors.BLOCKED)
                continue;

            RemoveNodeFromNodeSet(OpenSet, CurrentNode);

            if(CurrentNode.IsSame(this.DestinationNode))
            {
                // Solved
                System.out.println("Solved");
                Node[]  FinalRoute = GetRoute(CurrentNode);
                grid.drawRoute(FinalRoute);
                grid.draw();
                return FinalRoute;
            }

            CurrentNode.setColor(Node.Colors.CLOSED);
            ClosedSet.add(CurrentNode);

            for (Node Neighbor: CurrentNode.Neighbours) {
                if(IsNodeInNodeSet(ClosedSet, Neighbor))
                    continue;
                if(Neighbor.Color == Node.Colors.BLOCKED)
                    continue;

                int g = CurrentNode.g + Neighbor.c;

                if(IsNodeInNodeSet(OpenSet, Neighbor) && g > Neighbor.g)
                    continue;

                Neighbor.Parent = CurrentNode;

                Neighbor.g = g;

                int f = g + Heuristic(Neighbor, this.DestinationNode, Neighbor.c);

                if(IsNodeInNodeSet(OpenSet, Neighbor))
                {
                    RemoveNodeFromNodeSet(OpenSet, Neighbor);
                }

                Neighbor.setColor(Node.Colors.OPEN);
                OpenSet.add(Neighbor);

            }
            grid.draw();
        }

        return null;
    }

    public boolean IsNodeInNodeSet(List<Node> Set, Node node)
    {
        for (Node n: Set) {
            if(node.IsSame(n))
                return true;
        }
        return false;
    }

    public void RemoveNodeFromNodeSet(List<Node> Set, Node node)
    {
        Set.removeIf(node::IsSame);
    }

    public static int Heuristic(Node node, Node destinationNode, int c)
    {
        return (Math.abs(node.x - destinationNode.x) + Math.abs(node.y - destinationNode.y)) * c;
    }

    public Node[] GetRoute(Node node)
    {
        List<Node> Nodes = new ArrayList<>();
        Nodes.add(node);
        while (node.Parent != null)
        {
            Nodes.add(node.Parent);
            node = node.Parent;
        }
        Node[] FinalRoute = new Node[Nodes.size()];
        Nodes.toArray(FinalRoute);
        return FinalRoute;
    }
}
