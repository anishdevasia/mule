/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.db.integration;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mule.module.db.integration.matcher.FieldMatcher.containsField;
import static org.mule.module.db.integration.model.Planet.EARTH;
import static org.mule.module.db.integration.model.Planet.MARS;
import static org.mule.module.db.integration.model.Planet.VENUS;
import org.mule.api.MuleMessage;
import org.mule.module.db.integration.model.Field;
import org.mule.module.db.integration.model.Record;

import java.util.List;
import java.util.Map;

public class TestRecordUtil
{

    public static Record[] getAllRecords()
    {
        return new Record[] {getVenusRecord(), getEarthRecord(), getMarsRecord()};
    }

    public static void assertMessageContains(MuleMessage message, Record... records)
    {
        assertRecords(message.getPayload(), records);
    }

    public static void assertRecords(Object value, Record... records)
    {
        assertTrue("Expected a list but received: " + ((value == null) ? "null" : value.getClass().getName()), value instanceof List);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) value;
        assertThat(resultList.size(), equalTo(records.length));

        for (int i = 0, recordsLength = records.length; i < recordsLength; i++)
        {
            Record actualRecord = new Record(resultList.get(i));
            Record expectedRecord = records[i];

            for (Field field : expectedRecord.getFields())
            {
                assertThat(actualRecord, containsField(field));
            }
        }
    }

    public static Record getMarsRecord()
    {
        return new Record(new Field("NAME", MARS.getName()));
    }

    public static Record getVenusRecord()
    {
        return new Record(new Field("NAME", VENUS.getName()));
    }

    public static Record getEarthRecord()
    {
        return new Record(new Field("NAME", EARTH.getName()));
    }
}
