package com.assessment.registration.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PhoneDTO {
	
	@NotBlank(message = "Phone number is required")
    private String number;

    @NotBlank(message = "City code is required")
    private String cityCode;

    @NotBlank(message = "Country code is required")
    private String countryCode;

}
