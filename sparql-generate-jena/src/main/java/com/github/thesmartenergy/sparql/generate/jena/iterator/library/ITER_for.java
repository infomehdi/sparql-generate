/*
 * Copyright 2016 Ecole des Mines de Saint-Etienne.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.thesmartenergy.sparql.generate.jena.iterator.library;

import com.github.thesmartenergy.sparql.generate.jena.SPARQLGenerate;
import java.util.ArrayList;
import com.github.thesmartenergy.sparql.generate.jena.iterator.IteratorFunctionBase3;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.nodevalue.NodeValueDecimal;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Iterator function
 * <a href="http://w3id.org/sparql-generate/iter/for">iter:for</a>
 * iterates over numeric values that start at the first argument, increment
 * by the second argument, and stops whenever it goes over the third argument.
 *
 * <ul>
 * <li>Param 1 is the initial value;</li>
 * <li>Param 2 is the increment;</li>
 * <li>Param 3 is the threshold that defines the condition for stopping;</li>
 * </ul>
 *
 * @author Antoilne Zimmermann <antoine.zimmermann at emse.fr>
 */
public class ITER_for extends IteratorFunctionBase3 {

    private static final Logger LOG = LoggerFactory.getLogger(ITER_for.class);
    public static final String URI = SPARQLGenerate.ITER + "for";

    public ITER_for() {
    }

    @Override
    public List<List<NodeValue>> exec(NodeValue start, NodeValue incr, NodeValue stop) {

        // the initial value
        BigDecimal value = start.getDecimal();

        // the size of the increment
        BigDecimal incrV = incr.getDecimal();
        
        // the increment must not be zero
        if (incrV.equals(BigDecimal.ZERO)) {
            LOG.debug("Increment value must be non zero.");
            throw new ExprEvalException("Increment value must be non zero.");
        }

        // the threshold above/under which it stops
        BigDecimal stopV = stop.getDecimal(); 
        
        // the list that will contain the final result
        List<NodeValue> nodeValues = new ArrayList<>();

        while ( stopV.subtract(value).multiply(incrV).compareTo(BigDecimal.ZERO)>0 ) {
            NodeValue nodeValue = new NodeValueDecimal(value);
            nodeValues.add(nodeValue);
            value = value.add(incrV);
        }
        return new ArrayList<>(Collections.singletonList(nodeValues));
    }

}
