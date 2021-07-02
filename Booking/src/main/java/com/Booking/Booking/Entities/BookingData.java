package com.Booking.Booking.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "UniqueTransporterAndLoad", columnNames = { "transporterId", "loadid" }) })
@NoArgsConstructor
@AllArgsConstructor
public @Data class BookingData {

	public enum Unit {
		PER_TON, PER_TRUCK
	}

	@Id
	private String bookingId;

	@NotBlank(message = "Transporter Id can not be null")
	private String transporterId;
	@NotBlank(message = "Load Id can not be null")
	private String loadId;
	@NotBlank(message = "PostLoadId Id can not be null")
	private String postLoadId;

	private Long rate;
	private Unit unitValue;

	@Column(name = "truckIds")
	@ElementCollection(targetClass = String.class)
	@NotEmpty
	private List<String> truckId;

	private Boolean cancel;
	private Boolean completed;
	private String bookingDate;
	private String completedDate;

}
