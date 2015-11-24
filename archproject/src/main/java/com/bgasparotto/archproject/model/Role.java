package com.bgasparotto.archproject.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.bgasparotto.archproject.model.identity.AbstractEntity;

/**
 * Entity that represents a {@code Role} of the system.
 * 
 * @author Bruno Gasparotto
 *
 */
@Entity
@Table(name = "role", schema = "security")
@AttributeOverride(	name = "id",
					column = @Column(	name = "id_role",
										columnDefinition = "serial") )
public class Role extends AbstractEntity {

	@Size(	min = 3,
			max = 16,
			message = "Name's lenght must be between {min} and {max}")
	private String name;

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * 
	 * <p>
	 * Initializes a object using default values for its attributes, and
	 * {@code null} as its {@code id}.
	 * </p>
	 * 
	 * @deprecated Not for public use. This default constructor is meant to be
	 *             used only by frameworks.
	 */
	@Deprecated
	public Role() {
		this(null, "");
	}

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * 
	 * <p>
	 * Initializes a object populating its attributes using the given
	 * parameters.
	 * </p>
	 * 
	 * @param id
	 *            The roles's {@code id}
	 * @param name
	 *            The roles's {@code name}
	 */
	public Role(Long id, String name) {
		super(id);
		this.name = name;
	}

	/**
	 * Get the Role's {@code name}.
	 *
	 * @return Role's {@code name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the Role's {@code name}.
	 *
	 * @param name
	 *            The {@code name} to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}