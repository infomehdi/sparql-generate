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

import java.util.List;
import java.util.UUID;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.NodeVisitor;
import org.apache.jena.graph.Node_URI;
import org.apache.jena.graph.impl.LiteralLabelFactory;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.nodevalue.NodeValueString;

/**
 * The class of expression nodes of type {@code "text{<expr>}" }, or any other
 * RDF literal with embedded expressions (including RDF literals with language
 * tags, RDF datatypes. 
 * The RDF datatype itself can be an expression node of type {@code <uri{<expr>}>}
 * 
 * @author maxime.lefrancois
 */
public class Node_XLiteral extends Node_XExprList {

    private final String lang;

    private final Node datatype;

    /**
     * 
     * @param components list of NodeValueString or Expressions
     * @param lang optional language tag
     * @param datatype  optional URI or Node_XURI
     */
    private Node_XLiteral(List<Expr> components, String lang, Node datatype) {
        super(UUID.randomUUID().toString().substring(0, 8), components);
        this.lang = lang;
        this.datatype = datatype;
    }

    /**
     * Builder for immutable Node_XLiteral
     */
    public static class Builder extends Node_XExprList.Builder {

        private String lang = null;

        private Node datatype = null;

        public void setLang(String lang) {
            this.lang = lang;
        }

        public void setDatatype(Node datatype) {
            this.datatype = datatype;
        }

        public Node build() {
            if (datatype == null) {
                if (components.isEmpty()) {
                    return NodeFactory.createLiteral("", lang);
                } else if (components.size() == 1 && components.get(0) instanceof NodeValueString) {
                    String lex = ((NodeValueString) components.get(0)).getString();
                    return NodeFactory.createLiteral(lex, lang);
                }
            } else if (datatype instanceof Node_URI) {
                String dturi = datatype.getURI();
                RDFDatatype dt = TypeMapper.getInstance().getTypeByName(dturi);
                if (components.isEmpty()) {
                    return NodeFactory.createLiteral("", dt);
                } else if (components.size() == 1 && components.get(0) instanceof NodeValueString) {
                    String lex = ((NodeValueString) components.get(0)).getString();
                    return NodeFactory.createLiteral(lex, dt);
                }
            }
            return new Node_XLiteral(components, lang, datatype);
        }
    }

    public String getLang() {
        return lang;
    }

    public Node getDatatype() {
        return datatype;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object visitWith(NodeVisitor v) {
        if (v instanceof SPARQLGenerateNodeVisitor) {
            ((SPARQLGenerateNodeVisitor) v).visit(this);
        }
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node_XLiteral)) {
            return false;
        }
        Node_XLiteral on = (Node_XLiteral) o;
        if (components.size() != on.components.size()) {
            return false;
        }
        for (int i = 0; i < components.size(); i++) {
            if (!components.get(i).equals(on.components.get(i))) {
                return false;
            }
        }
        return true;
    }

}
