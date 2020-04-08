package com.jayway.jsonpath.internal.plan.travel;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.EvaluationAbortException;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.plan.ReadEvaluator;
import com.jayway.jsonpath.internal.plan.WriteEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreeTravelEvaluator implements ReadEvaluator, WriteEvaluator {
    private static final Logger logger = LoggerFactory.getLogger(TreeTravelEvaluator.class);
    private Path path;

    public TreeTravelEvaluator(Path path) {
        this.path = path;
    }

    /**
     * Evaluates this path
     *
     * @param document the json document to apply the path on
     * @param rootDocument the root json document that started this evaluation
     * @param configuration configuration to use
     * @param forUpdate is this a read or a write operation
     * @return EvaluationContext containing results of evaluation
     */
    public EvaluationContext evaluate(Object document, Object rootDocument, Configuration configuration, boolean forUpdate) {
        if (logger.isDebugEnabled()) {
            logger.debug("Evaluating path: {}", this.path.toString());
        }
        EvaluationContextImpl ctx = new EvaluationContextImpl(this.path, rootDocument, configuration, forUpdate);
        try {
            PathRef op = ctx.forUpdate() ?  PathRef.createRoot(rootDocument) : PathRef.NO_OP;
            PathTokenEvaluatorFactory.create(this.path.getRootToken()).evaluate("", op, document, ctx);
        } catch (EvaluationAbortException abort){}

        return ctx;
    }

    /**
     * Evaluates this path
     *
     * @param document the json document to apply the path on
     * @param rootDocument the root json document that started this evaluation
     * @param configuration configuration to use
     * @return EvaluationContext containing results of evaluation
     */
    public EvaluationContext evaluate(Object document, Object rootDocument, Configuration configuration) {
        return this.evaluate(document, rootDocument, configuration, false);
    }
}
