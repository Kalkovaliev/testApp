package com.pelagusit.store.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.pelagusit.store.domain.DefaultModel;


public interface DefaultModelCrudService  <T extends DefaultModel>{
	
	public void save(T entity);

    public List<T> save(Iterable<T> entities);

    public Collection<T> findAll();

    public T saveAndFlush(T entity);

    public T findById(Long id);

    public Page<T> findAll(Pageable pageable);

    public List<T> findAll(Sort sort);

    public List<T> findAll(Iterable<Long> ids);

    public List<T> findAll(Specification<T> spec);

    public Page<T> findAll(Specification<T> spec, Pageable pageable);

    public List<T> findAll(Specification<T> spec, Sort sort);

    public long count();

    public long count(Specification<T> spec);

    public T findOne(Specification<T> spec);

    public boolean exists(Long id);

    public void delete(Long id);

    public void delete(T entity);

    public void delete(Iterable<T> entities);

    public void deleteAll();

}
