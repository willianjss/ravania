package com.ravania.api.dto;

import java.sql.Date;
import java.time.LocalDateTime;

public class TransacaoDto {
    private int codigo;
    private LocalDateTime dataTransacao;
    private String status;
    private Float valor;
    private int cvv;
    private int numeroCartao;

    public TransacaoDto(int codigo, LocalDateTime dataTransacao, String status, Float valor, int cvv, int numeroCartao) {
        this.codigo = codigo;
        this.dataTransacao = dataTransacao;
        this.status = status;
        this.valor = valor;
        this.cvv = cvv;
        this.numeroCartao = numeroCartao;
    }

    public TransacaoDto() {

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(int numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
}
