package com.jacop.test.driven.design.tt;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Unboxing {

    /*
     Unboxing/Autoboxing, Java’da primitive tiplerin otomatik olarak
     wrapper sınıflarına dönüştürülmesi (ve tam tersi) işlemidir.
     Bu kolaylık sağlasa da performans ve bellek açısından bazı riskler doğurur:
     */


    public static void main(String[] args) {
        Integer x = 127;
        Integer y = 127;
        System.out.println("x==y: " + (x == y));
        System.out.println("x.equals(y): " + (x.equals(y)));

        Integer a = 128;
        Integer b = 128;
        System.out.println("a==b: " + (a == b));
        System.out.println("a.equals(b): " + (a.equals(b)));

        int c = 128;
        int d = 128;
        System.out.println("c==d: " + (c == d));

        System.out.println("a==c: " + (a == c));

        // npe
        try {
            Integer n = null;
            int npe = n;
        } catch (NullPointerException e) {
            System.out.println("u r in npe catched block");
        }

        System.out.println("-----------------------------------------------------------------------------");

        // outboxing-boxing example

        Integer o = Integer.valueOf(2); // primitive i Object e paketledim(boxing)
        int unboxing = o.intValue();  // an object unboxed to primitive

        // benzer şekilde işlemi java ya yaptıralım
        int number = 1; //our primitive type
        Integer boxing = number; // otomatikman boxing yapılır

        // Wrapper’dan primitive’e dönüşüm (Integer → int) ek CPU maliyeti getirir.
        // Wrapper tipler null olabilir, unboxing sırasında NPE oluşabilir. (bkz. npe)

        // pratik yanlış kullanım örneği. ortalama 240 ms
        long start = System.nanoTime();

        Long sum = 0L; // wrapper kullanımı
        for (long i = 0; i < 100_000_000L; i++) {
            sum += i; //her  iterasyon da i sum = (sum + i) işleminde unboxing yapıyor.
        }

        long end = System.nanoTime();
        System.out.println("sum = " + sum);
        long ns = end - start;
        long millisecond = TimeUnit.NANOSECONDS.toMillis(ns);
        System.out.println("time = " + millisecond);

        // pratik doğru kullanım örneği.  ortalama 35 ms
        long start2 = System.nanoTime();

        long sum2 = 0L;
        for (long i = 0; i < 100_000_000L; i++) {
            sum2 += i;
        }

        long end2 = System.nanoTime();
        System.out.println("sum2 = " + sum2);
        long duration = end2 - start2;
        long millis = TimeUnit.NANOSECONDS.toMillis(duration);
        System.out.println("time2 = " + millis);

        // List<Integer> üzerinde çok sayıda ilave/çıkarma yaparken
        // primitif int dizisi yerine List<Integer> kullanmanın dezavantajlarını açıklayınız.
        /*
         * 1. Autoboxing Maliyeti
         *  int → Integer dönüşümü her eklemede boxing yapar.
         *  Integer → int dönüşümü her okumada unboxing yapar.
         *  Bu dönüşümler CPU üzerinde ekstra yük oluşturur.
         */

        /*
         * 2. Heap Allocation ve Bellek Kullanımı
         *
         * int primitive 4 byte iken, Integer nesnesi yaklaşık 16 byte (header + value + padding).
         * 1 milyon eleman için:
         *
         * int[] ≈ 4 MB
         * List<Integer> ≈ 16 MB + liste yapısının overhead’i (ArrayList içindeki referanslar + Integer nesneleri).
         */

        int size = 10_000_000;
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        // int[] testi
        long beforeArray = runtime.totalMemory() - runtime.freeMemory();
        int[] intArr = new int[size]; // memory usage 40 MB
        long afterArray = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("int[] memory usage :" + (afterArray - beforeArray) / (1024 * 1024) + " MB");

        runtime.gc();
        beforeArray = runtime.totalMemory() - runtime.freeMemory();
        List<Integer> intList = new ArrayList<>(); // memory usage 255 MB
        for (int i = 0; i < size; i++) {
            intList.add(i);
        }
        afterArray = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("List<Integer> memory usage :" + (afterArray - beforeArray) / (1024 * 1024) + " MB");

        /*
         * 3. Garbage Collector Yükü
         * Her ekleme yeni bir Integer nesnesi oluşturur.
         * Çok sayıda ekleme/çıkarma → heap fragmentasyonu ve GC baskısı.
         */

        /* 4. Null Riski
         * List<Integer> null değer tutabilir, bu da unboxing sırasında NullPointerException riski doğurur.
         * int[] için böyle bir risk yok.
         */

        /*
         * 5. Ekleme/Çıkarma Karmaşıklığı
         * ArrayList içindeki ekleme/çıkarma işlemleri O(n) maliyetlidir (eleman kaydırma).
         * Primitive dizide doğrudan index erişimi daha hızlıdır.
         */
        System.out.println("----------------------------------------------------------------");
        System.gc();
        beforeArray = runtime.totalMemory() - runtime.freeMemory();
        start = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i); // boxing
        }
        end = System.currentTimeMillis();
        afterArray = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("List<Integer> memory usage :" + (afterArray - beforeArray) / (1024 * 1024) + " MB");
        System.out.println("List<Integer> add time: " + (end - start) + "ms");

        System.gc();
        beforeArray = runtime.totalMemory() - runtime.freeMemory();
        start = System.currentTimeMillis();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;// no boxing
        }
        end = System.currentTimeMillis();
        afterArray = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("int[] memory usage :" + (afterArray - beforeArray) / (1024 * 1024) + " MB");
        System.out.println("int[] add time: " + (end - start)  + "ms");

        System.gc();
        // == referans kontrolü yaparsınız, .equals() ile içerik kontrolü yapılır ancak toString() bağlamında
        // == primitive lerde değer karşılaştırır ancak Integer gibi wrapper class da referans karşılaştırır.

        System.out.println("-----------------------  EQUALS AND (==) -------------------------------");

        Integer t = 1024;
        Integer z = 1024;
        System.out.println("Integer -> t == z "+ (t == z));
        System.out.println("Integer -> t.equals(z) " + (t.equals(z)));

        int t1 = 1024;
        int z1 = 1024;
        System.out.println("int -> t1 == z1 "+ (t1 == z1));
        System.out.println("int -> t1.equals(z1) gibi bir method yok primitive ler de  ");

        System.out.println("unboxing -> t == t1 "+ (t1 == t));
        System.out.println("unboxing -> t.equals(t1) "+ (t.equals(t1)));

        System.out.println("------------------ AOP vs AspectJ ---------------------------------------");

        /* Yaklaşım ve Kapsam
         *** Spring AOP
                - proxy tabanlıdır.
                - Sadece Spring konteyneri tarafından yönetilen bean' ler üzerinden çalışır.
                - Method level interception yapar(join point = method execution)
                - Daha basit, Spring uygulamaları için yeterli

         *** AspectJ
                - Compile-time, load-time veya runtime weaving yapabilir.
                * Proxy tabanlı değil bytecode seviyesinde çalışır
                * Çok daha geniş kapsamlıdır. method constructor, field access, static initializer gibi daha fazla join point destekler
                * Spring dışındaki sınıflara da uygulanabilir.
         */

    }

}
