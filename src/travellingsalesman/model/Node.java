package travellingsalesman.model;

import java.util.Objects;

public class Node {

    private int number;
    protected double x;
    protected double y;

    public Node(int number, double x, double y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public Node() {

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Double.compare(node.x, x) == 0 && Double.compare(node.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Node{" +
                "number=" + number +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
