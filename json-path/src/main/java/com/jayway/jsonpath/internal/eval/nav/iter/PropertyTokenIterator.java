package com.jayway.jsonpath.internal.eval.nav.iter;

import com.jayway.jsonpath.internal.eval.nav.ResultIterator;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.index.node.ObjectNode;
import com.jayway.jsonpath.internal.path.PropertyPathToken;

public class PropertyTokenIterator implements ResultIterator {
    private ResultIterator parIter;
    private PropertyPathToken token;
    private IndexContext indexContext;

    private NodeIterator currentNodeIter;

    public PropertyTokenIterator(ResultIterator parIter, PropertyPathToken token, IndexContext indexContext) {
        this.parIter = parIter;
        this.token = token;
        this.indexContext = indexContext;
        this.initCurrentNodeIter();
    }

    private void initCurrentNodeIter() {
        this.currentNodeIter = indexContext.openObject(token.getProperties());
    }

    private boolean adjust() {
        while (true) {
            if (this.parIter.getNode().getFirstVisit() < this.currentNodeIter.peek().getFirstVisit()
                    && this.currentNodeIter.peek().getLastVisit() < this.parIter.getNode().getLastVisit()
                    && this.currentNodeIter.peek().getLevel() == this.parIter.getNode().getLevel() + 1) {
                return true;
            }
            if (this.parIter.hasNext()) this.parIter.next();
            else return false;
            if (this.parIter.getNode().getFirstVisit() > this.currentNodeIter.peek().getLastVisit()) return false;
        }
    }

    @Override
    public void next() {
        if (adjust()) {
            this.currentNodeIter.next();
        } else {
            this.parIter.next();
            this.initCurrentNodeIter();
        }
    }

    @Override
    public boolean hasNext() {
        while (!adjust()) {
            if (this.parIter.hasNext()) {
                this.parIter.next();
                this.initCurrentNodeIter();
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getPath() {
        while (!adjust()) {
            this.parIter.next();
            this.initCurrentNodeIter();
        }
        return this.parIter.getPath() + "." + ((ObjectNode)this.currentNodeIter.peek()).getNodeName();
    }

    @Override
    public Object getValue() {
        while (!adjust()) {
            this.parIter.next();
            this.initCurrentNodeIter();
        }
        return this.parIter.getPath() + "." + this.currentNodeIter.peek().getValue();
    }

    @Override
    public Node getNode() {
        while (!adjust()) {
            this.parIter.next();
            this.initCurrentNodeIter();
        }
        return this.currentNodeIter.peek();
    }
}
