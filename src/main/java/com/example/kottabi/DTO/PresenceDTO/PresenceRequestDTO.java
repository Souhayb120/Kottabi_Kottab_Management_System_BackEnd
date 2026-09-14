package com.example.kottabi.DTO.PresenceDTO;

import com.example.kottabi.enums.Statut;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresenceRequestDTO {

	@NotNull(message = "Date is required")
	private LocalDate date;

	@NotNull(message = "Statut is required")
	private Statut statut;

	@Min(value = 1, message = "Eleve ID is required")
	private long eleveId;
}
