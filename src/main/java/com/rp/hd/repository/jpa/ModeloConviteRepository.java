package com.rp.hd.repository.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.rp.hd.domain.ModeloConvite;
import com.rp.hd.domain.ModeloConviteFoto;

@Stateless
public class ModeloConviteRepository extends BaseRepository<ModeloConvite> {

	public ModeloConviteRepository() {
		super(ModeloConvite.class);
	}
	
	public List<ModeloConvite> getModelosComFotos() {
		return em.createNamedQuery("ModeloConvite.ModelosComFotos", ModeloConvite.class).getResultList();
	}
	
	public Optional<ModeloConvite> getModeloComFotos(Long modeloId) {
		TypedQuery<ModeloConvite> tq = em.createNamedQuery("ModeloConvite.ModeloComFotos", ModeloConvite.class);
		tq.setParameter("modelo", modeloId);
		try {
			return Optional.of(tq.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
	
	public List<ModeloConviteFoto> getTodasFotos() {
		List<ModeloConviteFoto> result = new ArrayList<ModeloConviteFoto>();
		
		em.createNamedQuery("ModeloConvite.ModelosComFotos", ModeloConvite.class).getResultList().stream().forEach(x-> {
			result.addAll(x.getFotos());
		});
		
		return result;
		
	}

}
