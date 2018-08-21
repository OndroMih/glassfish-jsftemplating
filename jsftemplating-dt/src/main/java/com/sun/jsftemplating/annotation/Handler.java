/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jsftemplating.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *  <p>	This is the <code>Handler Annotation</code>.  It is
 *	expected to exist on each
 *	{@link com.sun.jsftemplating.component.factory.ComponentFactory}.
 *	It must specify an identifier for the <code>ComponentFactory</code>
 *	so that it may be referenced when defining <code>UIComponents</code>
 *	in your template or code.</p>
 *
 *  @author Ken Paulsen (ken.paulsen@sun.com)
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Handler {
    public String id();
    public HandlerInput[] input() default {};
    public HandlerOutput[] output() default {};

    public static final String  ID =	    "id";
    public static final String  INPUT =	    "input";
    public static final String  OUTPUT =    "output";
}
