package com.ravania.api.controller;

import com.ravania.api.dao.TransacaoDao;
import com.ravania.api.dto.TransacaoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    /**
     * <p>
     *     Lista todas as transações no banco de dados, caso o parâmetro data seja fornecido, as transações são filtradas por data.
     * </p>
     * @param data
     * @return Retorna uma lista {@code ResponseEntity} com uma lista de transações {@code ArrayList<TransacaoDto>}.
     */
    @GetMapping
    public ResponseEntity<ArrayList<TransacaoDto>> listarTransacoes(@RequestParam(name = "data", required = false) final String data){
        if (Objects.isNull(data)) {
            try {
                ArrayList<TransacaoDto> listaTransacoes = new TransacaoDao().obterListaTransacoes();
                return ResponseEntity.ok(listaTransacoes);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        } else {
            try {
                ArrayList<TransacaoDto> listaTransacoes = new TransacaoDao().obterListaTransacoes(data);
                return ResponseEntity.ok(listaTransacoes);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        }
    }

    /**
     * <p>
     *     Inlcui uma transacão no banco de dados.
     * </p>
     * @param transacaoDto
     * @return Retorna uma resposta do tipo {@code HttpStatus.CREATED}(201) ou {@code HttpStatus.UNPROCESSABLE_ENTITY}
     */
    @PostMapping("/incluir")
    public ResponseEntity<TransacaoDto> incluirTransacao(@RequestBody final TransacaoDto transacaoDto){
        TransacaoDao transacaoDao = new TransacaoDao();
        transacaoDto.setDataTransacao(LocalDateTime.now());
        if(Objects.equals(transacaoDao.criarTransacao(transacaoDto), true)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
