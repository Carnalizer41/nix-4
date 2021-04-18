package ua.com.shyrkov;

import java.util.*;

public class OrderedList<E> implements List<E> {
    private final Comparator<? super E> comparator;
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private transient Object[] elementData;
    private int size;
    private transient int modCount = 0;

    public OrderedList(int initialCapacity) {
        this.comparator = null;
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                                                       initialCapacity);
        }
    }

    public OrderedList() {
        this.comparator = null;
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public OrderedList(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public OrderedList(Collection<? extends E> c) {
        this.comparator = null;
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == ArrayList.class) {
                elementData = a;
            } else {
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        } else {
            elementData = EMPTY_ELEMENTDATA;
        }
    }

    public OrderedList(Collection<? extends E> c, Comparator<? super E> comparator) {
        this.comparator = comparator;
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == ArrayList.class) {
                elementData = a;
            } else {
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        } else {
            elementData = EMPTY_ELEMENTDATA;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }


    private void add(E e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
    }

    @Override
    public boolean add(E e) {
        modCount++;
        add(e, elementData, size);
        if (size > 1)
            sort();
        return true;
    }

    private Object[] grow(int minCapacity) {
        return elementData = Arrays.copyOf(elementData, newCapacity(minCapacity));
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    private int newCapacity(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            return minCapacity;
        }
        return (newCapacity - MAX_ARRAY_SIZE <= 0)
                ? newCapacity
                : hugeCapacity(minCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE)
                ? Integer.MAX_VALUE
                : MAX_ARRAY_SIZE;
    }

    @Override
    public boolean remove(Object o) {
        final Object[] es = elementData;
        final int size = this.size;
        int i = 0;
        found:
        {
            if (o == null) {
                for (; i < size; i++)
                    if (es[i] == null)
                        break found;
            } else {
                for (; i < size; i++)
                    if (o.equals(es[i]))
                        break found;
            }
            return false;
        }
        fastRemove(es, i);
        return true;
    }

    private void fastRemove(Object[] es, int i) {
        modCount++;
        final int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(es, i + 1, es, i, newSize - i);
        es[size = newSize] = null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        E[] a = (E[]) c.toArray();
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        modCount++;
        int numNew = a.length;
        if (numNew == 0)
            return false;
        Object[] elementData;
        final int s;
        if (numNew > (elementData = this.elementData).length - (s = size))
            elementData = grow(s + numNew);
        System.arraycopy(a, 0, elementData, s, numNew);
        size = s + numNew;
        sort();
        return true;
    }

    @Deprecated
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return batchRemove(c, false, 0, size);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return batchRemove(c, true, 0, size);
    }

    private boolean batchRemove(Collection<?> c, boolean complement,
                                final int from, final int end) {
        Objects.requireNonNull(c);
        final Object[] es = elementData;
        int r;
        // Optimize for initial run of survivors
        for (r = from; ; r++) {
            if (r == end)
                return false;
            if (c.contains(es[r]) != complement)
                break;
        }
        int w = r++;
        try {
            for (Object e; r < end; r++)
                if (c.contains(e = es[r]) == complement)
                    es[w++] = e;
        } catch (Throwable ex) {
            // Preserve behavioral compatibility with AbstractCollection,
            // even if c.contains() throws.
            System.arraycopy(es, r, es, w, end - r);
            w += end - r;
            throw ex;
        } finally {
            modCount += end - w;
            shiftTailOverGap(es, w, end);
        }
        return true;
    }

    private void shiftTailOverGap(Object[] es, int lo, int hi) {
        System.arraycopy(es, hi, es, lo, size - hi);
        for (int to = size, i = (size -= hi - lo); i < to; i++)
            es[i] = null;
    }

    private void sort() {
        if (size == 0 || size == 1)
            return;
        if (comparator != null)
            mergeSort(elementData, 0, size - 1, false);
        else if (Comparable.class.isAssignableFrom(elementData[0].getClass()))
            mergeSort(elementData, 0, size - 1, true);
        else throw new ClassCastException("Objects must implement comparable");
        modCount++;
    }

    private void merge(Object[] array, int left, int mid, int right, boolean isComparable) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        Object[] L = new Object[n1];
        Object[] R = new Object[n2];
        for (int i = 0; i < n1; i++)
            L[i] = array[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = array[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            int compare;
            if (isComparable) {
                Comparable<E> el1 = (Comparable<E>) L[i];
                compare = el1.compareTo((E) R[j]);
            } else compare = comparator.compare((E) L[i], (E) R[j]);

            if (compare <= 0) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    private void mergeSort(Object[] array, int left, int right, boolean isComparable) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(array, left, mid, isComparable);
        mergeSort(array, mid + 1, right, isComparable);
        merge(array, left, mid, right, isComparable);
    }

    @Override
    public void clear() {
        modCount++;
        final Object[] es = elementData;
        for (int to = size, i = size = 0; i < to; i++)
            es[i] = null;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) elementData[index];
    }

    @Deprecated
    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    @Override
    public E remove(int index) {
        Objects.checkIndex(index, size);
        final Object[] es = elementData;

        @SuppressWarnings("unchecked") E oldValue = (E) es[index];
        fastRemove(es, index);

        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    private int indexOfRange(Object o, int start, int end) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size);
    }

    private int lastIndexOfRange(Object o, int start, int end) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = end - 1; i >= start; i--) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = end - 1; i >= start; i--) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = OrderedList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                OrderedList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                OrderedList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet = -1;
        int expectedModCount = modCount;

        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = OrderedList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                OrderedList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        List<E> sub = new OrderedList<>();
        for (int i = fromIndex; i <= toIndex; i++) {
            sub.add((E) elementData[i]);
        }
        return sub;
    }
}
