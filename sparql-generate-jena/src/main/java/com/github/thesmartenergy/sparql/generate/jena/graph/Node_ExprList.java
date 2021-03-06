/*
 * Copyright 2017 Ecole des Mines de Saint-Etienne.
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
package com.github.thesmartenergy.sparql.generate.jena.graph;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.graph.Node;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.nodevalue.NodeValueString;

/**
 * Abstract class for expression nodes where the expression depends on a list of 
 * expressions.
 * 
 * @author maxime.lefrancois
 */
public abstract class Node_ExprList extends Node_Extended {
    
    /**
     * Some list of expressions
     */
    protected final List<Expr> components;

    /**
     * Constructor
     * 
     * @param label
     * @param components 
     */
    public Node_ExprList(String label, List<Expr> components) {
        super(label);
        this.components = components;
    }   
    
    /**
     * Get the list of expressions
     * 
     * @return 
     */
    public List<Expr> getComponents() {
        return components;
    }

    /**
     * Abstract builder.
     */
    public static abstract class Builder {

        protected List<Expr> components = new ArrayList<>();
        
        public abstract Node build();
                
        public void add(String s){
            if(!s.isEmpty()) {
                components.add(new NodeValueString(s));
            }
        }
                
        public void add(Expr e){
            components.add(e);
        }

    }

}
