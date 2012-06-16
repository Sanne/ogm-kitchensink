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
package org.jboss.as.quickstarts.kitchensink.test;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.lucene.store.Directory;
import org.hibernate.search.engine.spi.SearchFactoryImplementor;
import org.hibernate.search.indexes.impl.DirectoryBasedIndexManager;
import org.hibernate.search.indexes.spi.IndexManager;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.store.DirectoryProvider;
import org.infinispan.lucene.InfinispanDirectory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * FullTextOnInfinispan.
 */
@RunWith(Arquillian.class)
public class FullTextOnInfinispanRegistrationTest extends MemberRegistrationTest {

   @Deployment
   public static Archive<?> createTestArchive() {
      return MemberRegistrationTest.createTestArchive("persistence-fulltext-infinispan.xml", "beans-alternativeQueries.xml", "fulltext-infinispan.war");
   }

   @Inject
   private FullTextEntityManager em;

   @Test
   public void testQueryRunViaSearch() throws Exception {
      SearchFactoryImplementor sfi = (SearchFactoryImplementor)em.getSearchFactory();
      IndexManager indexManager = sfi.getIndexBindingForEntity(Member.class).getIndexManagers()[0];
      DirectoryBasedIndexManager membersIndexManager = (DirectoryBasedIndexManager) indexManager;
      DirectoryProvider directoryProvider = membersIndexManager.getDirectoryProvider();
      Directory directory = directoryProvider.getDirectory();
      assertTrue(directory instanceof InfinispanDirectory);
   }

}
