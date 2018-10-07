package com.uniovi.types;

import java.io.Serializable;

public class ProductosCarritoKey implements Serializable {

	private static final long serialVersionUID = 1L;

	Long user;
	Long Producto;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Producto == null) ? 0 : Producto.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductosCarritoKey other = (ProductosCarritoKey) obj;
		if (Producto == null) {
			if (other.Producto != null)
				return false;
		} else if (!Producto.equals(other.Producto))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
