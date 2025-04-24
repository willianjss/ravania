package com.ravania.api.dao;

import com.ravania.api.connection.DBConnect;
import com.ravania.api.dto.ClienteDto;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * <p>
 *     Esta classe é responsável por fornecer o acesso aos dados do servidor à {@code ClienteController}
 * </p>
 */
public class ClienteDao extends DBConnect {
    /**
     * <p>
     *     Este método executa uma requisição SQL para obter todos os clientes do banco de dados.
     * </p>
     * @return O método retorna uma lista de clientes {@code ArrayList<ClienteDto>}.
     */
    public ArrayList<ClienteDto> obterListaClientes(){
        ArrayList<ClienteDto> listaClientes = new ArrayList<>();
        try {
            stmt = this.conectar().createStatement();
            rst = stmt.executeQuery("SELECT * FROM clientes");
            while(rst.next()) {
                ClienteDto cliente = new ClienteDto();
                cliente.setCodigo(rst.getInt("id_cliente"));
                cliente.setNome(rst.getString("nome_cliente"));
                cliente.setDataNascimento(rst.getDate("data_nascimento"));
                cliente.setCpf(rst.getInt("cpf_cliente"));
                listaClientes.add(cliente);
            }
            return listaClientes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            this.fecharConexao(0);
        }
    }

    /**
     * Este método obtém uma lista de clientes fazendo uma requisição SQL, mas filtrando pelo nome.
     * @param pNomeCliente
     * @return O método retorna uma lista de clientes {@code ArrayList<ClienteDto>}.
     */
    public ArrayList<ClienteDto> obterListaClientes(String pNomeCliente){
        ArrayList<ClienteDto> listaClientes = new ArrayList<>();
        try {
            stmt = this.conectar().createStatement();
            rst = stmt.executeQuery("SELECT * FROM clientes WHERE nome_cliente LIKE '"+pNomeCliente+"%';");
            while(rst.next()) {
                ClienteDto cliente = new ClienteDto();
                cliente.setCodigo(rst.getInt("id_cliente"));
                cliente.setNome(rst.getString("nome_cliente"));
                cliente.setDataNascimento(rst.getDate("data_nascimento"));
                cliente.setCpf(rst.getInt("cpf_cliente"));
                listaClientes.add(cliente);
            }
            return listaClientes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            this.fecharConexao(0);
        }
    }

    /**
     * Este método inclui um cliente no banco de dados.
     * @param pCliente
     * @return O método returna {@code Boolean}.
     */
    public boolean incluirCliente(ClienteDto pCliente){
        try{
            stmt = this.conectar().createStatement();
            stmt.execute("INSERT INTO clientes(" +
                    "nome_cliente, " +
                    "data_nascimento, " +
                    "cpf_cliente" +
                    ") VALUES (" +
                    "'" +pCliente.getNome()+ "', " +
                    "'" +pCliente.getDataNascimento()+ "', " +
                    "'" +pCliente.getCpf()+ "'" +
                    ");");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            this.fecharConexao(1);
        }
    }
}
