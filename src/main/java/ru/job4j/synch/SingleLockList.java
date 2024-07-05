package ru.job4j.synch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    private List<T> copy(List<T> origin) {
        return new ArrayList<>(origin);
    }
}