/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2012 - Javolution (http://javolution.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.javolution.util.internal.map;

import java.util.Map;

import org.javolution.util.AbstractMap;
import org.javolution.util.FastIterator;
import org.javolution.util.function.Equality;
import org.javolution.util.function.Order;

/**
 * An a shared view over a map.
 */
public final class AtomicMapImpl<K, V> extends AbstractMap<K, V> {

    private static final long serialVersionUID = 0x700L; // Version.
    private final AbstractMap<K, V> inner;
    private volatile AbstractMap<K, V> innerConst; // The copy used by readers.

    public AtomicMapImpl(AbstractMap<K, V> inner) {
        this.inner = inner;
        this.innerConst = inner.clone();
    }
 
    @Override
    public synchronized void clear() {
        inner.clear();
        innerConst = inner.clone();
    }

    @Override
    public AtomicMapImpl<K, V> clone() {
        return new AtomicMapImpl<K, V>(innerConst.clone());
    }

    @Override
    public Order<? super K> keyOrder() {
        return innerConst.keyOrder();
    }

    @Override
    public boolean containsKey(Object key) {
        return innerConst.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return innerConst.containsValue(value);
    }

    @Override
    public FastIterator<Entry<K, V>> descendingIterator() {
        return innerConst.descendingIterator();
    }

    @Override
    public FastIterator<Entry<K, V>> descendingIterator(K fromKey) {
        return innerConst.descendingIterator(fromKey);
    }

    @Override
    public boolean equals(Object obj) {
        return innerConst.equals(obj);
    }

    @Override
    public K firstKey() {
        return innerConst.firstKey();
    }

    @Override
    public V get(Object key) {
        return innerConst.get(key);
    }

    @Override
    public Entry<K, V> getEntry(K key) {
        return innerConst.getEntry(key);
    }

    @Override
    public int hashCode() {
        return innerConst.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return innerConst.isEmpty();
    }

    @Override
    public FastIterator<Entry<K, V>> iterator() {
        return innerConst.iterator();
    }

    @Override
    public FastIterator<Entry<K, V>> iterator(K fromKey) {
        return innerConst.iterator(fromKey);
    }

    @Override
    public K lastKey() {
        return innerConst.lastKey();
    }


    @Override
    public synchronized V put(K key, V value) {
        V previous = inner.put(key, value);
        innerConst = inner.clone();
        return previous;
    }

    @Override
    public synchronized boolean addEntry(Entry<K, V> entry) {
        if (!inner.addEntry(entry)) return false;
        innerConst = inner.clone();
        return true;
    }

    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> that) {
        inner.putAll(that);
        innerConst = inner.clone();
    }

    @Override
    public synchronized V putIfAbsent(K key, V value) {
        V previous = inner.putIfAbsent(key, value);
        innerConst = inner.clone();
        return previous;
    }

    @Override
    public synchronized V remove(Object key) {
        V previous = inner.remove(key);
        innerConst = inner.clone();
        return previous;
    }

    @Override
    public synchronized boolean remove(Object key, Object value) {
        boolean changed = inner.remove(key, value);
        if (changed)
            innerConst = inner.clone();
        return changed;
    }

    @Override
    public synchronized Entry<K, V> removeEntry(K key) {
        Entry<K, V> entry = inner.removeEntry(key);
        if (entry != null)
            innerConst = inner.clone();
        return entry;
    }

    @Override
    public synchronized V replace(K key, V value) {
        V previous = inner.replace(key, value);
        innerConst = inner.clone();
        return previous;
    }

    @Override
    public synchronized boolean replace(K key, V oldValue, V newValue) {
        return inner.replace(key, oldValue, newValue);
    }

    @Override
    public int size() {
        return innerConst.size();
    }

    @Override
    public String toString() {
        return innerConst.toString();
    }

    @Override
    public Equality<? super V> valuesEquality() {
        return innerConst.valuesEquality();
    }

}
