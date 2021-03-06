/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.db.domain.connection;

/**
 * Notifies that a connection can be released
 */
public interface DbConnectionReleaser
{

    /**
     * Releases a given connection
     *
     * @param connection connection to release
     */
    void release(DbConnection connection);
}
