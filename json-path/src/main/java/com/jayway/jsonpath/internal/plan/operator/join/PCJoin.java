package com.jayway.jsonpath.internal.plan.operator.join;

import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.plan.operator.PlanOperator;

import java.util.LinkedList;
import java.util.List;

public class PCJoin implements PlanOperator {
    private PlanOperator parentPlanOperator;
    private PlanOperator childPlanOperator;

    public PCJoin(PlanOperator parentPlanOperator, PlanOperator childPlanOperator) {
        this.parentPlanOperator = parentPlanOperator;
        this.childPlanOperator = childPlanOperator;
    }

    @Override
    public NodeIterator iterator() {
        return new PCJoinIter(this.parentPlanOperator.iterator(),
                this.childPlanOperator.iterator());
    }

    static class PCJoinIter implements NodeIterator {
        private NodeIterator pIter;
        private NodeIterator cIter;
        private List<Node> buffer;

        public PCJoinIter(NodeIterator pIter, NodeIterator cIter) {
            this.pIter = pIter;
            this.cIter = cIter;

            this.buffer = new LinkedList<Node>();
        }

        private PCJoinIter(NodeIterator pIter, NodeIterator cIter, List<Node> buffer) {
            this.pIter = pIter;
            this.cIter = cIter;
            this.buffer = buffer;
        }

        private void readBuffer() {
            if (this.buffer.size() > 0) return;

            while (this.pIter.hasNext() && this.buffer.size() == 0) {
                while (cIter.hasNext() && cIter.read().getLastVisit() < pIter.read().getFirstVisit())
                    cIter.next();
                NodeIterator curIter = cIter.cloneCurrentIterator();
                while (curIter.hasNext() && curIter.read().getFirstVisit() < pIter.read().getFirstVisit())
                    curIter.next();
                while (curIter.hasNext() && curIter.read().getLastVisit() < pIter.read().getLastVisit()) {
                    if (curIter.read().getLevel() == pIter.read().getLevel() + 1)
                        this.buffer.add(curIter.read());
                    curIter.next();
                }
                this.pIter.next();
            }
        }

        private boolean checkEmpty(boolean throwable) {
            if (this.buffer.size() == 0)
                readBuffer();
            if (this.buffer.size() == 0) {
                if (throwable) throw new IllegalArgumentException();
                return false;
            }
            return true;
        }

        @Override
        public Node read() {
            this.checkEmpty(true);
            return this.buffer.get(0);
        }

        @Override
        public void next() {
            this.checkEmpty(true);
            this.buffer.remove(0);
        }

        @Override
        public boolean hasNext() {
            return checkEmpty(false);
        }

        @Override
        public NodeIterator cloneCurrentIterator() {
            List<Node> buffer = new LinkedList<Node>(this.buffer);
            return new PCJoinIter(this.pIter.cloneCurrentIterator(), this.cIter.cloneCurrentIterator(), buffer);
        }
    }
}
