package org.jboss.as.quickstarts.kitchensink.util;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.jboss.as.quickstarts.kitchensink.model.Member;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;

/**
 * @author Hardy Ferentschik
 */
public class QueryHelper {
	public static FullTextQuery createFulltextQuery(EntityManager em) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager( em );
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder()
				.forEntity( Member.class )
				.get();
		Query query = queryBuilder.all().createQuery();
		FullTextQuery fulltextQuery = fullTextEntityManager.createFullTextQuery( query );
		fulltextQuery.initializeObjectsWith( ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID );
		return fulltextQuery;
	}
}


