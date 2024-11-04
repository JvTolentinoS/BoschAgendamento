package dao;

import conexaofabrica.Conexao;
import entidade.Agendamento;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {


    public void salvar(Agendamento agendamento) {
        String sql = "INSERT INTO agenda (cpf, data_criacao, data_agendada, carro_placa, carro_descricao) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, agendamento.getCpf());
            //DATA QUE VAI SER DEFINDA PARA CONSULTA
            LocalDateTime dataPrevista = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), agendamento.getDia(), agendamento.getHora(),0);
            pstm.setDate(2, new Date(agendamento.getDataCriacao().getTime()));
            pstm.setTimestamp(3, Timestamp.valueOf(dataPrevista));
            //
            pstm.setString(4, agendamento.getPlaca());
            pstm.setString(5, agendamento.getCarroDescricao());

            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            if (conn!=null) {conn.close();}
            if (pstm!=null) {pstm.close();}

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void atualizar(Agendamento agendamento) {

        String sql = "UPDATE agenda SET data_agendada = ? WHERE cpf = ? AND carro_placa = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            //DATA QUE VAI SER DEFINDA PARA CONSULTA

            LocalDateTime dataPrevista = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), agendamento.getDia(), agendamento.getHora(),0);
            pstm.setTimestamp(1, Timestamp.valueOf(dataPrevista));
            pstm.setString(2, agendamento.getCpf());
            pstm.setString(3, agendamento.getPlaca());
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn!=null) {conn.close();}
                if (pstm!=null) {pstm.close();}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

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

    public List<Agendamento> getAgendaTodos()   {
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

                if (rset!=null) {rset.close();}
                if (pstm!=null) {pstm.close();}
                if (conn!=null) {conn.close();}

            } catch (Exception e) {
                e.printStackTrace();
            }
            return agendamentos;
        }
    }

    public List<Agendamento> getAgendaUsuario(String cpf) {
        String sql = "SELECT * FROM agenda WHERE cpf = ?";

        List<Agendamento> agendamentos = new ArrayList<Agendamento>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {

            conn = Conexao.criarConexao();

            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, cpf);

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
            System.out.println("Falha na Leitura dos Pedidos");
        } finally {
            try {
                if (conn!=null){
                    conn.close();
                }
                if (pstm!=null){
                    pstm.close();
                }
                if (rset!=null){
                    rset.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return agendamentos;
        }
    }
}

