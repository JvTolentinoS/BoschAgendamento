package interfaceCliente;

import dao.AgendamentoDAO;
import dao.CarroDAO;
import dao.ClienteDAO;
import entidade.Agendamento;
import entidade.Carro;
import entidade.Cliente;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class ClienteServico {

        private static final InterfaceService interfaceService = new InterfaceService();
        private static final ClienteServico clienteServico = new ClienteServico();
        private static final ClienteDAO clienteDAO = new ClienteDAO();
        private static final CarroDAO carroDAO = new CarroDAO();
        private static final AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

        private Cliente clientSession;
        private Carro carroSession;
        private Agendamento agendamentoSession;

        /* SERVIÇO BÁSICO DO CLIENTE */

        // REGISTRO DE CLIENTE
        public void registrador(Cliente cliente, Carro carro, Agendamento agendamento) {
            String cpf, nome;
            int i = -1;
            Scanner scanner = new Scanner(System.in);
                System.out.println("\n========================== CADASTRANDO CLIENTE ===========================");
                System.out.println("Para ter acesso, por favor digite seu NOME e CPF: ");
                    do {
                        System.out.print("Digite seu CPF: ");
                        cpf = scanner.nextLine();
                        cpf = cpf.replaceAll("[.-]", "");
                        if (ValidadorCollection.validarCPF(cpf)) {
                            cliente.setCpf_cliente(cpf);
                            break;
                        } else {
                            UtilCollection.defaultError(scanner); // FALHA
                        }
                    } while (true);

                    if (!ValidadorCollection.verificarSeClienteExiste(cliente, clienteDAO)) {
                        do {
                            System.out.print("Digite seu Nome: ");
                            nome = scanner.nextLine();
                            if (ValidadorCollection.validarNome(nome)) {
                                cliente.setNome_cliente(nome);
                                break;
                            } else {
                                UtilCollection.defaultError(scanner);
                            }
                        } while (true);
                    } else {
                        clienteNome(cliente);
                    }

                    do {
                        try {
                            UtilCollection.defaultConfirm();
                            i = scanner.nextInt();
                            switch (i) {
                                case 0:
                                    UtilCollection.cancel();
                                    limparCliente(cliente, InterfaceService.initialInterface(clienteServico, cliente, carro, agendamento));
                                    break;
                                case 1:
                                    UtilCollection.confirm();
                                    if (ValidadorCollection.verificarSeClienteExiste(cliente, clienteDAO)) {
                                        if (ValidadorCollection.verificarCarro(cliente, carroDAO)) {
                                            instrucaoCarroExiste(carro,
                                                    cliente,
                                                    agendamento);
                                        } else {
                                            registrarNovoCarro(carro,
                                                    cliente,
                                                    agendamento);
                                        }
                                        break;
                                    } else {
                                        clienteDAO.salvar(cliente);
                                        registrarNovoCarro(carro,
                                                cliente,
                                                agendamento);
                                    }
                                    break;
                                default:
                                    UtilCollection.defaultSwitch(scanner);
                                }
                        } catch (InputMismatchException e) {
                            UtilCollection.defaultError(scanner); // FALHA
                            scanner.next();
                        }
                    } while (i != 0);
        }

        // LIMPANDO O BUFFER DE CLIENTE
        private static void limparCliente(Cliente cliente, InterfaceService interfaceService) {
            if (cliente != null) {
                cliente.setCpf_cliente(null);
                cliente.setNome_cliente(null);
            }
        }

        // LIMPANDO O BUFFER DE CARRO
        private static void limparCarro(Carro carro, InterfaceService interfaceService) {
            if (carro != null) {
                carro.setPlaca(null);
                carro.setMarca(null);
                carro.setModelo(null);
                carro.setAno(null);
            }
        }

        // RETORNAR NOME DO USUARIO
        private static String clienteNome(Cliente cliente) {
            for (Cliente c : clienteDAO.getClientes()) {
                if (Objects.equals(c.getCpf_cliente(), cliente.getCpf_cliente())) {
                    cliente.setNome_cliente(c.getNome_cliente());
                    return cliente.getNome_cliente().toUpperCase();
                }
            }
            return null;
        }

        /* LÓGICA DA PARTE DO CARRO */

        // SERVIÇO BÁSICO DO CLIENTE PARA O USO DOS CARROS
        private void instrucaoCarroExiste(Carro carro, Cliente cliente, Agendamento agendamento) {
            int i = -1; // CONTADOR
            do {
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Por favor, escolha entre as seguintes opções para prosseguir:");
                    System.out.println("1 - Escolher entre um carro existente\n2 - Registrar um novo carro\n0 - Voltar");
                    System.out.print("Resposta: ");
                    i = scanner.nextInt();
                        switch (i) {
                            case 0:
                                registrador(cliente,
                                        carro,
                                        agendamento);
                                break;// Voltar
                            case 1:
                                System.out.println("\n========================== CARROS DE " + clienteNome(cliente) + " ===========================");
                                System.out.println("Carros de : " +cliente.getNome_cliente());
                                for (Carro c : carroDAO.getCarrosCliente(cliente.getCpf_cliente())) {
                                    System.out.println(c.getPlaca() +" "+ c.getModelo() +" "+ c.getMarca() +" "+ c.getAno());
                                    System.out.println("_____________________________________");
                                }
                                instrucaoAgendamento(carro, agendamento, cliente); // PULA PARA O AGENDAMENTO
                                break;
                            case 2:
                                registrarNovoCarro(carro,
                                        cliente,
                                        agendamento); // INICIA O REGISTRO DO CARRO
                                break;
                            default:
                                UtilCollection.defaultSwitch(scanner);
                        }
                } catch (InputMismatchException e) {
                    System.out.println("Resposta Inválida");
                }
            } while (i != 0);
        }

        // REGISTRAR NOVO CARRO
        private static void registrarNovoCarro(Carro carro, Cliente cliente, Agendamento agendamento) {
            boolean verificador = false;
            int i = -1;
            String buffer;
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("\n==========================| REGISTRANDO NOVO VEÍCULO |===========================");

                    do {
                        System.out.println("\n========================== PLACA ===========================");
                        System.out.print("Digite a placa do veículo: ");
                        buffer = scanner.nextLine();
                        buffer = buffer.replace("-","");
                        buffer = buffer.toUpperCase();

                        Carro carroVerificador = new Carro();
                        carroVerificador.setPlaca(buffer);

                        if (ValidadorCollection.verificarCarroExiste(carroVerificador, carroDAO)) {
                            System.out.println("A placa "+carroVerificador.getPlaca()+" já possui registro. Verifique a placa informada e tente novamente.");
                        } else {
                            if (ValidadorCollection.verificarPlaca(buffer)) { // verificar placa
                            carro.setPlaca(buffer);
                            break;
                            } else {
                                UtilCollection.defaultError(scanner);
                            }
                        }
                    } while (true);

                    do {
                        System.out.println("\n========================== MARCA ===========================");
                        System.out.print("Digite a marca do veículo: ");
                        buffer = scanner.nextLine();
                        if (ValidadorCollection.verificarMarca(buffer)) { // verificar marca
                            carro.setMarca(buffer);
                            break;
                        } else {
                            UtilCollection.defaultError(scanner);
                        }
                    } while (true);

                    do {
                        System.out.println("\n========================== MODELO ===========================");
                        System.out.print("Digite o modelo do veículo: ");
                        buffer = scanner.nextLine();
                        if (ValidadorCollection.verificarModelo(buffer)) { // verificar modelo
                            carro.setModelo(buffer);
                            break;
                        } else {
                            UtilCollection.defaultError(scanner);
                        }
                    } while (true);

                    do {
                        System.out.println("\n========================== ANO ===========================");
                        System.out.print("Digite o ano de fabricação do veículo: ");
                        buffer = scanner.nextLine();
                        if (ValidadorCollection.verificarAno(buffer)) { // verificar ano
                            carro.setAno(buffer);
                            break;
                        } else {
                            UtilCollection.defaultError(scanner);
                        }
                    } while (true);

                    do {
                        try {
                            UtilCollection.defaultConfirm();
                            i = scanner.nextInt();
                                switch (i) {
                                    case 0:
                                        UtilCollection.cancel();
                                        limparCarro(carro,(InterfaceService.initialInterface(clienteServico, cliente, carro, agendamento)));
                                    case 1:
                                        UtilCollection.confirm();
                                        if (ValidadorCollection.verificarCarroExiste(cliente, carroDAO)) {
                                            System.out.println("Este carro já foi cadastrado, tente novamente.");
                                            scanner.nextLine();
                                        } else {
                                            carro.setCpf(cliente.getCpf_cliente());
                                            carroDAO.salvar(carro);
                                            clienteServico.instrucaoAgendamento(carro,
                                                    agendamento,
                                                    cliente);
                                            break;
                                        }
                                    default:
                                        UtilCollection.defaultSwitch(scanner);
                                }

                        } catch (InputMismatchException e) {
                            UtilCollection.defaultError(scanner);
                            scanner.next();
                        }
                    } while (i != 0);
                } catch (InputMismatchException e) {
                    System.out.println("Erro");
            }
        }

        /* LÓGICA DA PARTE DO AGENDAMENTO */

        // METODO DE REGISTRO DE AGENDAMENTO
        private void instrucaoAgendamento(Carro carro, Agendamento agendamento, Cliente cliente) {
                Scanner scanner = new Scanner(System.in);
                String placa;

                // EXIBE AS DATAS DISPONÍVEIS
                System.out.println("\n========================== DATAS DISPONÍVEIS PARA AGENDAMENTO ===========================\n");
                System.out.println("Escolha entre as datas listadas: ");
                System.out.println("\n1. 04/11/2024\n2. 05/11/2024\n3. 06/11/2024\n4. 07/11/2024\n5. 08/11/2024\n0. Voltar ao menu anterior");

                int opcaoData = -1;
                boolean entradaValida = false;
                int diaEscolhido = 0;

                while (!entradaValida) {
                    try {
                        System.out.print("\nEscolha uma opção (0-5): ");
                        opcaoData = scanner.nextInt();
                        if (opcaoData < 0 || opcaoData > 5) {
                            throw new InputMismatchException(); // Lança exceção se a opção não estiver entre 1 e 5
                        }
                        entradaValida = true;

                        switch (opcaoData) {
                            case 1: diaEscolhido = 4; break;
                            case 2: diaEscolhido = 5; break;
                            case 3: diaEscolhido = 6; break;
                            case 4: diaEscolhido = 7; break;
                            case 5: diaEscolhido = 8; break;
                            case 0:
                                System.out.println("Voltando ao menu anterior...");
                                instrucaoCarroExiste(carro,
                                        cliente,
                                        agendamento);
                                return;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Opção inválida! Por favor, escolha uma das opções (0-5)");
                        scanner.nextLine(); // Limpa o buffer
                    }
                }

                System.out.println("\n========================== HORÁRIOS DISPONÍVEIS  ===========================\n");
                System.out.println("Escolha entre os seguintes horários listados");
                System.out.println("\n1. 08:00\n2. 09:00\n3. 10:00\n4. 11:00\n5. 13:00\n6. 14:00\n7. 15:00\n8. 16:00\n9. 17:00\n10. 18:00\n11. 19:00");

                int opcaoHorario = 0;
                int horaEscolhida = 0;
                entradaValida = false;
                while (!entradaValida) {
                    try {
                        System.out.print("\nEscolha uma opção (1-11): ");
                        opcaoHorario = scanner.nextInt();
                        scanner.nextLine();

                        if (opcaoHorario < 1 || opcaoHorario > 11) {
                            throw new InputMismatchException();
                        }
                        entradaValida = true;

                        //DEFINE A HORA ESCOLHIDA COM BASE NA OPÇÃO
                        switch (opcaoHorario) {
                            case 1: horaEscolhida = 8; break;
                            case 2: horaEscolhida = 9; break;
                            case 3: horaEscolhida = 10; break;
                            case 4: horaEscolhida = 11; break;
                            case 5: horaEscolhida = 13; break;
                            case 6: horaEscolhida = 14; break;
                            case 7: horaEscolhida = 15; break;
                            case 8: horaEscolhida = 16; break;
                            case 9: horaEscolhida = 17; break;
                            case 10: horaEscolhida = 18; break;
                            case 11: horaEscolhida = 19; break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Opção inválida! Por favor, escolha um horário disponível (1-11)");
                        scanner.next();
                    }
                }

                // COLETA DE PLACA
                boolean coletaDePlaca = true;
                while (coletaDePlaca) {
                    System.out.print("Digite a placa do veículo: ");
                    placa = scanner.nextLine();

                    if (ValidadorCollection.verificarPlaca(placa)) {
                        String placaCarro = placa.toUpperCase();
                        for (Carro c : carroDAO.getCarros()) {
                            if (Objects.equals(c.getPlaca(), placaCarro)) {
                                carro.setPlaca(placaCarro);
                                System.out.println("Placa registrada: " + placaCarro);
                                coletaDePlaca = false;
                            }
                        }
                        if (coletaDePlaca) {
                            System.out.println("A Placa mencionada não existe!");
                        }
                    } else {
                        System.out.println("Placa inválida!");
                    }
                }
                // SOLICITAR A DESCRIÇÃO DO DEFEITO
                System.out.print("Nos explique brevemente o tipo de serviço que você precisa ou defeito encontrado: ");
                String defeito = scanner.nextLine();
                System.out.println("Serviço/Defeito: " + defeito);

                // CONFIRMAÇÃO DO AGENDAMENTO
                    boolean confirmarAgendamento = false;
                    while (!confirmarAgendamento) {
                        System.out.print("Deseja confirmar o agendamento? Digite 1 para confirmar ou 0 para não prosseguir: ");
                        try {
                            int opcaoConfirmacao = scanner.nextInt();
                            scanner.nextLine();

                        if (opcaoConfirmacao == 1) {
                            System.out.println("Agendamento confirmado!");
                            agendamento.setCarroDescricao(defeito);
                            agendamento.setDataCriacao(new Date());
                            agendamento.setCpf(cliente.getCpf_cliente());
                            agendamento.setPlaca(carro.getPlaca());
                            agendamento.setDia(diaEscolhido);
                            agendamento.setHora(horaEscolhida);
                            agendamentoDAO.salvar(agendamento);
                            confirmarAgendamento = true; // Finaliza a confirmação
                        } else if (opcaoConfirmacao == 0) {
                            System.out.println("Agendamento não prosseguido. Reiniciando o processo...");
                            confirmarAgendamento = true; // Finaliza a confirmação
                            // O loop principal vai reiniciar
                            agendamento.setCarroDescricao(null);
                            agendamento.setDataAgendada(null);
                        } else {
                            System.out.println("Opção inválida! Por favor, digite 1 para confirmar ou 0 para não prosseguir.");
                        }
                    } catch (InputMismatchException e) {
                UtilCollection.defaultError(scanner);
                scanner.next();
            }
                }

                // DEPOIS DE DEFINIR A AGENDA
                int opcao;
                do {
                try {
                    System.out.println("\nSelecione uma opção: ");
                    System.out.println("1 - Voltar para o Menu Inicial\n0 - Sair");
                opcao = scanner.nextInt();
                switch (opcao)
                {
                    case 1: InterfaceService.initialInterface(clienteServico, cliente, carro, agendamento);
                    break;
                    case 0: System.exit(0);
                    default: UtilCollection.defaultSwitch(scanner);
                }
                    } catch (InputMismatchException e) {
                        UtilCollection.defaultError(scanner);
                        scanner.next();
                }
            } while (true);
        }

        // METODO PARA REMOVER O HIFEN DA PLACA
        private static String removeHifen(String placa) {
            return placa.replace("-", "");
        }

        /* CONSULTA INDIVIDUAL DO CLIENTE */

        public void registradorVerAgendas(Cliente cliente, Carro carro, Agendamento agendamento) {
        String cpf, nome;
        int i = -1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n========================== PROCURANDO AGENDAS REGISTRADAS ===========================");
        System.out.println("Para ter acesso, precisamos de seu CPF: ");
        do {
            System.out.print("Digite seu CPF: ");
            cpf = scanner.next();
            cpf = cpf.replaceAll("[.-]", "");
            if (ValidadorCollection.validarCPF(cpf) && ValidadorCollection.verificarSeClienteExiste(cpf, clienteDAO) && ValidadorCollection.verificarSeClienteAgenda(cpf, agendamentoDAO)) {
                cliente.setCpf_cliente(cpf);
                break;
            } else {
                System.out.println("Falha, nenhuma agenda encontrada");
                limparCliente(cliente, InterfaceService.initialInterface(clienteServico, cliente, carro, agendamento));
            }
        } while (true);

        consultarAgenda(cliente, agendamento, carro);

    }

        public static void consultarAgenda(Cliente cliente, Agendamento agendamento, Carro carro) {
        int idDigitado = 0;
        boolean continuar = true;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                listagemDeAgendas(cliente, agendamento, agendamentoDAO);

                System.out.println("\nEscolha um \"ID\" para consultar os detalhes ");
                System.out.println("\nDigite D para Deletar um agendamento");
                System.out.println("\nDigite 0 para Voltar");
                System.out.print("\nResposta: ");
                String resposta = scanner.next().trim().toUpperCase();

                if (resposta.equals("0")) {
                    limparCliente(cliente, InterfaceService.initialInterface(clienteServico, cliente, carro, agendamento));
                    continuar = false;
                } else if (resposta.equals("D")) {
                    System.out.print("Digite o ID do agendamento que deseja deletar: ");
                    idDigitado = scanner.nextInt();
                    if (apagarAgenda(idDigitado, cliente)) {
                        System.out.println("Agendamento deletado com sucesso.");
                    } else {
                        System.out.println("Falha ao deletar o agendamento. Verifique o ID.");
                    }
                } else if (resposta.matches("\\d+")) {
                    try {
                        idDigitado = Integer.parseInt(resposta);
                        if (verificarId(idDigitado, agendamento, cliente, carro)) {
                            listarCarro(carro, cliente);
                            listarCliente(cliente);
                            listagemEscolhida(cliente, carro, agendamento);

                            boolean respostaValida = false;
                            while (!respostaValida) {
                                System.out.print("\nDeseja consultar outro agendamento? (S/N): ");
                                String resp = scanner.next().trim().toUpperCase();

                                if (resp.equals("S")) {
                                    respostaValida = true;
                                } else if (resp.equals("N")) {
                                    continuar = false; // Sai do loop se o usuário não quiser mais consultar
                                    respostaValida = true;
                                } else {
                                    System.out.println("Entrada inválida! Por favor, digite apenas 'S' para Sim ou 'N' para Não.");
                                }
                            }
                        } else {
                            System.out.println("ID não encontrado. Por favor, escolha um ID válido.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada Inválida");
                        scanner.next();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInsira um valor válido!");
                scanner.next();
            }
        } while (continuar);
    }

        public static void listagemDeAgendas(Cliente cliente, Agendamento agendamento, AgendamentoDAO agendamentoDAO) {
            System.out.println("\n========================== LISTA DE AGENDAMENTOS ===========================");
            System.out.format("+----+-------------+-----------+-----------------------+------------------+%n");
            System.out.format("| ID |     CPF     |   PLACA   |     DATA E HORÁRIO    |  DATA DE CRIACAO |%n");
            System.out.format("+----+-------------+-----------+-----------------------+------------------+%n");

            String rowFormat = "| %-2d | %-11s | %-9s | %-20s | %-16s |%n";
            for (Agendamento listagem : agendamentoDAO.getAgendaUsuario(cliente.getCpf_cliente())) {
                System.out.format(rowFormat,
                        listagem.getIdAgenda(),
                        listagem.getCpf(),
                        listagem.getPlaca(),
                        listagem.getDataAgendada(),
                        listagem.getDataCriacao());
                System.out.format("+----+-------------+-----------+-----------------------+------------------+%n");
            }
        }

        public static boolean verificarId(int id, Agendamento agendamento, Cliente cliente, Carro carro) {
        for (Agendamento a : agendamentoDAO.getAgendaUsuario(cliente.getCpf_cliente())) {
            if (a.getIdAgenda() == id) {
                agendamento.setIdAgenda(a.getIdAgenda());
                agendamento.setDataAgendada(a.getDataAgendada());
                agendamento.setCarroDescricao(a.getCarroDescricao());
                cliente.setCpf_cliente(a.getCpf());
                carro.setPlaca(a.getPlaca());

                return true;
            }
        }
        return false;
    }

        public static boolean apagarAgenda(int id, Cliente cliente) {
        for (Agendamento a : agendamentoDAO.getAgendaUsuario(cliente.getCpf_cliente())) {
            if (a.getIdAgenda() == id) {
                agendamentoDAO.deletarAgendamentoPorId(a.getIdAgenda());
                return true;
            }
        }
        return false;
    }

        public static void listarCarro(Carro carro, Cliente cliente) {
        for (Carro c : carroDAO.getCarrosCliente(cliente.getCpf_cliente())) {
            if (Objects.equals(carro.getPlaca(), c.getPlaca())) {
                carro.setPlaca(c.getPlaca());
                carro.setMarca(c.getMarca());
                carro.setModelo(c.getModelo());
                carro.setAno(c.getAno());
            }
        }
    }

        public static void listarCliente(Cliente cliente) {
        for (Cliente c : clienteDAO.getClientes()) {
            if (Objects.equals(cliente.getCpf_cliente(), c.getCpf_cliente())) {
                cliente.setNome_cliente(c.getNome_cliente());
            }
        }
    }

        public static void listagemEscolhida(Cliente cliente, Carro carro, Agendamento agendamento) {
        // Exibindo detalhes do agendamento em formato de tópicos
        System.out.println("\n======================== DETALHES DO AGENDAMENTO ========================");
        System.out.println("ID do Agendamento: " + agendamento.getIdAgenda());
        System.out.println("Data e Horário Escolhido: " + agendamento.getDataAgendada());
        System.out.println("Placa do Carro: " + carro.getPlaca());
        System.out.println("Marca do Carro: " + carro.getMarca());
        System.out.println("Modelo do Carro: " + carro.getModelo());
        System.out.println("Ano do Carro: " + carro.getAno());
        System.out.println("Defeito: " + agendamento.getCarroDescricao());
        System.out.println("Nome do Cliente: " + cliente.getNome_cliente());
        System.out.println("CPF do Cliente: " + cliente.getCpf_cliente());
        System.out.println("===========================================================================");
    }
}