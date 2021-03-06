package org.jboss.as.quickstarts.kitchensink.controller;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.as.quickstarts.kitchensink.model.ContactDetails;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

@Stateful
// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an EL name
// Read more about the @Model stereotype in this FAQ: http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class MemberRegistration {

	@Inject
	@Category("jboss-as-kitchensink")
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Member> memberEventSrc;

	private Member newMember;

	private ContactDetails newContactDetails;

	@Produces
	@Named
	public ContactDetails getNewContactDetails() {
		return newContactDetails;
	}

	@Produces
	@Named
	public Member getNewMember() {
		return newMember;
	}

	public void register() throws Exception {
		log.info( "Registering " + newMember.getName() );
		newMember.addContactDetails( newContactDetails );
		em.persist( newMember );
		memberEventSrc.fire( newMember );
		initNewMember();
	}

	@PostConstruct
	public void initNewMember() {
		newMember = new Member();
		newContactDetails = new ContactDetails();
	}
}
