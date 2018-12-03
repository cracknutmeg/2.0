/**
 *@ Filter
 */
package com.allpoint.model;

/**
 * Filter
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class Filter {

	private final String value;
	private final String name;

	private boolean isActivated = false;

	public Filter(final String name, final String key) {
		this.name = name;
		this.value = key;
	}

	public void setActivated(final boolean isActivated) {
		this.isActivated = isActivated;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public boolean isActivated() {
		return this.isActivated;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Filter filter = (Filter) o;

		if (name != null ? !name.equals(filter.name) : filter.name != null)
			return false;
		if (value != null ? !value.equals(filter.value) : filter.value != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = value != null ? value.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}