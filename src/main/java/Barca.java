import java.util.HashMap;
import java.util.Map;

public class Barca {
    private Map<String, Boolean> assentos;
    public int ocupacao;

    public Barca() {
        assentos = new HashMap<>();
        for (int i = 1; i <= 60; i++) {
            for (int j = 1; j <= 20; j++) {
                String seat = String.format("F%02dA%02d", i, j);
                assentos.put(seat, false);
            }
        }
        ocupacao = 0;
    }

    private int[] extraiNumeros(String assento) {
        int fileira = Integer.parseInt(assento.substring(1, 3));
        int lugar = Integer.parseInt(assento.substring(4, 6));
        return new int[]{fileira, lugar};
    }

    private boolean validaAssento(String assento) {
        if (assento == null || assento.length() != 6) return false;
        if (assento.charAt(0) != 'F' || assento.charAt(3) != 'A') return false;
        int[] nums = extraiNumeros(assento);
        int fileira = nums[0];
        int lugar = nums[1];
        return (fileira >= 1 && fileira <= 60) && (lugar >= 1 && lugar <= 20);
    }

    private int preencheAssento(String assento) {
        assentos.put(assento, true);
        ocupacao++;
        return 3;
    }

    /**
     * Occupies a seat following all business rules.
     *
     * @param assento the seat identifier (e.g., "F02A12")
     * @return 0 – assento invalido
     *         1 – assento ocupado
     *         2 – assento bloqueado devido a regras de distribuição de peso
     *         3 – assento OK
     */
    public int ocupaLugar(String assento) {
        if (!validaAssento(assento)) {
            return 0;
        }
        if (assentos.get(assento)) {
            return 1;
        }

        int fileira = extraiNumeros(assento)[0];

        // Weight distribution rules
        if (ocupacao < 100 && !(fileira >= 1 && fileira <= 20)) {
            return 2;
        }
        // correção do erro: if não chevaga se ocupacao era >= 100, então sempre disparava pra fileiras menores que 40 com ocupacao < 200
        if (ocupacao < 200 && ocupacao >= 100 && !(fileira >= 40 && fileira <= 60)) {
            return 2;
        }

        if (ocupacao < 1200) {
            return preencheAssento(assento);
        }

        // Em tese esse retorno nunca vai acontecer, mas melhor ter ele do que não
        return 1;
    }

    /**
     * 
     * Somente para testes
     *
     * @param fila    int (1‑60)
     * @param assento int (1‑20)
     */
    public void ocupaLugarSemVerificacao(int fila, int assento) {
        String chave = String.format("F%02dA%02d", fila, assento);
        preencheAssento(chave);
    }
}