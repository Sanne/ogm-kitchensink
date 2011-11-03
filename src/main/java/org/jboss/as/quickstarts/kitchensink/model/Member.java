package org.jboss.as.quickstarts.kitchensink.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@XmlRootElement
@Indexed
public class Member implements Serializable {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@NotNull
	@Size(min = 1, max = 50)
	@Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
	@Field
	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@XmlElement(name = "contacts")
	@Valid
	@IndexedEmbedded
	private List<ContactDetails> contactDetails;

	public Member() {
		contactDetails = new ArrayList<ContactDetails>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContactDetails> getContactDetails() {
		return contactDetails;
	}

	public void addContactDetails(ContactDetails newContactDetails) {
		contactDetails.add( newContactDetails );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}

		Member member = (Member) o;

		if ( name != null ? !name.equals( member.name ) : member.name != null ) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append( "Member" );
		sb.append( "{id='" ).append( id ).append( '\'' );
		sb.append( ", name='" ).append( name ).append( '\'' );
		sb.append( ", contactDetails=" ).append( contactDetails );
		sb.append( '}' );
		return sb.toString();
	}
}