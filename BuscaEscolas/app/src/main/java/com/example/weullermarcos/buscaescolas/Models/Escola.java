package com.example.weullermarcos.buscaescolas.Models;

/**
 * Created by weullermarcos on 04/07/17.
 */

public class Escola {

    private int codEscola;
    private String nome;
    private Double latitude;
    private Double longitude;
    private String rede;
    private String email;
    private String esferaAdministrativa;
    private String categoriaEscolaPrivada;
    private String situacaoFuncionamento;
    private String tipoConvenioPoderPublico;
    private String cnpj;
    private String telefone;




//            "seFimLucrativo": "N",
//            "seConveniadaSetorPublico": "N",
//            "qtdSalasExistentes": 6,
//            "qtdSalasUtilizadas": 6,
//            "qtdFuncionarios": 34,
//            "qtdComputadores": 10,
//            "qtdComputadoresPorAluno": 10,
//            "qtdAlunos": 341,
//            "endereco": {
//        "cep": "76820170",
//                "descricao": "RUA RAIMUNDO CANTUARIA 3862ESQUINA COM EQUADOR",
//                "bairro": "NOVA PORTO VELHO",
//                "municipio": "Porto Velho                                       ",
//                "uf": "RO"
//    },
//        "zona": "URBANA",
//            "infraestrutura": {
//        "temQuadraEsporteCoberta": "N",
//                "temQuadraEsporteDescoberta": "S",
//                "temInternet": "S",
//                "temBandaLarga": "S",
//                "temLaboratorioInformatica": "S",
//                "temLaboratorioCiencias": "N",
//                "temRefeitorio": "N",
//                "temAuditorio": "N",
//                "temDespensa": "N",
//                "temAlmoxarifado": "N",
//                "temPatioCoberto": "S",
//                "temPatioDescoberto": "N",
//                "temParqueInfantil": "N",
//                "temCozinha": "S",
//                "temBiblioteca": "S",
//                "temBercario": "N",
//                "temSanitarioNoPredio": "S",
//                "temSanitarioForaPredio": "N",
//                "temSalaLeitura": "N",
//                "temAreaVerde": "N",
//                "temAguaFiltrada": "S",
//                "temAcessibilidade": "N",
//                "temCreche": "N",
//                "temEnsinoFundamental": "S",
//                "temEnsinoMedio": "N",
//                "temEnsinoMedioNormal": "N",
//                "temEnsinoMedioProfissional": "N",
//                "temEnsinoMedioIntegrado": "N",
//                "temEducacaoJovemAdulto": "N",
//                "temEducacaoIndigena": "N",
//                "banheiroTemChuveiro": "N",
//                "ofereceAlimentacao": "S",
//                "atendeEducacaoEspecializada": "S"
//    },
//        "links": [
//        {
//            "rel": "self",
//                "href": "http://mobile-aceite.tcu.gov.br/nossaEscolaRS/rest/escolas/11000651"
//        }
//        ]
//    },


    public int getCodEscola() {
        return codEscola;
    }

    public void setCodEscola(int codEscola) {
        this.codEscola = codEscola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRede() {
        return rede;
    }

    public void setRede(String rede) {
        this.rede = rede;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEsferaAdministrativa() {
        return esferaAdministrativa;
    }

    public void setEsferaAdministrativa(String esferaAdministrativa) {
        this.esferaAdministrativa = esferaAdministrativa;
    }

    public String getCategoriaEscolaPrivada() {
        return categoriaEscolaPrivada;
    }

    public void setCategoriaEscolaPrivada(String categoriaEscolaPrivada) {
        this.categoriaEscolaPrivada = categoriaEscolaPrivada;
    }

    public String getSituacaoFuncionamento() {
        return situacaoFuncionamento;
    }

    public void setSituacaoFuncionamento(String situacaoFuncionamento) {
        this.situacaoFuncionamento = situacaoFuncionamento;
    }

    public String getTipoConvenioPoderPublico() {
        return tipoConvenioPoderPublico;
    }

    public void setTipoConvenioPoderPublico(String tipoConvenioPoderPublico) {
        this.tipoConvenioPoderPublico = tipoConvenioPoderPublico;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
