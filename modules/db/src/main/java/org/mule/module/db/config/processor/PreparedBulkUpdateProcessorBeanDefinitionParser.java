/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.db.config.processor;

import org.mule.module.db.config.domain.param.StaticQueryParamResolverFactoryBean;
import org.mule.module.db.domain.executor.BulkUpdateExecutorFactory;
import org.mule.module.db.domain.query.QueryType;
import org.mule.module.db.metadata.PreparedBulkUpdateMetadataProvider;
import org.mule.module.db.processor.PreparedBulkUpdateMessageProcessor;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class PreparedBulkUpdateProcessorBeanDefinitionParser extends AbstractSingleQueryProcessorDefinitionParser
{

    private final List<QueryType> validQueryTypes;

    public PreparedBulkUpdateProcessorBeanDefinitionParser(List<QueryType> validQueryTypes)
    {
        this.validQueryTypes = validQueryTypes;
    }

    @Override
    protected Class<?> getBeanClass(Element element)
    {
        return PreparedBulkUpdateMessageProcessor.class;
    }

    @Override
    protected void doParseElement(Element element, ParserContext context, BeanDefinitionBuilder builder)
    {
        //// We want any parsing to occur as a child of this tag so we need to make
        //// a new one that has this as it's owner/parent
        //ParserContext nestedCtx = new ParserContext(context.getReaderContext(), context.getDelegate(), builder.getBeanDefinition());
        //
        //parseConfig(element, builder);
        //
        //BeanDefinitionBuilder sqlParamResolverFactory = BeanDefinitionBuilder.genericBeanDefinition(StaticQueryParamResolverFactoryBean.class);
        //BeanDefinition sqlParamResolver = sqlParamResolverFactory.getBeanDefinition();
        //
        //queryBean = parameterizedQueryDefinitionParser.parseQuery(element, nestedCtx, sqlParamResolver, dbConfigResolverFactoryBeanDefinition);
        //
        //createQueryResolverBeanDefinition(sqlParamResolver);
        //
        //builder.addConstructorArgValue(queryResolverBean);
        //
        //parseSourceExpression(element, builder);
        //parseTargetExpression(element, builder);
        //parseExecutorFactory(element, builder);
        //parseTransactionalAction(element, builder);
        super.doParseElement(element, context, builder);
        builder.addConstructorArgValue(validQueryTypes);
        parseAutoGeneratedKeys(element, builder);
        parseMetadataProvider(element, builder);
    }

    @Override
    protected BeanDefinition getParamResolverBeanDefinition()
    {
        BeanDefinitionBuilder sqlParamResolverFactory = BeanDefinitionBuilder.genericBeanDefinition(StaticQueryParamResolverFactoryBean.class);
        return sqlParamResolverFactory.getBeanDefinition();
    }

    @Override
    protected Object createExecutorFactory(Element element)
    {
        BeanDefinitionBuilder executorFactoryBean = BeanDefinitionBuilder.genericBeanDefinition(BulkUpdateExecutorFactory.class);

        executorFactoryBean.addConstructorArgValue(parseStatementFactory(element));

        return executorFactoryBean.getBeanDefinition();
    }

    @Override
    protected Object getMetadataProvider()
    {
        BeanDefinitionBuilder metadataProviderBuilder = BeanDefinitionBuilder.genericBeanDefinition(PreparedBulkUpdateMetadataProvider.class);
        metadataProviderBuilder.addConstructorArgValue(dbConfigResolverFactoryBeanDefinition);
        metadataProviderBuilder.addConstructorArgValue(queryBean);
        metadataProviderBuilder.addConstructorArgValue(autoGeneratedKeyStrategy);

        return metadataProviderBuilder.getBeanDefinition();
    }
}