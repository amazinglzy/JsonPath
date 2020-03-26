package com.jayway.jsonpath.internal.eval.nav.iter;

import com.jayway.jsonpath.internal.eval.nav.ResultIterator;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.index.node.ObjectNode;
import com.jayway.jsonpath.internal.path.PathToken;
import com.jayway.jsonpath.internal.path.PropertyPathToken;

public abstract class TokenIterator implements ResultIterator {

    protected ResultIterator parIter;
    protected PathToken token;
    protected IndexContext indexContext;

    protected NodeIterator currentNodeIter;

    public TokenIterator(ResultIterator parIter, PathToken token, IndexContext indexContext) {
        this.parIter = parIter;
        this.token = token;
        this.indexContext = indexContext;
        this.initCurrentNodeIter();
    }

    protected abstract NodeIterator openCorrespondNodeIterator();

    protected void initCurrentNodeIter() {
        this.currentNodeIter = openCorrespondNodeIterator();
    }

    protected boolean adjust() {
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
        return this.parIter.getPath() + getPathFragment(this.currentNodeIter.peek());
    }

    protected abstract String getPathFragment(Node node);

    @Override
    public Object getValue() {
        while (!adjust()) {
            this.parIter.next();
            this.initCurrentNodeIter();
        }
        return this.currentNodeIter.peek().getValue();
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
