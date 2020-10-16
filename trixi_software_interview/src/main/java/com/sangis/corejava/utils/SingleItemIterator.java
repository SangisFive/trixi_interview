package com.sangis.corejava.utils;

import java.util.Iterator;

public class SingleItemIterator<T> implements Iterator<T> {
    private boolean taken = false;
    private final T item;
    public SingleItemIterator(T item){
        this.item= item;
    }
    @Override
    public boolean hasNext() {
        return !taken;
    }

    @Override
    public T next() {
        taken = true;
        return item;
    }
}
