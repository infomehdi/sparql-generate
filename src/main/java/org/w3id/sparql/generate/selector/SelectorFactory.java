/*
 * Copyright 2016 ITEA 12004 SEAS Project.
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
package org.w3id.sparql.generate.selector;

import org.apache.jena.sparql.sse.builders.ExprBuildException;

/**
 *  Interface for function factories. 
 */ 
public interface SelectorFactory
{
    /**
     * Create a selector with the given URI
     * @param uri URI
     * @return Selector
     * @throws ExprBuildException May be thrown if there is a problem creating a selector
     */
    public Selector create(String uri) ;
}
