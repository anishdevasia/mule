/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.db.domain.database;

import org.mule.common.Testable;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.module.db.domain.connection.DbConnectionFactory;

import javax.sql.DataSource;

/**
 * Database configuration used in the connector
 */
public interface DbConfig extends Testable, ConnectorMetaDataEnabled
{

    /**
     * @return configuration name
     */
    String getName();

    /**
     * @return a non null {@link javax.sql.DataSource} to access the database
     */
    DataSource getDataSource();

    /**
     * @return a non null {@link DbConnectionFactory} used to create connections for the database
     */
    DbConnectionFactory getConnectionFactory();
}
