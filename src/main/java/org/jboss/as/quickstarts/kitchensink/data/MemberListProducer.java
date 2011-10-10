package org.jboss.as.quickstarts.kitchensink.data;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.jboss.as.quickstarts.kitchensink.model.Member;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;

@RequestScoped
public class MemberListProducer {
	@Inject
	private EntityManager em;

	private List<Member> members;

	// @Named provides access the return value via the EL variable name "members" in the UI (e.g. Facelets or JSP view)
	@Produces
	@Named
	public List<Member> getMembers() {
		return members;
	}

	public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Member member) {
		retrieveAllMembersOrderedByName();
	}

	@PostConstruct
	public void retrieveAllMembersOrderedByName() {
//      CriteriaBuilder cb = em.getCriteriaBuilder();
//      CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
//      Root<Member> member = criteria.from(Member.class);
//      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
//      // feature in JPA 2.0
//      // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
//      criteria.select(member).orderBy(cb.asc(member.get("name")));
		FullTextQuery fullTextQuery = createMatchAllFulltextQuery();
		members = fullTextQuery.getResultList();
	}

	private FullTextQuery createMatchAllFulltextQuery() {
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
