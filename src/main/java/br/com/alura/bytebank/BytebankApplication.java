package br.com.alura.bytebank;

import br.com.alura.bytebank.domain.RegraDeNegocioException;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;
import br.com.alura.bytebank.domain.conta.ContaService;
import br.com.alura.bytebank.domain.conta.DadosAberturaConta;

import java.math.BigDecimal;
import java.util.Scanner;

public class BytebankApplication {

    private static ContaService service = new ContaService();
    private static Scanner teclado = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        var opcaoString = exibirMenu();
        int opcaoNumero = Integer.parseInt(opcaoString);
        while (opcaoNumero != 8) {
            try {
                switch (opcaoNumero) {
                    case 1:
                        listarContas();
                        break;
                    case 2:
                        abrirConta();
                        break;
                    case 3:
                        encerrarConta();
                        break;
                    case 4:
                        consultarSaldo();
                        break;
                    case 5:
                        realizarSaque();
                        break;
                    case 6:
                        realizarDeposito();
                        break;
                    case 7:
                        realizarTransferencia();
                }
            } catch (RegraDeNegocioException e) {
                System.out.println("Erro: " +e.getMessage());
                System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu");
                teclado.next();
            }
            opcaoNumero = Integer.parseInt(exibirMenu());
        }

        System.out.println("Finalizando a aplicação.");
    }

    private static String exibirMenu() {
        System.out.println("""
                BYTEBANK - ESCOLHA UMA OPÇÃO:
                1 - Listar contas abertas
                2 - Abertura de conta
                3 - Encerramento de conta
                4 - Consultar saldo de uma conta
                5 - Realizar saque em uma conta
                6 - Realizar depósito em uma conta
                7 - Realizar Transferência
                8 - Sair
                """);
        return teclado.next().replaceAll("[^\\d]", "");
    }

    private static void listarContas() {
        System.out.println("Contas cadastradas:");
        var contas = service.listarContasAbertas();
        contas.stream().forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void abrirConta() {
        System.out.println("Digite o número da conta:");
        String numeroDaConta = teclado.next().replaceAll("[^\\d]", "");

        System.out.println("Digite o nome do cliente:");
        var nome = teclado.next();

        System.out.println("Digite o cpf do cliente:");
        var cpf = teclado.next().replaceAll("[^\\d]", "");

        System.out.println("Digite o email do cliente:");
        var email = teclado.next();

        service.abrir(new DadosAberturaConta(Integer.parseInt(numeroDaConta), new DadosCadastroCliente(nome, cpf, email)));

        System.out.println("Conta aberta com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void encerrarConta() {
        System.out.println("Digite o número da conta:");
        var numeroDaContaStr = teclado.next().replaceAll("[^\\d]", "");
        int numeroDaConta = Integer.parseInt(numeroDaContaStr);

        service.encerrarLogico(numeroDaConta);

        System.out.println("Conta encerrada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void consultarSaldo() {
        System.out.println("Digite o número da conta:");
        var numeroDaContaStr = teclado.next().replaceAll("[^\\d]", "");
        int numeroDaConta = Integer.parseInt(numeroDaContaStr);
    
        var saldo = service.consultarSaldo(numeroDaConta);
        System.out.println("Saldo da conta: " + saldo);
    
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void realizarSaque() {
        System.out.println("Digite o número da conta:");
        var numeroDaContaStr = teclado.next().replaceAll("[^\\d]", "");
        int numeroDaConta = Integer.parseInt(numeroDaContaStr);

        System.out.println("Digite o valor do saque:");
        var valorStr = teclado.next().replaceAll("[^\\d.]", "");
        BigDecimal valor = new BigDecimal(valorStr);

        service.realizarSaque(numeroDaConta, valor);
        System.out.println("Saque realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void realizarDeposito() {
        System.out.println("Digite o número da conta:");
        var numeroDaContaStr = teclado.next().replaceAll("[^\\d]", "");
        int numeroDaConta = Integer.parseInt(numeroDaContaStr);

        System.out.println("Digite o valor do depósito:");
        var valorStr = teclado.next().replaceAll("[^\\d.]", "");
        BigDecimal valor = new BigDecimal(valorStr);

        service.realizarDeposito(numeroDaConta, valor);

        System.out.println("Depósito realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void realizarTransferencia() {
        System.out.println("Digite o número da conta de origem:");
        var numeroDaContaOrigemStr = teclado.next().replaceAll("[^\\d]", "");
        int numeroDaContaOrigem = Integer.parseInt(numeroDaContaOrigemStr);
    
        System.out.println("Digite o número da conta de destino:");
        var numeroDaContaDestinoStr = teclado.next().replaceAll("[^\\d]", "");
        int numeroDaContaDestino = Integer.parseInt(numeroDaContaDestinoStr);
    
        System.out.println("Digite o valor a ser transferido:");
        var valorStr = teclado.next().replaceAll("[^\\d.]", "");
        BigDecimal valor = new BigDecimal(valorStr);
    
        service.realizarTransferencia(numeroDaContaOrigem, numeroDaContaDestino, valor);
    
        System.out.println("Transferência realizada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }
}