package com.strigalev.desktopweb.list;

import java.util.Arrays;

public class ListUsr<E> {
    private static long instancesCounter = 0;

    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private Object elements[];

    public ListUsr() {
        elements = new Object[DEFAULT_CAPACITY];
        instancesCounter++;
    }

    public void add(E e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    public void update(E e, int index) {
        elements[index] = e;
    }

    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public E get(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        return (E) elements[i];
    }

    public static long count() {
        return instancesCounter;
    }
}