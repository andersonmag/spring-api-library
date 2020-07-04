package com.api.library.service;

import java.time.LocalDateTime;
import java.util.Random;
import com.api.library.model.Pedido;
import com.api.library.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido salvar(Pedido pedido) {

        int codigo = 0;
        boolean codigoRepetido = false;

        do {
            codigoRepetido = false;
            codigo = obterCodigo();
            System.err.println(codigo);
            for (Pedido pedidoIt : pedidoRepository.findAll()) {
                if (pedidoIt.getCodigo() == codigo)
                    codigoRepetido = true;
            }
        } while (codigoRepetido);

        pedido.setCodigo(codigo);
        pedido.setData(LocalDateTime.now());

        return pedidoRepository.save(pedido);
    }

    private int obterCodigo() {
        Random random = new Random();
        int totalIt = 4;
        int codigo = 0;

        for (int i = 0; i <= totalIt; i++) {
             codigo += Integer.parseInt(random.nextInt((10000 - 1000) + 1) + 1000 + "" );
        }

        return codigo;
    }

}