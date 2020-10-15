package com.sangis.corejava.utils;

import java.util.Iterator;

public class EmptyIterator<T>  implements Iterator<T> {
    public boolean hasNext() {
        return false;
    }

    public T next() {
        throw new UnsupportedOperationException();
    }

    public void remove() {
        throw new UnsupportedOperationException();

    }
}
