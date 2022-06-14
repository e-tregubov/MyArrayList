package org.practice.clevertec;

import java.util.*;


public class MyArrayList<E> implements MyList<E> {

    private static final int DEFAULT_CAPACITY = 8;
    private static final Object[] EMPTY_ELEMENT_DATA = {};
    private static final Object[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};
    transient Object[] elementData;
    private int size;


    public MyArrayList() {
        this.elementData = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }


    // итератор
    public MyIterator<E> getIterator() { return new Itr(); }

    private class Itr implements MyIterator<E> {
        int cursor; // индекс следующего возвращаемого элемента
        int lastRet = -1; // индекс последнего возвращённого элемента; -1 если его не было

        public boolean hasNext() { return cursor != size; }

        public void next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            lastRet = i;
        }

        public void remove() { MyArrayList.this.remove(cursor); }

        public void addBefore(E e) {
            if (cursor == 0) throw new IndexOutOfBoundsException(outOfBoundsMsg(cursor));
            add(e, cursor - 1);
        }

        public void addAfter(E e) {
            if (hasNext())
                add(e, cursor + 1);
            else
                add(e);
        }
    }



    // установка максимальной ёмкости
    public void setMaxSize(int newCapacity) {
        if (newCapacity > 0) {
            this.elementData = Arrays.copyOf(elementData, newCapacity);
            if (size > newCapacity)
                this.size = newCapacity;
        } else if (newCapacity == 0) {
            this.elementData = EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + elementData.length);
        }
    }

    // добавление элемента в хвост
    public void add(E e) { add(e, elementData, size); }
    private void add(E e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
    }

    // вставка элемента по индексу
    public void add(E element, int index) {
        rangeCheckForAdd(index);
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.elementData).length)
            elementData = grow();
        System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        elementData[index] = element;
        size = s + 1;
    }

    // добавление массива элементов
    public void addAll(Object[] elements) {
        int numNew = elements.length;
        if (numNew == 0)
            return;
        Object[] elementData;
        final int s;
        if (numNew > (elementData = this.elementData).length - (s = size))
            elementData = grow(s + numNew);
        System.arraycopy(elements, 0, elementData, s, numNew);
        size = s + numNew;
    }

    // вставляет новый элемент по индексу, возвращает старый
    @SuppressWarnings("unchecked")
    public E set(int index, E e) {
        Objects.checkIndex(index, size);
        E oldValue = (E) elementData[index];
        elementData[index] = e;
        return oldValue;
    }

    // удаляет элемент по индексу и возвращает его
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        Objects.checkIndex(index, size);
        final Object[] es = elementData;
        E oldValue = (E) es[index];
        fastRemove(es, index);
        return oldValue;
    }

    // "зануляет" все элементы
    public void clear() {
        final Object[] es = elementData;
        for (int to = size, i = size = 0; i < to; i++)
            es[i] = null;
    }

    // возвращает индекс элемента, -1 если его нет
    public int find(E e) {
        Object[] es = elementData;
        if (e == null) {
            for (int i = 0; i < size; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (e.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    // возвращает элемент по индексу
    @SuppressWarnings("unchecked")
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) elementData[index];
    }

    // возвращает массив элементов
    public Object[] toArray() { return Arrays.copyOf(elementData, elementData.length); }

    public int size() { return size; }
    public int capacity() { return elementData.length; }

    // удаляет null-элементы
    public void trim() {
        final Object[] newElementData = new Object[size];
        int n = 0;
        for (int i = 0; i < size; i++) {
            Object o = elementData[i];
            if (o != null) newElementData[n++] = o;
        }
        this.elementData = newElementData;
        this.size = n;
        trimToSize();
    }

    // урезает ёмкость под размер
    public void trimToSize() {
        if (size < elementData.length) {
            elementData = (size == 0)
                    ? EMPTY_ELEMENT_DATA
                    : Arrays.copyOf(elementData, size);
        }
    }

    @Override
    public String toString() { return Arrays.toString(elementData); }




    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > this.elementData.length)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) { return "Index: "+index+", Size: "+this.size; }

    private Object[] grow() { return grow(size + 1); }

    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        if (oldCapacity > 0 || elementData != DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
            int newCapacity = newLength(oldCapacity,
                    minCapacity - oldCapacity, /* minimum growth */
                    oldCapacity >> 1           /* preferred growth */);
            return elementData = Arrays.copyOf(elementData, newCapacity);
        } else {
            return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

    private int newLength(int oldLength, int minGrowth, int prefGrowth) {
        int prefLength = oldLength + Math.max(minGrowth, prefGrowth); // might overflow
        if (0 < prefLength && prefLength <= Integer.MAX_VALUE - 8) {
            return prefLength;
        } else {
            // put code cold in a separate method
            return hugeLength(oldLength, minGrowth);
        }
    }

    private static int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) { // overflow
            throw new OutOfMemoryError(
                    "Required array length " + oldLength + " + " + minGrowth + " is too large");
        } else return Math.max(minLength, Integer.MAX_VALUE - 8);
    }

    private void fastRemove(Object[] es, int i) {
        final int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(es, i + 1, es, i, newSize - i);
        es[size = newSize] = null;
    }

}