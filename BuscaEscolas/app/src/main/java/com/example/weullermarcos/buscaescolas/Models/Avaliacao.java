package com.example.weullermarcos.buscaescolas.Models;

import java.io.Serializable;

/**
 * Created by weullermarcos on 08/07/17.
 */

public class Avaliacao implements Serializable {

    private TipoAvaliacao tipoAvaliacao;
    private int ano;
    private Double valor;


    public TipoAvaliacao getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
