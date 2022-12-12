package travellingsalesman.model;

import javafx.util.Pair;
import travellingsalesman.Util;

public class NodesPair extends Pair<Node, Node> {
    public NodesPair(Node key, Node value) {
        super(key, value);
    }

    public double getLength() {
        return Util.getLength(this.getKey(), this.getValue());
    }
}
