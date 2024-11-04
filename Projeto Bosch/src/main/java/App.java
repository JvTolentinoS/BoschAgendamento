import conexaofabrica.Conexao;
import interfaceCliente.*;

import java.sql.Connection;

import static conexaofabrica.Conexao.criarConexao;

public class App {
    ClienteServico clienteServico = new ClienteServico();
    public static void main(String[] args) throws Exception {
        try {
        Connection con = criarConexao();
        if (con!=null) {
            System.out.println("Conexão obtida com sucesso");
            System.out.println(con);
            con.close();
                ClienteServico clienteServico = new ClienteServico();
                InterfaceService service = new InterfaceService();
                service.inicializadorCliente(clienteServico);
        }

        } catch (Exception e) {
            System.out.println("Erro ao Conectar");
            System.exit(0);
        }
    }
}
