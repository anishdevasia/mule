/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.db.result.row;

import org.mule.util.CaseInsensitiveHashMap;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Maps a row using returning a case insensitive map
 */
public class InsensitiveMapRowHandler implements RowHandler
{

    @Override
    public Map<String, Object> process(ResultSet resultSet) throws SQLException
    {
        CaseInsensitiveHashMap result = new CaseInsensitiveHashMap();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols = metaData.getColumnCount();

        for (int i = 1; i <= cols; i++)
        {
            String columnName = metaData.getColumnName(i);
            Object value = resultSet.getObject(i);
            result.put(columnName, value);
        }

        return result;
    }
}
