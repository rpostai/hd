package com.rp.hd.repository.jpa.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.rp.hd.domain.BaseEntity;
import com.rp.hd.domain.utils.DateUtils;

public class RepositoryEntityListener {

	@PrePersist
	public void prePersist(BaseEntity entity) {
		entity.setDataCadastro(DateUtils.getDate());
	}
	
	@PreUpdate
	public void preUpdate(BaseEntity entity) {
		entity.setDataCadastro(DateUtils.getDate());
	}
}
