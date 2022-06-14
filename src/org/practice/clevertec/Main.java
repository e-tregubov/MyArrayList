package org.practice.clevertec;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        MyList<String> list = new MyArrayList<>();

        for (int i = 1; i < 5; i++) { list.add("" + i); }
        print(list);

        MyIterator<String> iterator = list.getIterator();

        // методы итератора
        if (iterator.hasNext())
            iterator.next();
        iterator.addBefore("BEFORE");
        iterator.addAfter("AFTER");
        print(list);

        iterator.remove();
        print(list);


        // методы листа
        list.setMaxSize(3);
        print(list);

        ArrayList<String> list2 = new ArrayList<>();
        for (int i = 32; i < 41; i++) { list2.add("" + i); }
        list.addAll(list2.toArray());
        print(list);

        System.out.println(list.set(4,"INSERTED"));
        print(list);

        System.out.println(list.remove(11));
        print(list);

        System.out.println(list.find("INSERTED"));
        System.out.println(list.find("none"));
        System.out.println(list.get(0));

        list.set(5, null);
        list.set(8, null);
        print(list);

        list.trim();
        print(list);

        list.clear();
        print(list);

        list.trim();
        print(list);
    }


    static void print(MyList<String> list) {
        System.out.println();
        for (Object o : list.toArray()) { System.out.print(o + " "); }
        System.out.println("\nSize: " + list.size() + ", Capacity: " + list.capacity());
    }

}