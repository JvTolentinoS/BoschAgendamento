package dao;

import conexaofabrica.Conexao;
import entidade.Carro;
import entidade.Cliente;
import entidade.Agendamento;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public void deletarAgendamentoPorId(int id) {
        String sql = "DELETE FROM agenda WHERE idAgenda = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setInt(1, id);
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) { conn.close(); }
                if (pstm != null) { pstm.close(); }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Agendamento> getAgendaTodos() {
        String sql = "SELECT * FROM agenda";

        List<Agendamento> agendamentos = new ArrayList<Agendamento>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = Conexao.criarConexao();

            pstm = conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while (rset.next()) {
                Agendamento agendamento = new Agendamento();

                agendamento.setIdAgenda(rset.getInt("idAgenda"));
                agendamento.setCpf(rset.getString("cpf"));
                agendamento.setDataCriacao(rset.getDate("data_criacao"));
                agendamento.setDataAgendada(rset.getTimestamp("data_agendada"));
                agendamento.setPlaca(rset.getString("carro_placa"));
                agendamento.setCarroDescricao(rset.getString("carro_descricao"));

                agendamentos.add(agendamento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha na Leitura das Agendas");
        } finally {
            try {
                if (rset != null) { rset.close(); }
                if (pstm != null) { pstm.close(); }
                if (conn != null) { conn.close(); }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return agendamentos;
        }
    }

}
