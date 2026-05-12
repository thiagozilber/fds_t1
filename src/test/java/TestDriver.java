import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestDriver {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BarcaTest.class);

        System.out.println("======== Test Driver ========");
        System.out.println("Numero de testes: " + result.getRunCount());
        System.out.println("Falhas:           " + result.getFailureCount());
        System.out.println("Ignorados:        " + result.getIgnoreCount());
        System.out.println("Tempo:            " + result.getRunTime() + "ms");
        System.out.println("Exito:            " + result.wasSuccessful());

        if (result.getFailureCount() > 0) {
            System.out.println("\n --- Falhas ---");
            for (Failure failure: result.getFailures()) {
                System.out.println(failure.toString());
            }
        } else {
            System.out.println("\n Todos os testes passaram :)");
        }
    }
}