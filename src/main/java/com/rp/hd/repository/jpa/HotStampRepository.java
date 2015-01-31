package com.rp.hd.repository.jpa;

import javax.ejb.Stateless;

import com.rp.hd.domain.HotStamp;

@Stateless
public class HotStampRepository extends BaseRepository<HotStamp>{

	public HotStampRepository() {
		super(HotStamp.class);
	}

}
