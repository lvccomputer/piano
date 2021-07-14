package com.yyyyy.piano;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public class UtilsMusic {
    public static SparseArray<String> soundArrayList = new SparseArray<>();
    public static Map<String, Integer> list = new HashMap<>();

    static {
        soundArrayList.put(1, "A-2");
        soundArrayList.put(2, "B-2");
        soundArrayList.put(3, "C-1");
        soundArrayList.put(4, "D-1");
        soundArrayList.put(5, "E-1");
        soundArrayList.put(6, "F-1");
        soundArrayList.put(7, "G-1");

        soundArrayList.put(8, "A-1");
        soundArrayList.put(9, "B-1");
        soundArrayList.put(10, "C-0");
        soundArrayList.put(11, "D-0");
        soundArrayList.put(12, "E-0");
        soundArrayList.put(13, "F-0");
        soundArrayList.put(14, "G-0");


        soundArrayList.put(15, "A-0");
        soundArrayList.put(16, "B-0");
        soundArrayList.put(17, "c");
        soundArrayList.put(18, "d");
        soundArrayList.put(19, "e");
        soundArrayList.put(20, "f");
        soundArrayList.put(21, "g");

        soundArrayList.put(22, "a");
        soundArrayList.put(23, "b");
        soundArrayList.put(24, "c1");
        soundArrayList.put(25, "d1");
        soundArrayList.put(26, "e1");
        soundArrayList.put(27, "f1");
        soundArrayList.put(28, "g1");

        soundArrayList.put(29, "a1");
        soundArrayList.put(30, "b1");
        soundArrayList.put(31, "c2");
        soundArrayList.put(32, "d2");
        soundArrayList.put(33, "e2");
        soundArrayList.put(34, "f2");
        soundArrayList.put(35, "g2");

        soundArrayList.put(36, "a2");
        soundArrayList.put(37, "b2");
        soundArrayList.put(38, "c3");
        soundArrayList.put(39, "d3");
        soundArrayList.put(40, "e3");
        soundArrayList.put(41, "f3");
        soundArrayList.put(42, "g3");

        soundArrayList.put(43, "a3");
        soundArrayList.put(44, "b3");
        soundArrayList.put(45, "c4");
        soundArrayList.put(46, "d4");
        soundArrayList.put(47, "e4");
        soundArrayList.put(48, "f4");
        soundArrayList.put(49, "g4");

        soundArrayList.put(50, "a4");
        soundArrayList.put(51, "b4");
        soundArrayList.put(52, "c5");

        soundArrayList.put(53, "A-2#");

        soundArrayList.put(54, "C-1#");
        soundArrayList.put(55, "D-1#");

        soundArrayList.put(56, "F-1#");
        soundArrayList.put(57, "G-1#");
        soundArrayList.put(58, "A-1#");

        soundArrayList.put(59, "C-0#");
        soundArrayList.put(60, "D-0#");

        soundArrayList.put(61, "F-0#");
        soundArrayList.put(62, "G-0#");
        soundArrayList.put(63, "A-0#");

        soundArrayList.put(64, "c#");
        soundArrayList.put(65, "d#");

        soundArrayList.put(66, "f#");
        soundArrayList.put(67, "g#");
        soundArrayList.put(68, "a#");

        soundArrayList.put(69, "c1#");
        soundArrayList.put(70, "d1#");

        soundArrayList.put(71, "f1#");
        soundArrayList.put(72, "g1#");
        soundArrayList.put(73, "a1#");

        soundArrayList.put(74, "c2#");
        soundArrayList.put(75, "d2#");

        soundArrayList.put(76, "f2#");
        soundArrayList.put(77, "g2#");
        soundArrayList.put(78, "a2#");

        soundArrayList.put(79, "c3#");
        soundArrayList.put(80, "d3#");

        soundArrayList.put(81, "f3#");
        soundArrayList.put(82, "g3#");
        soundArrayList.put(83, "a3#");

        soundArrayList.put(84, "c4#");
        soundArrayList.put(85, "d4#");

        soundArrayList.put(86, "f4#");
        soundArrayList.put(87, "g4#");
        soundArrayList.put(88, "a4#");


    }

    static {
        list.put("M", 0);
        list.put("A-2", 1);
        list.put("B-2", 2);
        list.put("C-1", 3);
        list.put("D-1", 4);
        list.put("E-1", 5);
        list.put("F-1", 6);
        list.put("G-1", 7);

        list.put("A-1", 8);
        list.put("B-1", 9);
        list.put("C-0", 10);
        list.put("D-0", 11);
        list.put("E-0", 12);
        list.put("F-0", 13);
        list.put("G-0", 14);


        list.put("A-0", 15);
        list.put("B-0", 16);
        list.put("c", 17);
        list.put("d", 18);
        list.put("e", 19);
        list.put("f", 20);
        list.put("g", 21);

        list.put("a", 22);
        list.put("b", 23);
        list.put("c1", 24);
        list.put("d1", 25);
        list.put("e1", 26);
        list.put("f1", 27);
        list.put("g1", 28);

        list.put("a1", 29);
        list.put("b1", 30);
        list.put("c2", 31);
        list.put("d2", 32);
        list.put("e2", 33);
        list.put("f2", 34);
        list.put("g2", 35);

        list.put("a2", 36);
        list.put("b2", 37);
        list.put("c3", 38);
        list.put("d3", 39);
        list.put("e3", 40);
        list.put("f3", 41);
        list.put("g3", 42);

        list.put("a3", 43);
        list.put("b3", 44);
        list.put("c4", 45);
        list.put("d4", 46);
        list.put("e4", 47);
        list.put("f4", 48);
        list.put("g4", 49);

        list.put("a4", 50);
        list.put("b4", 51);
        list.put("c5", 52);

        list.put("A-2#", 53);

        list.put("C-1#", 54);
        list.put("D-1#", 55);

        list.put("F-1#", 56);
        list.put("G-1#", 57);
        list.put("A-1#", 58);

        list.put("C-0#", 59);
        list.put("D-0#", 60);

        list.put("F-0#", 61);
        list.put("G-0#", 62);
        list.put("A-0#", 63);

        list.put("c#", 64);
        list.put("d#", 65);

        list.put("f#", 66);
        list.put("g#", 67);
        list.put("a#", 68);

        list.put("c1#", 69);
        list.put("d1#", 70);

        list.put("f1#", 71);
        list.put("g1#", 72);
        list.put("a1#", 73);

        list.put("c2#", 74);
        list.put("d2#", 75);

        list.put("f2#", 76);
        list.put("g2#", 77);
        list.put("a2#", 78);

        list.put("c3#", 79);
        list.put("d3#", 80);

        list.put("f3#", 81);
        list.put("g3#", 82);
        list.put("a3#", 83);

        list.put("c4#", 84);
        list.put("d4#", 85);

        list.put("f4#", 86);
        list.put("g4#", 87);
        list.put("a4#", 88);
    }

}
