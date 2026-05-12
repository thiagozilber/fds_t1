import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BarcaTest {
    private Barca barca;

    @Before
    public void setUp() {
        barca = new Barca();
    }

    // ------------------- Assentos Invalidos -------------------

    @Test
    public void testAssentoInvalido_FormatoInvalido() {
        assertEquals(0, barca.ocupaLugar("F1A1"));      // sem 0
        assertEquals(0, barca.ocupaLugar("F01A1"));     // curto de mais
        assertEquals(0, barca.ocupaLugar("Z01A01"));    // prefixo 1 errado
        assertEquals(0, barca.ocupaLugar("F01V01"));    // prefixo 2 errado
        assertEquals(0, barca.ocupaLugar("F01A001"));   // longo de mais
    }

    @Test
    public void testAssentoInvalido_FilaInvalida() {
        assertEquals(0, barca.ocupaLugar("F99A01"));    // fila inexistente
        assertEquals(0, barca.ocupaLugar("F00A01"));    // fila 0 != fila 1
        assertEquals(0, barca.ocupaLugar("F-1A01"));    // fila negativa
    }

    @Test
    public void testAssentoInvalido_AssentoInvalido() {
        assertEquals(0, barca.ocupaLugar("F01A99"));    // assento inexistente
        assertEquals(0, barca.ocupaLugar("F01A00"));    // assento 0 != assento 1
        assertEquals(0, barca.ocupaLugar("F01A-1"));    // assento negativo
    }

    // ------------------- Assento Ocupado -------------------

    @Test
    public void testAssentoOcupado() {
        barca.ocupaLugarSemVerificacao(1, 1);
        assertEquals(1, barca.ocupaLugar("F01A01"));
    }

    // ------------------- Distribuição de Peso -------------------

    @Test
    public void testPrimeiros100_Filas1a20Permitidas() {
        for (int i = 1; i <= 99; i++) {
            barca.ocupaLugarSemVerificacao(21, i % 20 + 1); // fila 21, invalida pros primeiros 100
        }
        assertEquals(3, barca.ocupaLugar("F01A01")); // passageiro 100, filas 1 - 20 devem ser permitidas
        assertEquals(3, barca.ocupaLugar("F20A01"));
    }

    @Test
    public void testPrimeiros100_Fila21Bloqueada() {
        assertEquals(2, barca.ocupaLugar("F21A01"));
    }

    @Test
    public void testPrimeiros100_Fila40Bloqueada() {
        assertEquals(2, barca.ocupaLugar("F40A01"));
    }

    @Test
    public void testProximos100_Filas40a60Permitidas() {
        for (int i = 1; i <= 100; i++) {
            barca.ocupaLugarSemVerificacao(1, i % 20 + 1); // fila 1, invalida pros proximos 100 
        }
        assertEquals(3, barca.ocupaLugar("F40A01")); // passageiro 101, filas 40 - 60 devem ser permitidas
        assertEquals(3, barca.ocupaLugar("F50A01"));
    }

    @Test
    public void testProximos100_Fila1Bloqueada() {
        for (int i = 1; i <= 100; i++) {
            barca.ocupaLugarSemVerificacao(60, i % 20 + 1);
        }
        assertEquals(2, barca.ocupaLugar("F01A01"));
    }

    @Test
    public void testPassageirosRestantes_QualquerFila() {
        for (int i = 1; i <= 200; i++) {
            barca.ocupaLugarSemVerificacao(60, i % 20 + 1);
        }
        assertEquals(3, barca.ocupaLugar("F01A01"));
        assertEquals(3, barca.ocupaLugar("F20A01"));
        assertEquals(3, barca.ocupaLugar("F21A01"));
        assertEquals(3, barca.ocupaLugar("F40A01"));
        assertEquals(3, barca.ocupaLugar("F59A01"));
    }

    // ------------------- Ocupação com Sucesso -------------------

    @Test
    public void testOcupacaoSucesso() {
        assertEquals(3, barca.ocupaLugar("F19A19")); // Sentou
        assertEquals(1, barca.ocupaLugar("F19A19")); // Ocupado
    }

    // ------------------- Ocupa sem Verificar -------------------

    @Test
    public void testOcupaLugarSemVerificacao() {
        barca.ocupaLugarSemVerificacao(21, 5);
        assertEquals(1, barca.ocupaLugar("F21A05"));
    }
}