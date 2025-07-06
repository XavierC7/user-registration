package com.assessment.registration.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String number;

    private String citycode;

    private String contrycode;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
	
}
