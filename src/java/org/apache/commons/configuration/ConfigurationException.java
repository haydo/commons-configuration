/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.configuration;

import org.apache.commons.lang.exception.NestableException;

/**
 * Any exception that occurs while initializing a Configuration
 * object.
 *
 * @author Eric Pugh
 * @version $Revision$, $Date$
 */
public class ConfigurationException extends NestableException
{
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -1316746661346991484L;

    /**
     * Constructs a new <code>ConfigurationException</code> without specified
     * detail message.
     */
    public ConfigurationException()
    {
        super();
    }

    /**
     * Constructs a new <code>ConfigurationException</code> with specified
     * detail message.
     *
     * @param message  the error message
     */
    public ConfigurationException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new <code>ConfigurationException</code> with specified
     * nested <code>Throwable</code>.
     *
     * @param cause  the exception or error that caused this exception to be thrown
     */
    public ConfigurationException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new <code>ConfigurationException</code> with specified
     * detail message and nested <code>Throwable</code>.
     *
     * @param message  the error message
     * @param cause    the exception or error that caused this exception to be thrown
     */
    public ConfigurationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
