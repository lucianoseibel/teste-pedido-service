package br.com.eicon.pedidos_service.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eicon.pedidos_service.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByNumeroControle(String numeroControle);

    Optional<Pedido> findOneByNumeroControle(String numeroControle);

    List<Pedido> findByDataCadastro(LocalDate dataCadastro);
}
