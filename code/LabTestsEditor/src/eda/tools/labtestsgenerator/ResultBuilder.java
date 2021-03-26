package eda.tools.labtestsgenerator;

/**
 *
 * @author trace
 */
public class ResultBuilder {

    private static double ejer(int ejer, ArrayList<Object> mensaje) {
        double nota = 0.0;
        nota = LabTests.exeBasTemp(10L, TimeUnit.SECONDS, ejer);
        System.out.println(ENTREGA[lang]);
        mensaje.add(PRUEBA[lang] + " ");
        mensaje.add(ejer);
        mensaje.add(": ");
        mensaje.add(nota);
        mensaje.add("\n");
        return nota;
    }

    private static void datosAlum() {
        alumno = System.getProperty("user.name");
        try {
            InetAddress localMachine = InetAddress.getLocalHost();
            pc = localMachine.getHostName();
        } catch (Exception e) {
            pc = "";
        }
    }B

}

/*
    private static double ejer(int ejer, ArrayList<Object> mensaje) {
        System.out.print("---> " + PRUEBA[lang] + ejer + " (" + NOM_PRACT[lang] + EJER_PRACT[ejer - 1] + "). ");
        double nota = 0.0;
        Object razonDelCero = "";
        try {
            nota = LabTests.exeBasTemp(10L, TimeUnit.SECONDS, ejer);
        }
        catch (Exception e) {
            razonDelCero = EXC[lang] + e + ". ";
            System.out.print(EXC[lang] + e + ". ");
        }
        catch (Error e) {
            razonDelCero = EXC[lang] + e + ". ";
            System.out.println(ERR[lang] + e + ". ");
        }
        System.out.println(ENTREGA[lang]);
        String res = "";
        if (!((String)razonDelCero).equals("")) {
            mensaje.add(razonDelCero);
        }
        mensaje.add(PRUEBA[lang] + " ");
        mensaje.add(ejer);
        mensaje.add(": ");
        mensaje.add(nota);
        mensaje.add("\n");
        return nota;
    }

 */

 /*
    private static void datosAlum() {
        alumno = System.getProperty("user.name");
        try {
            InetAddress localMachine = InetAddress.getLocalHost();
            pc = localMachine.getHostName();
        }
        catch (Exception e) {
            pc = "";
        }
    }

 */
