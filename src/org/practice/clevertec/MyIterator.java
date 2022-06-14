package org.practice.clevertec;

public interface MyIterator<E> {

    void next();

    boolean hasNext();

    default void remove() { throw new UnsupportedOperationException("remove"); }

    void addBefore(E e);

    void addAfter(E e);

}