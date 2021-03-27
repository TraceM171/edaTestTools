/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  aplicaciones.biblioteca.BuscadorDeLaBibl
 *  aplicaciones.biblioteca.Termino
 *  librerias.estructurasDeDatos.modelos.ListaConPI
 */
package aplicaciones.biblioteca;

import aplicaciones.biblioteca.BuscadorDeLaBibl;
import aplicaciones.biblioteca.Termino;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import librerias.estructurasDeDatos.modelos.ListaConPI;

public class LabTests {
    private static ObjectOutputStream salida = null;
    private static String alumno;
    private static String pc;
    private static boolean verb;
    private static final int CAS = 0;
    private static final int ENG = 1;
    private static final int turno = 0;
    private static int lang;
    private static final int TIME_OUT = 10;
    private static final double MIN_NOTA = 0.0;
    private static final String[] CAP;
    private static final String LIN = "========================================================";
    private static final String[] ALUM;
    private static final String[] ENTREGA;
    private static final String[] PRUEBA;
    private static final String[] NO_AUT;
    private static final String[] EXC_TM;
    private static final String[] EXC;
    private static final String[] ERR;
    private static final String[] NO_METHOD;
    private static final String[] NOM_PRACT;
    private static final int[] EJER_PRACT;
    private static final double[] EJER_PUNTOS;

    private LabTests() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main() {
        ArrayList<Object> mensaje = new ArrayList<Object>();
        String notaEnEjer = "";
        double notaLabTests = 0.0;
        int numEjer = EJER_PRACT.length;
        for (int i = 1; i <= numEjer; ++i) {
            notaLabTests += LabTests.ejer(i, mensaje);
        }
        LabTests.datosAlum();
        Instant ahora = Instant.ofEpochMilli(System.currentTimeMillis() + 3600000L);
        File eixida = new File("./aplicaciones/biblioteca/Practica3S1.LabTests");
        String path = eixida.getAbsolutePath();
        try {
            salida = new ObjectOutputStream(new FileOutputStream(eixida));
            LabTests.salidaPantalla("\n" + alumno + " " + pc + "\n");
            salida.writeObject(alumno + " " + pc + "\n");
            LabTests.salidaPantalla(path + "\n\n");
            salida.writeObject(path + "\n\n");
            for (Object s : mensaje) {
                LabTests.salidaPantalla(s);
                salida.writeObject(s);
            }
            salida.writeObject("\n");
            LabTests.salidaPantalla("\n" + notaLabTests + "\n\n");
            salida.writeObject("->");
            salida.writeObject(notaLabTests);
            salida.writeObject("<-\n");
            salida.writeObject("\n");
            LabTests.salidaPantalla(ahora + "\n\n");
            salida.writeObject(ahora);
            salida.writeObject("\n");
        }
        catch (IOException e) {
            System.out.println("El fichero no existe o no se puede crear");
            notaLabTests = 0.0;
        }
        finally {
            try {
                if (salida != null) {
                    salida.close();
                }
            }
            catch (IOException e) {
                System.out.println("Fichero no cerrado correctamente");
                notaLabTests = 0.0;
            }
        }
        System.out.println();
        System.out.println("  Si has terminado, sube el fichero \n  Practica3S1.LabTests a PoliformaT\n");
    }

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

    private static Double exeBasTemp(long timeout, TimeUnit unit, int ejer) throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Double nota = 0.0;
        try {
            Future<Double> f = service.submit(() -> LabTests.exeBas(ejer));
            nota = f.get(timeout, unit);
        }
        catch (TimeoutException e) {
            System.out.print(EXC_TM[lang]);
            service.shutdown();
        }
        return nota;
    }

    private static double exeBas(int ejer) throws Exception {
        double nota = 0.0;
        switch (ejer) {
            case 1: {
                nota = LabTests.testTermino();
                break;
            }
            case 2: {
                nota = LabTests.testHapax();
                break;
            }
        }
        return nota;
    }

    private static double testTermino() {
        boolean ok;
        double MAX_NOTA = EJER_PUNTOS[0];
        Termino saco1 = new Termino("saco", 1);
        Termino saco31 = new Termino("saco", 31);
        Termino saco4 = new Termino("saco", 4);
        boolean bl = ok = saco1.hashCode() == 422 && saco31.hashCode() == 3522362 && saco4.hashCode() == 9419;
        if (ok) {
            Termino asco1 = new Termino("asco", 1);
            Termino asco31 = new Termino("asco", 31);
            Termino asco4 = new Termino("asco", 4);
            boolean bl2 = ok = asco1.hashCode() == 422 && asco31.hashCode() == 3003422 && asco4.hashCode() == 8555;
            if (ok) {
                boolean noIguales1 = saco1.equals((Object)asco1);
                boolean noIguales31 = saco31.equals((Object)asco31);
                boolean noIguales4 = saco4.equals((Object)asco4);
                boolean iguales1 = saco1.equals((Object)saco1);
                boolean iguales1Reves = asco1.equals((Object)asco1);
                boolean iguales4 = saco4.equals((Object)saco4);
                boolean iguales4Reves = asco4.equals((Object)asco4);
                boolean bl3 = ok = !noIguales1 && !noIguales31 && !noIguales4 && iguales1 && iguales1Reves && iguales4 && iguales4Reves;
            }
        }
        if (ok) {
            return MAX_NOTA;
        }
        return 0.0;
    }

    private static double testHapax() {
        double MAX_NOTA = EJER_PUNTOS[1];
        boolean ok = true;
        try {
            BuscadorDeLaBibl.verb = false;
            BuscadorDeLaBibl buscador = new BuscadorDeLaBibl();
            ListaConPI lpi = buscador.hapax();
            String lpiSt = lpi.toString();
            ok = lpi.talla() == 10126 && lpiSt.indexOf("ambages") == 348 && lpiSt.indexOf("troncoso") == 1374 && lpiSt.indexOf("alimenta") == 43210;
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
            return 0.0;
        }
        if (ok) {
            return MAX_NOTA;
        }
        return 0.0;
    }

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

    private static void salidaPantalla(Object x) {
        if (verb) {
            System.out.print(x);
        } else {
            System.out.print(".");
        }
    }

    static {
        verb = false;
        lang = 0;
        CAP = new String[]{"EDA GII. Examen Pr\u00e1ctica 3 - S1. Curso 2020-21.", "EDA GII - First Lab Exam. Academic Year 2019-20."};
        ALUM = new String[]{"Alumno: ", "Student: "};
        ENTREGA = new String[]{"Calificado.", "Submitted."};
        PRUEBA = new String[]{"PRUEBA ", "TEST "};
        NO_AUT = new String[]{"*** Examen fuera de plazo o no autorizado.", "*** Unauthorized access or lab exam out of time."};
        EXC_TM = new String[]{"TIEMPO TEST EXCEDIDO: \u00bfBucle infinito? ", "Test Run Time Limit Exceeded: probable infinite loop. "};
        EXC = new String[]{"EXCEPCION: ", "EXCEPTION: "};
        ERR = new String[]{"ERROR: ", "ERROR: "};
        NO_METHOD = new String[]{"No existe el metodo que se quiere ejecutar.", "The method to be tested does not exist."};
        NOM_PRACT = new String[]{"Pr\u00e1ctica ", "Lab "};
        EJER_PRACT = new int[]{3, 3};
        EJER_PUNTOS = new double[]{5.0E-4, 0.312};
    }
}
