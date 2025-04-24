package com.ravania.api.dao;

import com.ravania.api.connection.DBConnect;
import com.ravania.api.dto.TransacaoDto;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * <p>
 *     Esta classe é responsável por permitir a troca de dados entre a classe {@code TransacaoController} e o banco de dados.
 * </p>
 */
public class TransacaoDao extends DBConnect {

    /**
     * Este método obtém uma lista de todas as transações do banco de dados.
     * @return O método returna uma lista de transações {@code ArrayList<TransacaoDto>}.
     */
    public ArrayList<TransacaoDto> obterListaTransacoes(){
        ArrayList<TransacaoDto> listaTransacoes = new ArrayList<>();
        try {
            stmt = this.conectar().createStatement();
            rst = stmt.executeQuery("SELECT * FROM transacoes");
            while(rst.next()){
                TransacaoDto transacao = new TransacaoDto();
                transacao.setCodigo(rst.getInt("id_transacao"));
                transacao.setDataTransacao(rst.getTimestamp("data_transacao").toLocalDateTime());
                transacao.setCvv(rst.getInt("cvv"));
                transacao.setStatus(rst.getString("status_transacao"));
                transacao.setValor(rst.getFloat("valor_transacao"));
                transacao.setNumeroCartao(rst.getInt("numeroCartao"));
                listaTransacoes.add(transacao);
            }
            return listaTransacoes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            this.fecharConexao(0);
        }
    }

    /**
     * Este método obtém uma lista de todas as transações do banco de dados, mas com o filtro por data.
     * @param data
     * @return O método retorna uma lista de transações {@code ArrayList<TransacaoDto>}.
     */
    public ArrayList<TransacaoDto> obterListaTransacoes(final String data){
        ArrayList<TransacaoDto> listaTransacoes = new ArrayList<>();
        try {
            stmt = this.conectar().createStatement();
            rst = stmt.executeQuery("SELECT * FROM transacoes WHERE data_transacao = '" + data + "';");
            while(rst.next()){
                TransacaoDto transacao = new TransacaoDto();
                transacao.setCodigo(rst.getInt("id_transacao"));
                transacao.setDataTransacao(rst.getTimestamp("data_transacao").toLocalDateTime());
                transacao.setCvv(rst.getInt("cvv"));
                transacao.setStatus(rst.getString("status_transacao"));
                transacao.setValor(rst.getFloat("valor_transacao"));
                transacao.setNumeroCartao(rst.getInt("numeroCartao"));
                listaTransacoes.add(transacao);
            }
            return listaTransacoes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            this.fecharConexao(0);
        }
    }

    /**
     * Este método permite a criação de uma transação no banco de dados.
     * @param pTransacaoDto
     * @return O método retorna {@code Boolean}.
     */
    public boolean criarTransacao(TransacaoDto pTransacaoDto){
        try {
            stmt = this.conectar().createStatement();
            stmt.execute("INSERT INTO transacoes(" +
                //"id_transacao, " +
                "data_transacao, " +
                "status_transacao, " +
                "valor_transacao, " +
                "cvv, " +
                "numeroCartao" +
                ") VALUES (" +
                //"'"+pTransacaoDto.getCodigo()+"', " +
                "'"+pTransacaoDto.getDataTransacao()+"', " +
                "'"+pTransacaoDto.getStatus()+"', " +
                "'"+pTransacaoDto.getValor()+"', " +
                "'"+pTransacaoDto.getCvv()+"', " +
                "'"+pTransacaoDto.getNumeroCartao()+"');");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            this.fecharConexao(1);
        }
    }
}
