package com.example.weullermarcos.buscaescolas.Models;

import java.io.Serializable;

/**
 * Created by weullermarcos on 08/07/17.
 */

public class TipoAvaliacao implements Serializable {

    private int cod;
    private String nome;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
