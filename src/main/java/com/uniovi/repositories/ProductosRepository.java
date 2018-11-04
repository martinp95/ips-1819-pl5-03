package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Producto;
import com.uniovi.entities.ProductosPedido;

public interface ProductosRepository extends CrudRepository<Producto, Long> {

	@Query("SELECT p FROM Producto p WHERE (LOWER(p.name) LIKE LOWER(?1) " + "OR LOWER(p.description) LIKE LOWER(?1))")
	List<Producto> searchByNameAndDescription(String searchText);

	@Query("SELECT p FROM Producto p")
	List<Producto> findAll();

	@Query(value = "SELECT p.*, pp.CANTIDAD_POR_RECOGER FROM PRODUCTO p, PRODUCTOS_PEDIDO pp WHERE pp.PRODUCTO_ID = p.ID "
			+ "and pp.PEDIDO_ID IN (SELECT pe.ID FROM PEDIDO pe WHERE pe.ID IN(SELECT po.PEDIDO_ID FROM PEDIDOS_ORDEN_TRABAJO po "
			+ "WHERE po.ORDENTRABAJO_ID=?1)) AND pp.CANTIDAD_POR_RECOGER > 0 order by p.pasillo , p.posicion , p.num_estanteria , p.num_fila", nativeQuery = true)
	List<Object> findProductosByOtOrderByPosicionAlmacen(OrdenTrabajo ordenTrabajo);

	@Query(value = "SELECT p.*, pp.CANTIDAD_POR_RECOGER FROM PRODUCTO p, PRODUCTOS_PEDIDO pp WHERE pp.PRODUCTO_ID = p.ID and p.ID = ?1 "
			+ "and pp.PEDIDO_ID IN (SELECT pe.ID FROM PEDIDO pe WHERE pe.ID IN(SELECT po.PEDIDO_ID FROM PEDIDOS_ORDEN_TRABAJO po "
			+ "WHERE po.ORDENTRABAJO_ID=?2)) AND pp.CANTIDAD_POR_RECOGER > 0", nativeQuery = true)
	List<Producto> getProductoByProductoIDAndOtID(String producto, String ordenTrabajo);
	
	/*
	 * Seleccionar los productosPedido que sean de una orden de trabajo dada que  no tenga incidencia
	 * y no esten empaquetados ordenados por los pasillos, etc
	 */
	
//	@Query("SELECT pp from ProductosPedido pp WHERE ?1 IN pp.pedido.pedidoOrdenesTrabajo.ordenTrabajo "
//			+ "and pp.pedido.pedidoOrdenesTrabajo.ordenTrabajo.incidencia = False and pp.paquete is null"
//			+ " order by pp.producto.pasillo , pp.producto.posicion , pp.producto.num_estanteria , pp.producto.num_fila")
	
	//consulta para que pueda ejecutarse
	@Query("select pp from ProductosPedido pp")
	List<ProductosPedido> findProductoByOtNoincidenciaNoEmpaquetado(OrdenTrabajo ordenTrabajo);
}
