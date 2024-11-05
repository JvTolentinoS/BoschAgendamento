//COMPLETO

package dao;

import conexaofabrica.Conexao;
import entidade.Carro;
import entidade.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    public List<Carro> getCarrosCliente(String cpf) {
        String sql = "SELECT * FROM carro WHERE cpf = ?";
        List<Carro> carros = new ArrayList<Carro>();

        Connection conn = null;

        PreparedStatement pstm = null;

        ResultSet rset = null;
        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, cpf);

            rset = pstm.executeQuery();
            while (rset.next()) {
                Carro carro = new Carro();

                carro.setCpf(rset.getString("cpf"));
                carro.setPlaca(rset.getString("carro_placa"));
                carro.setModelo(rset.getString("carro_modelo"));
                carro.setMarca(rset.getString("carro_marca"));
                carro.setAno(rset.getString("carro_ano"));

                carros.add(carro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (rset != null) {
                    rset.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return carros;
        }
    }

}

