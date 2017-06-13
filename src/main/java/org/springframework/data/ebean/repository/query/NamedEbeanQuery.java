/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.ebean.repository.query;

import io.ebean.EbeanServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.ebean.annotations.Query;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;

import javax.persistence.PersistenceException;

/**
 * {@link RepositoryQuery} implementation that inspects a {@link QueryMethod}
 * for the existence of an {@link Query} annotation and creates a Ebean native
 * {@link io.ebean.Query} from it.
 *
 * @author Xuegui Yuan
 */
final class NamedEbeanQuery extends AbstractEbeanQuery {

    private static final Logger LOG = LoggerFactory.getLogger(NamedEbeanQuery.class);

    private final String queryName;
    private io.ebean.Query query;

    /**
     * Creates a new {@link NamedEbeanQuery}.
     */
    private NamedEbeanQuery(EbeanQueryMethod method, EbeanServer ebeanServer, io.ebean.Query query) {
        super(method, ebeanServer);

        this.queryName = method.getNamedQueryName();
        this.query = query;
    }

    /**
     * Looks up a named query for the given {@link org.springframework.data.repository.query.QueryMethod}.
     *
     * @param method
     * @return
     */
    public static RepositoryQuery lookupFrom(EbeanQueryMethod method, EbeanServer ebeanServer) {
        final String queryName = method.getNamedQueryName();

        LOG.debug("Looking up named query {}", queryName);

        try {
            io.ebean.Query query = ebeanServer.createNamedQuery(method.getEntityInformation().getJavaType(), queryName);
            return new NamedEbeanQuery(method, ebeanServer, query);
        } catch (PersistenceException e) {
            LOG.debug("Did not find named query {}", queryName);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.ebean.repository.query.AbstractJpaQuery#doCreateQuery(java.lang.Object[])
     */
    @Override
    protected Object doCreateQuery(Object[] values) {
        return createBinder(values).bindAndPrepare(query);
    }
}