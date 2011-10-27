package org.jboss.as.quickstarts.kitchensink.rest;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.lucene.search.Query;
import org.jboss.as.quickstarts.kitchensink.model.Member;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;

/**
 * JAX-RS Example
 *
 * This class produces a RESTful service to read the contents of the members table.
 */
@Path("/members")
@RequestScoped
public class MemberResourceRESTService {
	@Inject
	private EntityManager em;

	@GET
	@Produces("text/xml")
	public List<Member> listAllMembers() {
		return retrieveAllMembersWithCriteria();
	}

	public List<Member> retrieveAllMembersWithCriteria() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery( Member.class );
		Root<Member> member = criteria.from( Member.class );
		criteria.select( member ).orderBy( cb.asc( member.get( "name" ) ) );
		return em.createQuery( criteria ).getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<Member> createMatchAllFulltextQuery() {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager( em );
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder()
				.forEntity( Member.class )
				.get();
		Query query = queryBuilder.all().createQuery();
		FullTextQuery fulltextQuery = fullTextEntityManager.createFullTextQuery( query );
		fulltextQuery.initializeObjectsWith( ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID );
		return fulltextQuery.getResultList();
	}

	@GET
	@Path("/{id:[0-9]+}")
	@Produces("text/xml")
	public Member lookupMemberById(@PathParam("id") long id) {
		return em.find( Member.class, id );
	}

//	@GET
//	@Path("/{id:[a-z-0-9]+}")
//	@Produces("text/xml")
//	public Member lookupMemberById(@PathParam("id") String id) {
//		return em.find( Member.class, id );
//	}
}
