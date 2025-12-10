package com.spring.balance.model.entity.embaddables;

import java.time.Instant;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AccessHistoryPK {

	private String username;
	private Instant accessAt;
}
