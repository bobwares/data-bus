package com.bobwares.core.bean;

import com.bobwares.core.util.ApplicationContextUtils;
import com.bobwares.core.util.StringUtils;


import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class Registry<T> implements BeanNameAware, EnvironmentAware {

	protected List<T> collection;
	protected BeanFilter<T> beanFilter;
	protected String beanName;
	protected boolean propertyFiltered;
	protected Environment environment;

	public Registry() {
		this.collection = new ArrayList<>();
		beanFilter = new BeanFilter<>(this.collection);
	}

	public Registry(Collection<T> collection) {
		this.collection = new ArrayList<>(collection);
		beanFilter = new BeanFilter<>(this.collection);
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public Collection<T> getCollection() {
		if (!propertyFiltered) {
			applyPropertyFilters();
			propertyFiltered = true;
		}
		return collection;
	}

	public Registry<T> add(T item) {
		if (item != null) collection.add(item);
		return this;
	}

	public Registry<T> addFirst(T item) {
		return addAt(0, item);
	}

	public Registry<T> addAt(int index, T item) {
		if (item != null) {
			if (index >= 0 && index < collection.size()) collection.add(index, item);
			else if (index < 0) collection.add(0, item);
			else collection.add(item);
		}
		return this;
	}

	public Registry<T> add(Collection<T> items) {
		if (items != null) collection.addAll(items);
		return this;
	}

	public Registry<T> remove(T item) {
		if (item != null) collection.remove(item);
		return this;
	}

	public Registry<T> clear() {
		collection.clear();
		return this;
	}

	public Registry<T> include(Collection<Class<? extends T>> includeClasses) {
		beanFilter.include(includeClasses);
		return this;
	}

	public Registry<T> include(Class<? extends T> includeClass) {
		beanFilter.include(includeClass);
		return this;
	}

	public Registry<T> include(String name) {
		beanFilter.include(name);
		return this;
	}

	public Registry<T> include(T bean) {
		beanFilter.include(bean);
		return this;
	}

	public Registry<T> exclude(Collection<Class<? extends T>> excludeClasses) {
		beanFilter.exclude(excludeClasses);
		return this;
	}

	public Registry<T> exclude(Class<? extends T> excludeClass) {
		beanFilter.exclude(excludeClass);
		return this;
	}

	public Registry<T> exclude(String name) {
		beanFilter.exclude(name);
		return this;
	}

	public Registry<T> exclude(T bean) {
		beanFilter.exclude(bean);
		return this;
	}

	public Registry<T> filter() {
		beanFilter.filter();
		return this;
	}

	protected void applyPropertyFilters() {
		if (environment != null && beanName != null) {
			List<String> includeNames = StringUtils.parseDelimitedData(environment.getProperty(beanName + ".include"));
			if (includeNames != null) {
				for (String name : includeNames) {
					beanFilter.include(name);
				}
			}
			
			List<String> excludeNames = StringUtils.parseDelimitedData(environment.getProperty(beanName + ".exclude"));
			if (excludeNames != null) {
				for (String name : excludeNames) {
					beanFilter.exclude(name);
				}
			}
			
			beanFilter.filter();
		}
	}

    public static <E> Registry<E> getRegistry(String registryName, Class<E> beanType) {
    	Registry<E> registry = ApplicationContextUtils.<Registry<E>>getBean(registryName);
		return registry != null ? registry : new Registry<E>();
    }

    public static <E> Registry<E> getRegistry(String registryName) {
		Registry<E> registry = ApplicationContextUtils.<Registry<E>>getBean(registryName);
		return registry != null ? registry : new Registry<E>();
    }

    public static <E> Collection<E> getBeans(String registryName, Class<E> beanType) {
    	Registry<E> registry = ApplicationContextUtils.<Registry<E>>getBean(registryName);
		return registry != null ? registry.getCollection() : new ArrayList<E>();
    }

    public static <E> Collection<E> getBeans(String registryName) {
    	Registry<E> registry = ApplicationContextUtils.<Registry<E>>getBean(registryName);
		return registry != null ? registry.getCollection() : new ArrayList<E>();
    }

}
