package com.bobwares.core.bean;

import com.bobwares.core.util.ApplicationContextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


public class BeanFilter<T> {

	Collection<T> beans;
	boolean modifyBeanCollection = true;
	boolean needToApplyFilter;

	Collection<Class<? extends T>> includedClasses = new HashSet<>();
	Collection<T> includedBeans = new HashSet<>();

	Collection<Class<? extends T>> excludedClasses = new HashSet<>();
	Collection<T> excludedBeans = new HashSet<>();


	public static <T> BeanFilter<T> beans(Collection<T> beans) {
		return new BeanFilter<>(beans);
	}

	public BeanFilter(Collection<T> beans) {
		this.beans = beans;
	}

	public BeanFilter<T> include(Collection<Class<? extends T>> includeClasses) {
		if (includeClasses != null && !includeClasses.isEmpty()) {
			this.includedClasses.addAll(includeClasses);
			needToApplyFilter = true;
		}
		return this;
	}

	public BeanFilter<T> include(Class<? extends T> includeClass) {
		if (includeClass != null) {
			this.includedClasses.add(includeClass);
			needToApplyFilter = true;
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public BeanFilter<T> include(String name) {
		if (name != null) {
			include((T) ApplicationContextUtils.getBean(name));
		}
		return this;
	}

	public BeanFilter<T> include(T bean) {
		if (bean != null) {
			includedBeans.add(bean);
			needToApplyFilter = true;
		}
		return this;
	}

	public BeanFilter<T> exclude(Collection<Class<? extends T>> excludeClasses) {
		if (excludeClasses != null && !excludeClasses.isEmpty()) {
			this.excludedClasses.addAll(excludeClasses);
			needToApplyFilter = true;
		}
		return this;
	}

	public BeanFilter<T> exclude(Class<? extends T> excludeClass) {
		if (excludeClass != null) {
			this.excludedClasses.add(excludeClass);
			needToApplyFilter = true;
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public BeanFilter<T> exclude(String name) {
		if (name != null) {
			exclude((T)ApplicationContextUtils.getBean(name));
		}
		return this;
	}

	public BeanFilter<T> exclude(T bean) {
		if (bean != null) {
			excludedBeans.add(bean);
			needToApplyFilter = true;
		}
		return this;
	}

	public BeanFilter<T> modifyBeanCollection(boolean modifyBeanCollection) {
		this.modifyBeanCollection = modifyBeanCollection;
		return this;
	}

	public Collection<T> filter() {
		if (!needToApplyFilter) return beans;

		Collection<T> filteredBeans = new ArrayList<>();
		for (T bean : beans) {
			if (!isExcluded(bean) && isIncluded(bean)) filteredBeans.add(bean);
		}
		needToApplyFilter = false;

		if (modifyBeanCollection) {
			beans.clear();
			beans.addAll(filteredBeans);
			return beans;
		}

		return filteredBeans;
	}

	private boolean isExcluded(T bean) {
		boolean isExcluded = false;

		if (excludedBeans.contains(bean)) {
			isExcluded = true;
		}
		else {
			for (Class<? extends T> excludedClass : excludedClasses) {
				if (excludedClass.isAssignableFrom(bean.getClass())) {
					isExcluded = true;
					break;
				}
			}
		}

		return isExcluded;
	}

	private boolean isIncluded(T bean) {
		boolean isIncluded = false;

		//nothing was specifically listed as being included, so everything is included
		if (includedBeans.isEmpty() && includedClasses.isEmpty()) return true;

		if (includedBeans.contains(bean)) {
			isIncluded = true;
		}
		else {
			for (Class<? extends T> includedClass : includedClasses) {
				if (includedClass.isAssignableFrom(bean.getClass())) {
					isIncluded = true;
					break;
				}
			}
		}

		return isIncluded;
	}

}
