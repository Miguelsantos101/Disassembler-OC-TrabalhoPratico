import java.io.*;
//
// Não alterar o nome da classe!
//
class disasm {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }

        String filename = args[0];

        try (InputStream inputStream = new FileInputStream(filename);) {

            // Declaração de variáveis
            // String[] operacoesMarie: Guarda todas as operações MARIE em ordem numérica (0 até 14)
            String[] operacoesMarie = new String[]{"jns", "load", "store", "add", "subt",
                                                   "input", "output", "halt", "skipcond", "jump",
                                                   "clear", "addi", "jumpi", "loadi", "storei"};

            // String[] _16Bits: Vetor de strings para guardar dois bytes em sequência em binário
            String[] _16Bits = new String[2];

            // int instrucao: Recebe o byte lido em decimal no while abaixo
            // int contador: Faz a diferenciação entre o primeiro e segundo byte
            int instrucao, contador = 0;

            // O while abaixo serve para ler bytes até o final do arquivo
            while ((instrucao = inputStream.read()) != -1) {
                // int opcode: Guarda o indice a ser usado no vetor de strings (operacoesMarie)
                int opcode = 0;
                
                // String opcodeS: Guarda os 4 primeiros bits de operação e os 4 primeiros bits de endereço
                // String enderecoS: Guarda os 8 bits que de endereço restantes
                String opcodeS = "";
                String endereco = "";
                
                if (contador % 2 == 0) {
                    // Atribuindo a primeira posição do vetor de strings o byte lido convertido de decimal para binário
                    _16Bits[0] = String.format("%8s", Integer.toBinaryString(instrucao)).replace(' ', '0');

                } else if (contador % 2 != 0) {
                    // Atribuindo a segunda posição do vetor de strings o byte lido convertido de decimal para binário
                    _16Bits[1] = String.format("%8s", Integer.toBinaryString(instrucao)).replace(' ', '0');

                    for (int i = 0; i < 4; i++) {
                        // Adicionando os 4 primeiros bits de operação e os 4 primeiros bits de endereço as respectivas strings (opcodeS e endereco)
                        opcodeS += _16Bits[0].charAt(i);
                        endereco += _16Bits[0].charAt(i + 4);
                    }

                    for (int i = 0; i < _16Bits[1].length(); i++) {
                        // Adicionando o resto do endereço contido no segundo byte a string (endereco)
                        endereco += _16Bits[1].charAt(i);
                    }

                    // Convertendo a string de binários (opcodeS) em inteiro para ser usado como indice no vetor de strings (operacoesMarie)
                    opcode = Integer.parseInt(opcodeS, 2);

                    // Convertendo a string de binários (endereco) em inteiro em seguida para hexadecimal
                    endereco = Integer.toHexString(Integer.parseInt(endereco, 2));

                    // Verificando se a operação precisa de endereço
                    if (opcode == 5 || opcode == 6 || opcode == 7 || opcode == 10) {
                        System.out.println(operacoesMarie[opcode]);
                    } else {
                        System.out.println(operacoesMarie[opcode] + " " + endereco);
                    }
                }
                contador++;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}