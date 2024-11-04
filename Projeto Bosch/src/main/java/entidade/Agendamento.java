package entidade;

import java.util.Date;

public class Agendamento {



    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(Date dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCarroDescricao() {
        return carroDescricao;
    }

    public void setCarroDescricao(String carroDescricao) {
        this.carroDescricao = carroDescricao;
    }

    public int getHora() {
        return hora;
    }
    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getDia() {
        return dia;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    // CHAVE PRIMÁRIA
    private int idAgenda;

    // CHAVES ESTRANGEIRAS
    private String cpf;
    private String placa;
    //
    private Date dataCriacao;
    private Date dataAgendada;
    //
    private int dia;
    private int hora;
    //
    private String carroDescricao;

}






















