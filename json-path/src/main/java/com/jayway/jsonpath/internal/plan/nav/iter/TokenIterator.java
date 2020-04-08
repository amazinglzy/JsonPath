package com.jayway.jsonpath.internal.plan.nav.iter;

import com.jayway.jsonpath.internal.plan.nav.ResultIterator;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.path.PathToken;

public abstract class TokenIterator implements ResultIterator {

    protected ResultIterator parIter;
    protected PathToken token;
    protected IndexContext indexContext;
    protected boolean isScan;

    protected NodeIterator currentNodeIter;

    public TokenIterator(ResultIterator parIter, PathToken token, IndexContext indexContext) {
        this.parIter = parIter;
        this.token = token;
        this.indexContext = indexContext;
        this.isScan = false;
        this.initCurrentNodeIter();
    }

    public TokenIterator(ResultIterator parIter, PathToken token, IndexContext indexContext, boolean isScan) {
        this.parIter = parIter;
        this.token = token;
        this.indexContext = indexContext;
        this.isScan = isScan;
        this.initCurrentNodeIter();
    }

    protected abstract NodeIterator openCorrespondNodeIterator();

    protected void initCurrentNodeIter() {
        this.currentNodeIter = openCorrespondNodeIterator();
    }

    protected boolean adjust() {
        while (true) {
            if (!this.parIter.hasNext()) return false;
            if (!this.currentNodeIter.hasNext() || this.parIter.getNode().getLastVisit() < this.currentNodeIter.read().getFirstVisit()) {
                this.parIter.next();
                if (!this.parIter.hasNext()) return false;
                this.initCurrentNodeIter();
            }

            while (this.currentNodeIter.hasNext()) {
                if (this.parIter.getNode().getFirstVisit() < this.currentNodeIter.read().getFirstVisit()
                        && this.currentNodeIter.read().getLastVisit() < this.parIter.getNode().getLastVisit()) {
                    if (this.isScan || (this.parIter.getNode().getLevel() + 1 == this.currentNodeIter.read().getLevel()))
                        return true;
                    else
                        this.currentNodeIter.next();
                } else if (this.parIter.getNode().getLastVisit() < this.currentNodeIter.read().getFirstVisit()) {
                    break;
                } else {
                    this.currentNodeIter.next();
                }
            }
        }
    }

    @Override
    public void next() {
        adjust();
        this.currentNodeIter.next();
    }

    @Override
    public boolean hasNext() {
        return adjust();
    }

    @Override
    public String getPath() {
        adjust();
        return this.parIter.getPath() + getPathFragment(this.currentNodeIter.read());
    }

    protected abstract String getPathFragment(Node node);

    @Override
    public Object getValue() {
        adjust();
        return this.currentNodeIter.read().getValue();
    }

    @Override
    public Node getNode() {
        adjust();
        return this.currentNodeIter.read();
    }
}
