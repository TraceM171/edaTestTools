/*
 * Decompiled with CFR 0.151.
 */
package aplicaciones.primitiva;

import aplicaciones.primitiva.ApuestaPrimitiva;
import aplicaciones.primitiva.NumeroPrimitiva;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import librerias.estructurasDeDatos.lineales.LEGListaConPIOrdenada;

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
        File eixida = new File("./aplicaciones/primitiva/Practica1.LabTests");
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
        System.out.println("  Si has terminado, sube el fichero \n  Practica.LabTests a PoliformaT\n");
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
                nota = LabTests.testLEGListaOrdenada();
                break;
            }
            case 2: {
                nota = LabTests.testNumeroPrimitiva();
                break;
            }
            case 3: {
                nota = LabTests.testApuestaPrimitiva();
                break;
            }
        }
        return nota;
    }

    private static double testLEGListaOrdenada() throws Exception {
        int i;
        double MAX_NOTA = EJER_PUNTOS[0];
        boolean res = true;
        int TALLA = 2500;
        LEGListaConPIOrdenada lista = new LEGListaConPIOrdenada();
        ArrayList<Integer> v = new ArrayList<Integer>();
        Random r = new Random();
        for (i = 1; i <= 2500; ++i) {
            int n = r.nextInt();
            v.add(n);
            lista.insertar((Object)n);
        }
        Collections.sort(v);
        if (lista.talla() != v.size()) {
            res = false;
        }
        if (res) {
            lista.inicio();
            i = 0;
            while (!lista.esFin() && ((Integer)v.get(i)).equals(lista.recuperar())) {
                lista.siguiente();
                ++i;
            }
            if (!lista.esFin()) {
                res = false;
            }
        }
        if (res) {
            return MAX_NOTA;
        }
        return 0.0;
    }

    private static double testNumeroPrimitiva() throws Exception {
        double MAX_NOTA = EJER_PUNTOS[1];
        boolean res = true;
        int TALLA = 2500;
        for (int i = 1; i <= 2500; ++i) {
            int y;
            int x;
            NumeroPrimitiva a = new NumeroPrimitiva();
            NumeroPrimitiva b = new NumeroPrimitiva();
            if (a.toString().equals(b.toString())) {
                if (!a.equals((Object)b)) {
                    res = false;
                }
                if (a.compareTo(b) == 0) continue;
                res = false;
                continue;
            }
            if (a.equals((Object)b)) {
                res = false;
            }
            if ((x = Integer.parseInt(a.toString())) < (y = Integer.parseInt(b.toString()))) {
                if (a.compareTo(b) < 0) continue;
                res = false;
                continue;
            }
            if (a.compareTo(b) > 0) continue;
            res = false;
        }
        if (res) {
            return MAX_NOTA;
        }
        return 0.0;
    }

    private static double testApuestaPrimitiva() throws Exception {
        boolean res;
        double MAX_NOTA = EJER_PUNTOS[2];
        boolean bl = res = LabTests.testApuestaPrimitiva(false) && LabTests.testApuestaPrimitiva(true);
        if (res) {
            return MAX_NOTA;
        }
        return 0.0;
    }

    private static boolean testApuestaPrimitiva(boolean ordenada) {
        int TALLA = 2500;
        for (int i = 0; i <= 2500; ++i) {
            ApuestaPrimitiva a = new ApuestaPrimitiva(ordenada);
            ArrayList<Integer> c = LabTests.obtenerCombinacion(a);
            if (c.size() != 6) {
                return false;
            }
            boolean[] v = new boolean[49];
            int prev = -1;
            for (int j = 0; j < c.size(); ++j) {
                int n = c.get(j) - 1;
                if (v[n]) {
                    return false;
                }
                v[n] = true;
                if (ordenada && n < prev) {
                    return false;
                }
                prev = n;
            }
        }
        return true;
    }

    private static ArrayList<Integer> obtenerCombinacion(ApuestaPrimitiva a) {
        ArrayList<Integer> c = new ArrayList<Integer>();
        String[] nums = a.toString().split(",");
        for (int i = 0; i < nums.length; ++i) {
            c.add(Integer.parseInt(nums[i].trim()));
        }
        return c;
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
        CAP = new String[]{"EDA GIA. Examen Pr\u00e1ctica 1. Curso 2020-21.", "EDA GII - First Lab Exam. Academic Year 2019-20."};
        ALUM = new String[]{"Alumno: ", "Student: "};
        ENTREGA = new String[]{"Calificado.", "Submitted."};
        PRUEBA = new String[]{"PRUEBA ", "TEST "};
        NO_AUT = new String[]{"*** Examen fuera de plazo o no autorizado.", "*** Unauthorized access or lab exam out of time."};
        EXC_TM = new String[]{"TIEMPO TEST EXCEDIDO: \u00bfBucle infinito? ", "Test Run Time Limit Exceeded: probable infinite loop. "};
        EXC = new String[]{"EXCEPCION: ", "EXCEPTION: "};
        ERR = new String[]{"ERROR: ", "ERROR: "};
        NO_METHOD = new String[]{"No existe el metodo que se quiere ejecutar.", "The method to be tested does not exist."};
        NOM_PRACT = new String[]{"Pr\u00e1ctica ", "Lab "};
        EJER_PRACT = new int[]{1, 1, 1};
        EJER_PUNTOS = new double[]{5.0E-4, 0.1, 0.212};
    }
}
