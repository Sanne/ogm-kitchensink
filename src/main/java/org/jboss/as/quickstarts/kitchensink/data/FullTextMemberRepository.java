/* 
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.as.quickstarts.kitchensink.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.jboss.as.quickstarts.kitchensink.model.Member;

@Stateless @Alternative
public class FullTextMemberRepository implements MemberRepository {

    @Inject
    private QueryBuilder queryBuilder;

    @PersistenceContext(unitName="kitchen")
    private EntityManager em;

    @Override
    public Member findById(String id) {
        return em.find(Member.class, id);
    }

    @Override
    public Member findByEmail(String email) {
        Query luceneQuery = queryBuilder
                .keyword()
                    .onField("contactDetails.email")
                .matching(email)
                .createQuery();
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        List resultList = fullTextEntityManager.createFullTextQuery(luceneQuery)
              .initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID)
              .getResultList();
        if (resultList.size()>0) {
            return (Member) resultList.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public List<Member> findAllOrderedByName() {
        Query luceneQuery = queryBuilder
                .all()
                .createQuery();
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        List resultList = fullTextEntityManager.createFullTextQuery(luceneQuery)
              .initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID)
              .setSort(new Sort(new SortField("sortableStoredName", SortField.STRING_VAL)))
              .getResultList();
        return resultList;
    }

}
