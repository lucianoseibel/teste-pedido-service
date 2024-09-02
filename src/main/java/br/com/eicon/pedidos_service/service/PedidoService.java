package br.com.eicon.pedidos_service.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eicon.pedidos_service.model.Pedido;
import br.com.eicon.pedidos_service.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        if (pedido.getDataCadastro() == null) {
            pedido.setDataCadastro(LocalDate.now());
        }
        if (pedido.getQuantidade() == null || pedido.getQuantidade() == 0) {
            pedido.setQuantidade(1);
        }
        BigDecimal total = pedido.getValor().multiply(BigDecimal.valueOf(pedido.getQuantidade()));
        if (pedido.getQuantidade() >= 10) {
            total = total.multiply(BigDecimal.valueOf(0.90));
        } else if (pedido.getQuantidade() >= 5) {
            total = total.multiply(BigDecimal.valueOf(0.95));
        }
        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> searchPedidos(String numeroControle, LocalDate dataCadastro) {
        if (numeroControle != null) {
            return pedidoRepository.findByNumeroControle(numeroControle).stream().toList();
        } else if (dataCadastro != null) {
            return pedidoRepository.findByDataCadastro(dataCadastro);
        } else {
            return pedidoRepository.findAll();
        }
    }

    public void savePedidos(List<Pedido> pedidos) {
        if (pedidos.size() > 10) {
            throw new IllegalArgumentException("Não é permitido enviar mais de 10 pedidos.");
        }
        for (Pedido pedido : pedidos) {
            validateAndSavePedido(pedido);
        }
    }

    private void validateAndSavePedido(Pedido pedido) {
        if (pedidoRepository.findByNumeroControle(pedido.getNumeroControle()) != null) {
            throw new IllegalArgumentException("Número de controle já existe.");
        }

        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new IllegalArgumentException("Código de cliente não informado.");
        }

        if (pedido.getCliente().getId() < 1 || pedido.getCliente().getId() > 10) {
            throw new IllegalArgumentException("Código de cliente inválido. (códigos válidos 1 a 10)");
        }

        if (pedido.getDataCadastro() == null) {
            pedido.setDataCadastro(LocalDate.now());
        }

        if (pedido.getQuantidade() == null) {
            pedido.setQuantidade(1);
        }

        double valorTotal = pedido.getValor().doubleValue() * pedido.getQuantidade();

        if (pedido.getQuantidade() > 10) {
            valorTotal *= 0.9; // 10% de desconto
        } else if (pedido.getQuantidade() > 5) {
            valorTotal *= 0.95; // 5% de desconto
        }

        pedido.setTotal((BigDecimal.valueOf(valorTotal)));

        pedidoRepository.save(pedido);
    }

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> getPedidoByNumeroControle(String numeroControle) {
        return pedidoRepository.findOneByNumeroControle(numeroControle);
    }
}
