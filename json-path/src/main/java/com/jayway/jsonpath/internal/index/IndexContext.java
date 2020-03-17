package com.jayway.jsonpath.internal.index;

import com.jayway.jsonpath.internal.index.node.Node;

import java.util.LinkedList;
import java.util.Map;

public class IndexContext {
    private Map<String, LinkedList<Node>> objectsPartitions;
    private Map<Long, LinkedList<Node>> arraysPartitions;

    public IndexContext(Map<String, LinkedList<Node>> objectPartitions, Map<Long, LinkedList<Node>> arrayPartitions) {
        this.objectsPartitions = objectPartitions;
        this.arraysPartitions = arrayPartitions;
    }

    public LinkedList<Node> open(String objectLabel) {
        return getObjectOrEmptyStream(objectLabel);
    }

    public LinkedList<Node> open(Long arrayIndex) {
        return getArrayOrEmptyStream(arrayIndex);
    }


    private LinkedList<Node> getObjectOrEmptyStream(String objectLabel) {
        LinkedList<Node> ret = this.objectsPartitions.get(objectLabel);
        if (ret == null) ret = new LinkedList<Node>();
        return ret;
    }

    private LinkedList<Node> getArrayOrEmptyStream(Long arrayIndex) {
        LinkedList<Node> ret = this.arraysPartitions.get(arrayIndex);
        if (ret == null) ret = new LinkedList<Node>();
        return ret;
    }
}
