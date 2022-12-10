package travellingsalesman.model;

import java.util.Objects;

public class Node {

    protected long number;
    protected double x;
    protected double y;

    protected int deep;

    protected Node prev;
    protected Node next;

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node(int number, double x, double y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public Node() {

    }

    public boolean isCluster() {
        return false;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
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
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < deep; i++) {
            spaces.append("  ");
        }

        if(prev != null && next != null) {
            return  "\n" + spaces + "Node{" +
                    "number=" + number +
                    ", x=" + x +
                    ", y=" + y +
                    ", prev=" + prev.getNumber() +
                    ", next=" + next.getNumber() +
                    '}';
        } else {
            return  "\n" + spaces + "Node{" +
                    "number=" + number +
                    ", x=" + x +
                    ", y=" + y +
                    ", prev=-1" +
                    ", next=-1" +
                    '}';
        }
    }
}
