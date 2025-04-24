package com.ravania.api.dto;

import java.sql.Date;

public class ClienteDto {
    private int codigo;
    private String nome;
    private Date dataNascimento;
    private int cpf;

    public ClienteDto(int codigo, String nome, Date dataNascimento, int cpf) {
        this.codigo = codigo;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
    }

    public ClienteDto() {

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }


}
