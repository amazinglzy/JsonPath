package com.jayway.jsonpath.internal.index;

import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.index.node.SingleNodeIterator;

import java.util.LinkedList;
import java.util.Map;

public class IndexContext {
    private Map<String, LinkedList<Node>> objectsPartitions;
    private Map<Long, LinkedList<Node>> arraysPartitions;

    public IndexContext(Map<String, LinkedList<Node>> objectPartitions, Map<Long, LinkedList<Node>> arrayPartitions) {
        this.objectsPartitions = objectPartitions;
        this.arraysPartitions = arrayPartitions;
    }

    public NodeIterator open(String objectLabel) {
        return getObjectOrEmptyStream(objectLabel);
    }

    public NodeIterator open(Long arrayIndex) {
        return getArrayOrEmptyStream(arrayIndex);
    }


    private NodeIterator getObjectOrEmptyStream(String objectLabel) {
        LinkedList<Node> ret = this.objectsPartitions.get(objectLabel);
        if (ret == null) ret = new LinkedList<Node>();
        return new SingleNodeIterator(ret);
    }

    private NodeIterator getArrayOrEmptyStream(Long arrayIndex) {
        LinkedList<Node> ret = this.arraysPartitions.get(arrayIndex);
        if (ret == null) ret = new LinkedList<Node>();
        return new SingleNodeIterator(ret);
    }
}
