package ru.job4j.synch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;
    private final Object lock = new Object();

    public SingleLockList(List<T> list) {
        synchronized (lock) {
            this.list = copy(list);
        }
    }

    public void add(T value) {
        synchronized (lock) {
            list.add(value);
        }
    }

    public T get(int index) {
        synchronized (lock) {
            return list.get(index);
        }
    }

    @Override
    public Iterator<T> iterator() {
        synchronized (lock) {
            return copy(list).iterator();
        }
    }

    private List<T> copy(List<T> origin) {
        synchronized (lock) {
            return new ArrayList<>(origin);
        }
    }
}