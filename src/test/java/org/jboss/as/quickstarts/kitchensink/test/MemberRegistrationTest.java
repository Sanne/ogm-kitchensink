/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.kitchensink.test;

import java.io.File;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.quickstarts.kitchensink.controller.MemberController;
import org.jboss.as.quickstarts.kitchensink.data.MemberListProducer;
import org.jboss.as.quickstarts.kitchensink.data.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.model.ContactDetails;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.rest.JaxRsActivator;
import org.jboss.as.quickstarts.kitchensink.rest.MemberResourceRESTService;
import org.jboss.as.quickstarts.kitchensink.service.MemberRegistration;
import org.jboss.as.quickstarts.kitchensink.util.QueryHelper;
import org.jboss.as.quickstarts.kitchensink.util.Resources;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class MemberRegistrationTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClasses(
                        MemberController.class,
                        MemberListProducer.class,
                        MemberRepository.class,
                        ContactDetails.class,
                        Member.class,
                        JaxRsActivator.class,
                        MemberResourceRESTService.class,
                        MemberRegistration.class,
                        QueryHelper.class,
                        Resources.class,
                        FacesContextStub.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("infinispan.xml", "infinispan.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"), "beans.xml")
                .addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml")
                .addAsLibraries(
                        DependencyResolvers.use( MavenDependencyResolver.class )
                                .artifact( "org.hibernate:hibernate-search-orm:4.1.1.Final" )
                                .exclusion( "org.hibernate:hibernate-entitymanager" )
                                .exclusion( "org.hibernate:hibernate-core" )
                                .exclusion( "org.hibernate:hibernate-search-analyzers" )
                                .exclusion( "org.hibernate.common:hibernate-commons-annotations" )
                                .exclusion( "org.jboss.logging:jboss-logging" )
                                .resolveAs( JavaArchive.class ) );
    }

    @Inject
    MemberController memberController;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
        FacesContextStub.setCurrentInstance(new FacesContextStub("test"));
        Member newMember = memberController.getNewMember();
        newMember.setName("Jane Doe");

        ContactDetails newContactDetails = memberController.getContactDetails();
        newContactDetails.setEmail("jane@mailinator.com");
        newContactDetails.setPhoneNumber("2125551234");

        memberController.register();

        assertNotNull(newMember.getId());
        log.info(newMember.getName() + " was persisted with id " + newMember.getId());
    }

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
