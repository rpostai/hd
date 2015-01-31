package com.rp.hd.services;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import com.rp.hd.domain.BaseEntity;
import com.rp.hd.domain.exceptions.BaseException;
import com.rp.hd.domain.utils.DateUtils;
import com.rp.hd.repository.Repository;
import com.rp.hd.repository.jpa.BaseRepository;
import com.rp.hd.repository.jpa.listener.RepositoryEntityListener;

@RunWith(Arquillian.class)
@CleanupUsingScript("clean.sql")
public abstract class AbstractServiceTest {
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class)
				.addPackage(BaseEntity.class.getPackage())

				.addPackage(RepositoryEntityListener.class.getPackage())
				.addPackage(Repository.class.getPackage())
				.addPackage(BaseRepository.class.getPackage())

				.addPackage(RepositoryEntityListener.class.getPackage())

				.addPackage(GeradorHash.class.getPackage())
				.addPackage(BaseException.class.getPackage())
				.addPackage(DateUtils.class.getPackage())
				.addPackage(CalculadoraService.class.getPackage())
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("performance-ds.xml");
	}
}
