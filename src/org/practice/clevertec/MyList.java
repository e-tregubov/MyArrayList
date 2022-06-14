package org.practice.clevertec;

public interface MyList<E> {

    MyIterator<E> getIterator();

    void setMaxSize(int maxSize);

    void add(E e);

    void addAll(Object[] elements);

    E set(int index, E e);

    E remove(int index);

    void clear();

    int find(E e);

    E get(int index);

    Object[] toArray();

    int size();

    int capacity();

    void trim();

    void trimToSize();

}