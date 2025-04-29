package com.ravania.api.controller;

import com.ravania.api.dao.TransacaoDao;
import com.ravania.api.dto.TransacaoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    public static final Logger LOGGER = Logger.getLogger(TransacaoController.class.getName());

    /**
     * <p>
     *     Lista todas as transações no banco de dados, caso o parâmetro data seja fornecido, as transações são filtradas por data.
     * </p>
     * @param data no formato AAAA/MM/DD.
     * @return Retorna uma lista {@code ResponseEntity} com uma lista de transações {@code ArrayList<TransacaoDto>}.
     */
    @GetMapping
    public ResponseEntity<ArrayList<TransacaoDto>> listarTransacoes(@RequestParam(name = "data", required = false) final String data){
        if (Objects.isNull(data)) {
            try {
                ArrayList<TransacaoDto> listaTransacoes = new TransacaoDao().obterListaTransacoes();
                LOGGER.log(Level.FINE,"Lista de transações retornada com sucesso.");
                return ResponseEntity.ok(listaTransacoes);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                assert LOGGER != null;
                LOGGER.log(Level.WARNING, "Ocorreu um erro ao buscar as transações no banco de dados: "+e.getMessage());
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        } else {
            try {
                ArrayList<TransacaoDto> listaTransacoes = new TransacaoDao().obterListaTransacoes(data);
                LOGGER.log(Level.FINE,"Lista de transações retornada com sucesso.");
                return ResponseEntity.ok(listaTransacoes);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                LOGGER.log(Level.WARNING, "Ocorreu um erro ao buscar as transações no banco de dados: "+e.getMessage());
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        }
    }

    /**
     * <p>
     *     Inlcui uma transacão no banco de dados.
     *     O sistema não aceitará datas fornecidas no corpo da mensagem, ele substituirá a data e hora pela atual.
     * </p>
     * @param transacaoDto preenchido com status, valor, cvv e número do cartão.
     * @return Retorna uma resposta do tipo {@code HttpStatus.CREATED}(201) ou {@code HttpStatus.UNPROCESSABLE_ENTITY}
     */
    @PostMapping("/incluir")
    public ResponseEntity<TransacaoDto> incluirTransacao(@RequestBody final TransacaoDto transacaoDto){
        TransacaoDao transacaoDao = new TransacaoDao();
        transacaoDto.setDataTransacao(LocalDateTime.now());
        if(transacaoDto.getValor() <= -1){
            LOGGER.log(Level.WARNING,"O valor da transação deve ser positivo ou igual a zero.");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        if(Objects.equals(transacaoDao.criarTransacao(transacaoDto), true)) {
            LOGGER.log(Level.FINE, "Transação incluída com sucesso no banco de dados.");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            LOGGER.log(Level.WARNING, "Ocorreu um erro ao criar a transação.");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
