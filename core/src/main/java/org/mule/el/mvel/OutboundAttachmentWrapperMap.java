/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.el.mvel;

import org.mule.api.MuleMessage;
import org.mule.api.MuleRuntimeException;
import org.mule.el.AbstractExpressionLanguageMap;

import java.util.Set;

import javax.activation.DataHandler;

class OutboundAttachmentWrapperMap extends AbstractExpressionLanguageMap<String, DataHandler>
{
    private MuleMessage message;

    public OutboundAttachmentWrapperMap(MuleMessage message)
    {
        this.message = message;
    }

    @Override
    public void clear()
    {
        for (String name : message.getOutboundAttachmentNames())
        {
            try
            {
                message.removeOutboundAttachment(name);
            }
            catch (Exception e)
            {
                throw new MuleRuntimeException(e);
            }
        }
    }

    @Override
    public boolean containsKey(Object key)
    {
        return message.getOutboundAttachmentNames().contains(key);
    }

    @Override
    public DataHandler get(Object key)
    {
        if (!(key instanceof String))
        {
            return null;
        }

        return message.getOutboundAttachment((String) key);
    }

    @Override
    public Set<String> keySet()
    {
        return message.getOutboundPropertyNames();
    }

    @Override
    public DataHandler put(String key, DataHandler value)
    {
        DataHandler previousValue = get(key);
        try
        {
            message.addOutboundAttachment(key, (DataHandler) value);
        }
        catch (Exception e)
        {
            throw new MuleRuntimeException(e);
        }
        return previousValue;
    }

    @Override
    public DataHandler remove(Object key)
    {
        if (!(key instanceof String))
        {
            return null;
        }

        DataHandler previousValue = get(key);
        try
        {
            message.removeOutboundAttachment((String) key);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return previousValue;
    }

}