//import java.util.ArrayList;
//import java.util.ConcurrentModificationException;
//import java.util.NoSuchElementException;
//
//public class List implements MyIterator<E> {
//    int cursor;       // индекс следущего возвращаемого элемента
//    int lastRet = -1; // индекс последнего возвращённого, -1 если его нет
//
//    // prevent creating a synthetic constructor
//    Itr() {}
//
//    public boolean hasNext() {
//        return cursor != size;
//    }
//
//    @SuppressWarnings("unchecked")
//    public E next() {
//        checkForComodification();
//        int i = cursor;
//        if (i >= size)
//            throw new NoSuchElementException();
//        Object[] elementData = ArrayList.this.elementData;
//        if (i >= elementData.length)
//            throw new ConcurrentModificationException();
//        cursor = i + 1;
//        return (E) elementData[lastRet = i];
//    }
//
//    public void remove() {
//        if (lastRet < 0)
//            throw new IllegalStateException();
//        checkForComodification();
//
//        try {
//            ArrayList.this.remove(lastRet);
//            cursor = lastRet;
//            lastRet = -1;
//            expectedModCount = modCount;
//        } catch (IndexOutOfBoundsException ex) {
//            throw new ConcurrentModificationException();
//        }
//    }
//
//    @Override
//    public void addBefore(E e) {
//
//    }
//
//    @Override
//    public void addAfter(E e) {
//
//    }
//}