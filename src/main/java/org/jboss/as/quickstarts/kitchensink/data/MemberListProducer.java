package org.jboss.as.quickstarts.kitchensink.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.infinispan.Cache;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.util.QueryHelper;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.ogm.jpa.impl.OgmEntityManagerFactory;
import org.hibernate.ogm.metadata.GridMetadataManager;
import org.hibernate.search.jpa.FullTextQuery;

@RequestScoped
public class MemberListProducer {
	@Inject
	private EntityManager em;

	private List<Member> members;
	private List<CacheEntry> cacheEntries;

	@Produces
	@Named
	public List<Member> getMembers() {
		return members;
	}

	@Produces
	@Named
	public List<CacheEntry> getCacheEntries() {
		return cacheEntries;
	}

	public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Member member) {
		retrieveAllMembersUsingHibernateSearch();
		members.add( member );
		checkInfinispanEntityCache();
	}

//	@PostConstruct
//	public void retrieveAllMembersWithCriteria() {
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<Member> criteria = cb.createQuery( Member.class );
//		Root<Member> member = criteria.from( Member.class );
//		criteria.select( member ).orderBy( cb.asc( member.get( "name" ) ) );
//		members = em.createQuery( criteria ).getResultList();
//	}

	@PostConstruct
	public void retrieveAllMembersUsingHibernateSearch() {
		FullTextQuery fullTextQuery = QueryHelper.createFulltextQuery( em );
		members = new ArrayList<Member>( fullTextQuery.getResultList() );
	}

	public void checkInfinispanEntityCache() {
		SessionFactory factory = ( (OgmEntityManagerFactory) em.getEntityManagerFactory() ).getSessionFactory();
		final SessionFactoryObserver observer = ( (SessionFactoryImplementor) factory ).getFactoryObserver();
		if ( observer == null ) {
			throw new RuntimeException( "Wrong OGM configuration: observer not set" );
		}
		Cache cache = ( (GridMetadataManager) observer ).getCacheContainer().getCache( "ENTITIES" );

		cacheEntries = new ArrayList<CacheEntry>();
		for ( Object o : cache.entrySet() ) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
			CacheEntry keyValue = new CacheEntry( entry.getKey().toString(), (Map<String, ?>) entry.getValue() );
			cacheEntries.add( keyValue );
		}
	}

	public static class CacheEntry {
		private String key;
		private String value;

		CacheEntry(String key, Map<String, ?> valueMap) {
			this.key = key;
			this.value = createStringValue( valueMap );
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		private String createStringValue(Map<String, ?> valueMap) {
			StringBuilder builder = new StringBuilder();
			builder.append( "Value{" );
			for ( Map.Entry<String, ?> valueEntry : valueMap.entrySet() ) {
				if ( !builder.toString().startsWith( "Value{" ) ) {
					builder.append( ",\n" );
				}
				builder.append( valueEntry.getKey() );
				builder.append( "=" );
				builder.append( valueEntry.getValue().toString() );
			}
			builder.append( "}" );
			return builder.toString();
		}
	}
}
