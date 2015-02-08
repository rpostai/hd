package com.rp.hd.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.rp.hd.repository.Repository;

public abstract class BaseRepository<T> implements Repository<T> {

	@PersistenceContext(unitName = "hdPU")
	protected EntityManager em;

	private Class<T> clazz;

	public BaseRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T salvar(T obj) {
		return em.merge(obj);
	}

	public void excluir(Long id) {
		T obj = get(id);
		em.remove(obj);
	}

	public T get(Long id) {
		return em.find(clazz, id);
	}

	public List<T> getTodos() {
		TypedQuery<T> tq = em.createQuery(
				"select obj from " + clazz.getSimpleName() + " obj", clazz);
		return tq.getResultList();
	}

}
