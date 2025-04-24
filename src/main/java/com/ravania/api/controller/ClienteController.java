package com.ravania.api.controller;

import com.ravania.api.dao.ClienteDao;
import com.ravania.api.dto.ClienteDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    /**
     * <p>
     *     Lista todos os clientes no banco de dados. Se o parâmetro {@code nome} for fornecido, a lista retornada é
     *     filtrada por nome.
     * </p>
     * @param nome
     * @return É retornado uma resposta {@code ResponseEntity} com uma lista de clientes do tipo {@code ArrayList<ClienteDto>}.
     */
    @GetMapping
    private ResponseEntity<ArrayList<ClienteDto>> listarClientes(@RequestParam(name = "nome", required = false) final String nome){
        if(Objects.isNull(nome)) {
            try {
                ArrayList<ClienteDto> listaClientes = new ClienteDao().obterListaClientes();
                return ResponseEntity.ok(listaClientes);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        } else {
            try {
                ArrayList<ClienteDto> listaClientes = new ClienteDao().obterListaClientes(nome);
                return ResponseEntity.ok(listaClientes);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        }
    }

    /**
     * <p>
     *     Este método inclui um cliente no banco de dados.
     * </p>
     * @param clienteDto
     * @return O método retorna um {@code ResponseEntity}, que pode ser {@code HttpStatus.CREATED} (201) ou {@code HttpStatus.UNPROCESSABLE_ENTITY}.
     */
    @PostMapping("/incluir")
    private ResponseEntity<ClienteDto> incluirClientes(@RequestBody final ClienteDto clienteDto){
        ClienteDao clienteDao = new ClienteDao();
        if(Objects.equals(clienteDao.incluirCliente(clienteDto), true)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
