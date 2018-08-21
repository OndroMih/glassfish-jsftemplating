/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.jsftemplating.layout.template;

import com.sun.jsftemplating.layout.ProcessingCompleteException;
import com.sun.jsftemplating.layout.SyntaxException;
import com.sun.jsftemplating.layout.descriptors.LayoutDefine;
import com.sun.jsftemplating.layout.descriptors.LayoutDefinition;
import com.sun.jsftemplating.layout.descriptors.LayoutElement;
import com.sun.jsftemplating.util.LayoutElementUtil;

import java.io.IOException;


/**
 *  <p> This {@link CustomParserCommand} handles "composition" statements.
 *	TBD...
 *  </p>
 *
 *  @author Ken Paulsen	(ken.paulsen@sun.com)
 */
public class DefineParserCommand implements CustomParserCommand {

    /**
     *	<p> This method processes a "custom" command.  These are commands that
     *	    start with a !.  When this method receives control, the
     *	    <code>name</code> (i.e. the token after the '!' character) has
     *	    already been read.  It is passed via the <code>name</code>
     *	    parameter.</p>
     *
     *	<p> The {@link ProcessingContext} and
     *	    {@link ProcessingContextEnvironment} are both available.</p>
     */
    public void process(ProcessingContext ctx, ProcessingContextEnvironment env, String name) throws IOException {
	// Get the reader and parser
	TemplateReader reader = env.getReader();
	TemplateParser parser = reader.getTemplateParser();

	// Skip any white space...
	parser.skipCommentsAndWhiteSpace(TemplateParser.SIMPLE_WHITE_SPACE);

	// Get the parent and define name
	LayoutElement parent = env.getParent();
	String id = (String) parser.getNVP(NAME_ATTRIBUTE, true).getValue();

	// Create new LayoutDefine
	LayoutDefine compElt = new LayoutDefine(parent, id);
	parent.addChildLayoutElement(compElt);

	// Skip any white space or extra junk...
	String theRest = parser.readUntil('>', true).trim();
	if (theRest.endsWith("/")) {
	    reader.popTag();  // Don't look for end tag
	} else {
	    // Process child LayoutElements (recurse)
	    reader.process(
		LAYOUT_DEFINE_CONTEXT, compElt,
		LayoutElementUtil.isLayoutComponentChild(compElt));
	}
    }


    /**
     *	<p> This is the {@link ProcessingContext} for
     *	    {@link LayoutDefine}s.</p>
     */
    protected static class LayoutDefineContext extends BaseProcessingContext {
    }

    /**
     *	<p> A String containing "template".  This is the attribute name of the
     *	    template file to use in the {@link LayoutDefine}.</p>
     */
    public static final String NAME_ATTRIBUTE	= "name";

    /**
     *	<p> The {@link ProcessingContext} to be used when processing children
     *	    of a {@link LayoutDefine}.  This {@link ProcessingContext} may
     *	    have special meaning for {@link LayoutDefine}s and other tags.</p>
     */
    public static final ProcessingContext LAYOUT_DEFINE_CONTEXT	=
	new LayoutDefineContext();
}
