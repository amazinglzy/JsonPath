package com.jayway.jsonpath.internal.index;

import com.jayway.jsonpath.internal.index.node.CombinedNodeIterator;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.index.node.SingleNodeIterator;
import com.jayway.jsonpath.internal.path.ArrayIndexOperation;
import com.jayway.jsonpath.internal.path.ArraySliceOperation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IndexContext {
    private Map<String, LinkedList<Node>> objectsPartitions;
    private Map<Long, LinkedList<Node>> arraysPartitions;

    public IndexContext(Map<String, LinkedList<Node>> objectPartitions, Map<Long, LinkedList<Node>> arrayPartitions) {
        this.objectsPartitions = objectPartitions;
        this.arraysPartitions = arrayPartitions;
    }

    public NodeIterator openAll() {
        NodeIterator iter = null;
        for (String label: this.objectsPartitions.keySet()) {
            if (iter == null) iter = getObjectOrEmptyStream(label);
            else iter = new CombinedNodeIterator(iter, getObjectOrEmptyStream(label));
        }
        for (Long idx: this.arraysPartitions.keySet()) {
            if (iter == null) iter = getArrayOrEmptyStream(idx);
            else iter = new CombinedNodeIterator(iter, getArrayOrEmptyStream(idx));
        }
        return iter;
    }

    public NodeIterator openObject(String objectLabel) {
        return getObjectOrEmptyStream(objectLabel);
    }

    public NodeIterator openArray(Long arrayIndex) {
        return getArrayOrEmptyStream(arrayIndex);
    }

    public NodeIterator openObject(List<String> objectLabels) {
        if (objectLabels.size() == 1) return getObjectOrEmptyStream(objectLabels.get(0));
        NodeIterator ret = new CombinedNodeIterator(
                getObjectOrEmptyStream(objectLabels.get(0)),
                getObjectOrEmptyStream(objectLabels.get(1))
        );
        for (int i = 2; i < objectLabels.size(); i++) {
            ret = new CombinedNodeIterator(
                    getObjectOrEmptyStream(objectLabels.get(i)),
                    ret
            );
        }
        return ret;
    }

    public NodeIterator openArray(ArrayIndexOperation operation) {
        if (operation.isSingleIndexOperation()) return getArrayOrEmptyStream(new Long(operation.indexes().get(0)));
        NodeIterator ret = new CombinedNodeIterator(
                getArrayOrEmptyStream(new Long(operation.indexes().get(0))),
                getArrayOrEmptyStream(new Long(operation.indexes().get(1)))
        );
        for (int i = 2; i < operation.indexes().size(); i++) {
            ret = new CombinedNodeIterator(
                    getArrayOrEmptyStream(new Long(operation.indexes().get(i))),
                    ret
            );
        }
        return ret;
    }

    public NodeIterator openArray(ArraySliceOperation operation) {
        NodeIterator ret = null;
        Long left = 0L;
        if (operation.from() != null) left = new Long(operation.from());
        for (Long idx: this.arraysPartitions.keySet()) {
            if (idx < left) continue;
            if (operation.to() != null && idx >= operation.to()) break;
            if (ret == null) ret = getArrayOrEmptyStream(idx);
            else {
                ret = new CombinedNodeIterator(
                        getArrayOrEmptyStream(idx),
                        ret
                );
            }
        }
        if (ret == null) return new SingleNodeIterator(new LinkedList<Node>());
        else return ret;
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
