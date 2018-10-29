package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Producto;

public interface ProductosRepository extends CrudRepository<Producto, Long> {

	@Query("SELECT p FROM Producto p WHERE (LOWER(p.name) LIKE LOWER(?1) " + "OR LOWER(p.description) LIKE LOWER(?1))")
	Page<Producto> searchByNameAndDescription(Pageable pageable, String searchText);

	@Query("SELECT p FROM Producto p")
	Page<Producto> findAll(Pageable pageable);

	@Query(value = "SELECT p.*, pp.cantidad_por_recoger FROM PRODUCTO  p,PRODUCTOS_PEDIDO pp where pp.producto_id=p.id "
			+ "and p.id in (SELECT producto_id FROM PRODUCTOS_PEDIDO "
			+ "where pedido_id in (SELECT PEDIDO_ID FROM PEDIDOS_ORDEN_TRABAJO WHERE ORDENTRABAJO_ID=?1)) "
			+ "order by p.pasillo , p.posicion , p.num_estanteria , p.num_fila", nativeQuery = true)
	List<Object> findProductosByOtOrderByPosicionAlmacen(OrdenTrabajo ordenTrabajo);

}
