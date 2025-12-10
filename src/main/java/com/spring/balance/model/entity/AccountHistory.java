package com.spring.balance.model.entity;

import com.spring.balance.model.entity.consts.AccessStatus;
import com.spring.balance.model.entity.consts.AccessType;
import com.spring.balance.model.entity.embaddables.AccessHistoryPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class AccountHistory {

	@EmbeddedId
	private AccessHistoryPK id;
	
	@Column(nullable = false)
	private AccessType accessType;
	
	private AccessStatus status;
	
	private String remark;
}
