package org.jboss.as.quickstarts.kitchensink.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class ContactDetails {
	@Id
	@GeneratedValue
//	@GeneratedValue(generator = "uuid")
//	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private long id;

	@NotNull
	@NotEmpty
	@Email
//	@Field
	private String email;

	@NotNull
	@Size(min = 10, max = 12)
	@Digits(fraction = 0, integer = 12)
//	@Field
	private String phoneNumber;

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}

		ContactDetails that = (ContactDetails) o;

		if ( email != null ? !email.equals( that.email ) : that.email != null ) {
			return false;
		}
		if ( phoneNumber != null ? !phoneNumber.equals( that.phoneNumber ) : that.phoneNumber != null ) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = email != null ? email.hashCode() : 0;
		result = 31 * result + ( phoneNumber != null ? phoneNumber.hashCode() : 0 );
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append( "ContactDetails" );
		sb.append( "{email='" ).append( email ).append( '\'' );
		sb.append( ", phoneNumber='" ).append( phoneNumber ).append( '\'' );
		sb.append( '}' );
		return sb.toString();
	}
}


