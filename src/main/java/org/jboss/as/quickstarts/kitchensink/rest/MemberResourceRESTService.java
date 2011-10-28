package org.jboss.as.quickstarts.kitchensink.rest;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.util.QueryHelper;

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
		return createMatchAllFulltextQuery();
	}

//	public List<Member> retrieveAllMembersWithCriteria() {
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<Member> criteria = cb.createQuery( Member.class );
//		Root<Member> member = criteria.from( Member.class );
//		criteria.select( member ).orderBy( cb.asc( member.get( "name" ) ) );
//		return em.createQuery( criteria ).getResultList();
//	}

	@SuppressWarnings("unchecked")
	private List<Member> createMatchAllFulltextQuery() {
		return QueryHelper.createFulltextQuery( em ).getResultList();
	}

//	@GET
//	@Path("/{id:[0-9]+}")
//	@Produces("text/xml")
//	public Member lookupMemberById(@PathParam("id") long id) {
//		return em.find( Member.class, id );
//	}

	@GET
	@Path("/{id:[a-z-0-9]+}")
	@Produces("text/xml")
	public Member lookupMemberById(@PathParam("id") String id) {
		return em.find( Member.class, id );
	}
}
