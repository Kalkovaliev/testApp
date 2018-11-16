package com.pelagusit.store.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.pelagusit.store.domain.DefaultModel;
import com.pelagusit.store.repository.JpaSpecificationRepository;
import com.pelagusit.store.service.DefaultModelCrudService;

public abstract class DefaultModelCrudServiceImpl <T extends DefaultModel, R extends JpaSpecificationRepository<T>>
implements DefaultModelCrudService<T> {
	
	protected abstract R getRepository();

	@Override
	public void save(T entity) {
		getRepository().save(entity);
	}

	@Override
	public Collection<T> findAll() {
		return getRepository().findAll();
	}

	@Override
	public List<T> save(Iterable<T> entities) {
		return getRepository().save(entities);
	}

	@Override
	public T saveAndFlush(T entity) {
		return getRepository().saveAndFlush(entity);
	}

	@Override
	public T findById(Long id) {
		return getRepository().findOne(id);
	}

	@Override
	public void delete(Long id) {
		getRepository().delete(id);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	@Override
	public List<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	@Override
	public List<T> findAll(Iterable<Long> ids) {
		return getRepository().findAll(ids);
	}

	@Override
	public List<T> findAll(Specification<T> spec) {
		return getRepository().findAll(spec);
	}

	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return getRepository().findAll(spec, pageable);
	}

	@Override
	public List<T> findAll(Specification<T> spec, Sort sort) {
		return getRepository().findAll(spec, sort);
	}

	@Override
	public long count() {
		return getRepository().count();
	}

	@Override
	public long count(Specification<T> spec) {
		return getRepository().count(spec);
	}

	@Override
	public T findOne(Specification<T> spec) {
		return getRepository().findOne(spec);
	}

	@Override
	public boolean exists(Long id) {
		return getRepository().exists(id);
	}

	@Override
	public void delete(T entity) {
		getRepository().delete(entity);
	}

	@Override
	public void delete(Iterable<T> entities) {
		getRepository().delete(entities);
	}

	@Override
	public void deleteAll() {
		getRepository().deleteAll();
	}

}
