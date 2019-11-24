package ua.edu.ucu.collections.immutable;

import java.util.Arrays;

public class ImmutableArrayList implements ImmutableList {
    private final Object[] arr;

    public ImmutableArrayList() {
        this.arr = new Object[0];
    }

    public ImmutableArrayList(Object[] arr) {
        this.arr = Arrays.copyOf(arr, arr.length);
    }

    private ImmutableArrayList copy(int s) {
        ImmutableArrayList arrCopy =
                new ImmutableArrayList(Arrays.copyOf(arr, s));
        return arrCopy;
    }

    @Override
    public ImmutableArrayList add(int index, Object e) {
        if (index > arr.length || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableArrayList newArr = copy(arr.length + 1);
        for (int i = arr.length; i > index; i--) {
            newArr.arr[i] = newArr.arr[i - 1];
        }
        newArr.arr[index] = e;
        return newArr;
    }

    @Override
    public ImmutableArrayList add(Object e) {
        return add(arr.length, e);
    }

    @Override
    public ImmutableArrayList addAll(int index, Object[] c) {
        if (index > arr.length || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableArrayList newArr = copy(arr.length);
        for (int i = 0; i < c.length; i++) {
            newArr = newArr.add(index + i, c[i]);
        }
        return newArr;
    }

    @Override
    public ImmutableArrayList addAll(Object[] c) {
        return addAll(arr.length, c);
    }

    @Override
    public Object get(int index) {
        if (index >= arr.length || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return arr[index];
    }

    @Override
    public ImmutableArrayList remove(int index) {
        if (index >= arr.length || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableArrayList newArr = copy(arr.length);
        for (int i = index; i < newArr.arr.length - 1; i++) {
            newArr.arr[i] = newArr.arr[i + 1];
        }
        newArr = newArr.copy(newArr.arr.length - 1);
        return newArr;
    }

    @Override
    public ImmutableArrayList set(int index, Object e) {
        if (index >= arr.length || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableArrayList newArr = copy(arr.length);
        newArr.arr[index] = e;
        return newArr;
    }

    @Override
    public int indexOf(Object e) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return arr.length;
    }

    @Override
    public ImmutableArrayList clear() {
        return new ImmutableArrayList();
    }

    @Override
    public boolean isEmpty() {
        return arr.length == 0;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(arr, arr.length);
    }

    @Override
    public String toString() {
        String str = Arrays.toString(toArray());
        return str.substring(1, str.length() - 1);
    }
}
