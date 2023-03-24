import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Graph {
    private HashSet<Node> nodes = new HashSet<>();
    private HashMap<Node, List<Node>> adjList = new HashMap<>();

    public void addEdge(int outNode, int inNode) {
        if (!checkNode(outNode)) createNode(outNode);
        if (!checkNode(inNode)) createNode(inNode);
        if (checkNode(outNode) && checkNode(inNode))
            createEdge(getNode(outNode), getNode(inNode));
    }

    public void displayGraph() {
        for (Node node : nodes) {
            System.out.println(
                    "Vertex " + node.getIndex() + ": rank = " + node.getRank() +
                            ", out-degree = " + node.getOutDegree()
            );
            System.out.println(
                    "Edges from " + node.getIndex() + " to: " +
                            adjListString(adjList.get(node)).replace(" ", ", ")
            );
            System.out.println();
        }
    }

    public void saveGraph() throws IOException {
        PrintWriter output = new PrintWriter("output.txt");
        for (Node node : nodes) {
            output.println(
                    node.getIndex() + " " +
                    node.getOutDegree() + " " +
                    adjListString(adjList.get(node))
            );
        }
        output.close();
    }

    private void createNode(int vertexId) {
        Node node = new Node(vertexId);
        nodes.add(node);
        adjList.put(node, new ArrayList<>());
    }

    private boolean checkNode(int vertexId) {
        boolean nodeExists = false;

        Node node = new Node(vertexId);

        if (nodes.contains(node)) nodeExists = true;

        return nodeExists;
    }

    private Node getNode(int index) {
        Node result = null;
        for (Node node : nodes) {
            if (node.getIndex() == index) result = node;
        }
        return result;
    }

    private void createEdge(Node parentNode, Node adjNode) {
        adjList.get(parentNode).add(adjNode);
        adjustDegrees(parentNode, adjNode);
    }

    private void adjustDegrees(Node outNode, Node inNode) {
        outNode.addOutDegree();
        inNode.addInDegree();
    }

    private String adjListString(List<Node> adjList) {
        String output = "";
        for (Node node : adjList) {
            output += node.getIndex() + " ";
        }
        return output.trim();
    }
}
