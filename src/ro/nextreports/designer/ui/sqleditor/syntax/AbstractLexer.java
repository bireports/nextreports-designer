/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.nextreports.designer.ui.sqleditor.syntax;

/**
 * This is an abstract implemenatation of a Lexer with some 
 * utility methods that Lexers can implement.
 *
 * @author Decebal Suiu
 */
public abstract class AbstractLexer implements Lexer {

    /**
     * Return the current matched token as a string. This is <b>expensive</b>
     * as it creates a new String object for the token. Use with care.
     *
     * @return
     */
    protected CharSequence getTokenString() {
        return yytext();
    }
    
}
