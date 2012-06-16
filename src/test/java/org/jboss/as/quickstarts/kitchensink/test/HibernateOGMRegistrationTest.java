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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.runner.RunWith;

/**
 * HibernateOGMRegistrationTest.
 * 
 */
@RunWith(Arquillian.class)
public class HibernateOGMRegistrationTest extends MemberRegistrationTest {

   @Deployment//(order=2, testable=true)
   public static Archive<?> createTestArchive() {
      return MemberRegistrationTest.createTestArchive("persistence-hibernateOGM.xml", "beans-alternativeQueries.xml", "hibernate-ogm.war");
   }

   /*
   @Deployment(order=1, testable=false)
   public static Archive<?> defineOGMModule() {
      MavenDependencyResolver resolver = getMavenResolver();
      return ShrinkWrap.create(WebArchive.class, "hibernate-ogm-module")
            .addAsResource("ogm-module.xml", "module.xml")
            .addAsLibraries(
                  resolver
                      .artifact("org.hibernate.ogm:hibernate-ogm-infinispan:4.0.0-SNAPSHOT")
                      .exclusions(dependencyExclusions)
                      .resolveAs(JavaArchive.class)
                  )
                  ;
   }
   */

}
