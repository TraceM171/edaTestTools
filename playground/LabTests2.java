/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  librerias.util.Ordenacion
 */
package librerias.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import librerias.util.Ordenacion;

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
        File eixida = new File("./librerias/util/Practica2.LabTests");
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
        System.out.println("  Si has terminado, sube el fichero \n  Practica2.LabTests a PoliformaT\n");
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
                nota = LabTests.testMerge2();
                break;
            }
            case 2: {
                nota = LabTests.testMergeSort2();
                break;
            }
        }
        return nota;
    }

    private static double testMerge2() throws Exception {
        String metodo = "merge2";
        String clase = "librerias.util.Ordenacion";
        double nota = 0.0;
        Method m = LabTests.buscarMetodo(Class.forName(clase), metodo);
        if (m == null) {
            System.out.println(NO_METHOD[lang]);
        } else {
            nota = LabTests.compruebaMerge2(m);
        }
        return nota;
    }

    private static double compruebaMerge2(Method m) throws Exception {
        double MAX_NOTA = EJER_PUNTOS[0];
        int TALLA = 66666;
        Object[] a1 = LabTests.crearAleatorioInteger(66666);
        Arrays.sort(a1);
        Object[] a2 = LabTests.crearAleatorioInteger(66666);
        Arrays.sort(a2);
        Comparable[] c = (Comparable[])m.invoke(null, a1, a2);
        boolean res = true;
        for (int i = 1; i < c.length && res; ++i) {
            res = c[i - 1].compareTo(c[i]) <= 0;
        }
        boolean bl = res = res && c.length == a1.length + a2.length;
        if (res) {
            return MAX_NOTA;
        }
        return 0.0;
    }

    private static double testMergeSort2() {
        double MAX_NOTA = EJER_PUNTOS[1];
        int TALLA = 666666;
        Object[] a1 = LabTests.crearAleatorioInteger(666666);
        Comparable[] a2 = Arrays.copyOf(a1, a1.length);
        Arrays.sort(a1);
        Ordenacion.mergeSort2((Comparable[])a2);
        boolean res = LabTests.sonIguales((Comparable[])a1, (Comparable[])a2);
        if (res) {
            return MAX_NOTA;
        }
        return 0.0;
    }

    private static Integer[] crearAleatorioInteger(int talla) {
        Integer[] aux = new Integer[talla];
        for (int i = 0; i < aux.length; ++i) {
            aux[i] = (int)(Math.random() * (double)(10 * talla));
        }
        return aux;
    }

    private static <T extends Comparable<T>> boolean sonIguales(T[] a, T[] b) {
        boolean iguales = true;
        if (a.length != b.length) {
            iguales = false;
        } else {
            for (int i = 0; i < a.length && iguales; ++i) {
                iguales = a[i].compareTo(b[i]) == 0;
            }
        }
        return iguales;
    }

    private static Method buscarMetodo(Class clase, String nombre) {
        AccessibleObject m = null;
        Method[] methods = clase.getDeclaredMethods();
        for (int i = 0; i < methods.length && m == null; ++i) {
            if (!methods[i].getName().equalsIgnoreCase(nombre)) continue;
            m = methods[i];
        }
        if (m != null) {
            m.setAccessible(true);
        }
        return m;
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
        CAP = new String[]{"EDA GII. Examen Pr\u00e1ctica 2. Curso 2020-21.", "EDA GII - First Lab Exam. Academic Year 2019-20."};
        ALUM = new String[]{"Alumno: ", "Student: "};
        ENTREGA = new String[]{"Calificado.", "Submitted."};
        PRUEBA = new String[]{"PRUEBA ", "TEST "};
        NO_AUT = new String[]{"*** Examen fuera de plazo o no autorizado.", "*** Unauthorized access or lab exam out of time."};
        EXC_TM = new String[]{"TIEMPO TEST EXCEDIDO: \u00bfBucle infinito? ", "Test Run Time Limit Exceeded: probable infinite loop. "};
        EXC = new String[]{"EXCEPCION: ", "EXCEPTION: "};
        ERR = new String[]{"ERROR: ", "ERROR: "};
        NO_METHOD = new String[]{"No existe el metodo que se quiere ejecutar.", "The method to be tested does not exist."};
        NOM_PRACT = new String[]{"Pr\u00e1ctica ", "Lab "};
        EJER_PRACT = new int[]{2, 2};
        EJER_PUNTOS = new double[]{5.0E-4, 0.312};
    }
}
