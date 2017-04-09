package org.swiss.model;

import java.io.Serializable;
import java.util.UUID;

//@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
	// @Id
	// @GeneratedValue(generator = "system-uuid")
	// @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private final String id;

	public AbstractEntity() {
		this(UUID.randomUUID().toString());
	}

	public AbstractEntity(final String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
}
