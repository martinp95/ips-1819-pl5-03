package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Pedido;
import com.uniovi.entities.Producto;
import com.uniovi.entities.ProductosPedido;

public interface ProductosPedidoRepository extends CrudRepository<ProductosPedido, Long> {

	@Query(value = "SELECT pp.* FROM PRODUCTO p, PRODUCTOS_PEDIDO pp WHERE pp.PRODUCTO_ID = p.ID and p.ID = ?1 "
			+ "and pp.PEDIDO_ID IN (SELECT pe.ID FROM PEDIDO pe WHERE pe.ID IN(SELECT po.PEDIDO_ID FROM PEDIDOS_ORDEN_TRABAJO po "
			+ "WHERE po.ORDENTRABAJO_ID=?2)) AND pp.CANTIDAD_POR_RECOGER > 0", nativeQuery = true)
	List<ProductosPedido> getProductoPedidoByProductoIDAndOtID(String producto, String ordenTrabajo);

	@Query("SELECT pp from ProductosPedido pp where pp.producto.id = ?1")
	ProductosPedido findByProductoId(String id);

	@Query(value = "SELECT pp.* FROM PRODUCTO p, PRODUCTOS_PEDIDO pp WHERE pp.PRODUCTO_ID = p.ID and p.ID = ?1 "
			+ "and pp.PEDIDO_ID IN (SELECT pe.ID FROM PEDIDO pe WHERE pe.ID IN(SELECT po.PEDIDO_ID FROM PEDIDOS_ORDEN_TRABAJO po "
			+ "WHERE po.ORDENTRABAJO_ID=?2)) AND pp.CANTIDAD_POR_EMPAQUETAR > 0", nativeQuery = true)
	List<ProductosPedido> getProductoPedidoByProductoIDAndOtIDAndNoEmpaquetado(String idProducto, String otID);

	@Query(value = "select pp.* from productos_pedido pp where pp.pedido_id=?1 and pp.producto_id=?2", nativeQuery = true)
	ProductosPedido findProductoPedidoByPedidoAndProducto(Pedido pedido, Producto producto);

	List<ProductosPedido> findByPedido(Pedido pedido);

}
