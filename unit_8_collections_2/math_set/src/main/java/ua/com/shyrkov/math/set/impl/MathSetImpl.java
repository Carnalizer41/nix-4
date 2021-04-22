package ua.com.shyrkov.math.set.impl;

import ua.com.shyrkov.math.set.MathSet;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathSetImpl<N extends Number> implements MathSet<N> {

    private static final int DEFAULT_CAPACITY = 10;
    private final N[] DEFAULT_CAPACITY_EMPTY_ARRAY = (N[]) new Number[DEFAULT_CAPACITY];
    private transient N[] elementData;
    private int size = 0;
    private int capacity = 0;

    public MathSetImpl() {
        capacity = DEFAULT_CAPACITY;
        elementData = DEFAULT_CAPACITY_EMPTY_ARRAY;
    }

    public MathSetImpl(int capacity) {
        this.capacity = capacity;
        elementData = DEFAULT_CAPACITY_EMPTY_ARRAY;
    }

    public MathSetImpl(N[] numbers) {
        elementData = DEFAULT_CAPACITY_EMPTY_ARRAY;
        add(numbers);
    }

    @SafeVarargs
    public MathSetImpl(N[]... numbers) {
        elementData = DEFAULT_CAPACITY_EMPTY_ARRAY;
        for (N[] number : numbers) {
            add(number);
        }
    }

    public MathSetImpl(MathSet<N> numbers) {
        elementData = DEFAULT_CAPACITY_EMPTY_ARRAY;
        add(numbers.toArray());
    }

    @SafeVarargs
    public MathSetImpl(MathSet<N>... numbers) {
        elementData = DEFAULT_CAPACITY_EMPTY_ARRAY;
        for (MathSet<N> number : numbers) {
            add(number.toArray());
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(N n) {
        if (n != null && isUnique(n)) {
            int oldCapacity = capacity;
            if (size == capacity) {
                capacity = (int) (oldCapacity * 1.5 + 1);
                N[] tempArray = (N[]) new Number[capacity];
                for (int i = 0; i < size; i++) {
                    tempArray[i] = elementData[i];
                }
                elementData = tempArray;
            }
            elementData[size] = n;
            size++;
        }
    }

    @SafeVarargs
    @Override
    public final void add(N... numbers) {
        for (N number : numbers) {
            add(number);
        }
    }

    private boolean isUnique(N number) {
        if (size > 0)
            for (N element : elementData) {
                if (element != null && element.equals(number))
                    return false;
            }
        return true;
    }

    @Override
    public void join(MathSet<N> ms) {
        add(ms.toArray());
    }

    @SafeVarargs
    @Override
    public final void join(MathSet<N>... ms) {
        for (MathSet<N> m : ms) {
            add(m.toArray());
        }
    }

    @Override
    public void sortDesc() {
        mergeSort(elementData, 0, size - 1, false);
    }

    @Override
    public void sortDesc(int firstIndex, int lastIndex) {
        mergeSort(elementData, firstIndex, lastIndex, false);
    }

    @Override
    public void sortDesc(N value) {
        int index = indexOf(value);
        mergeSort(elementData, index, size - 1, false);
    }

    @Override
    public void sortAsc() {
        mergeSort(elementData, 0, size - 1, true);
    }

    @Override
    public void sortAsc(int firstIndex, int lastIndex) {
        mergeSort(elementData, firstIndex, lastIndex, true);
    }

    @Override
    public void sortAsc(N value) {
        int index = indexOf(value);
        mergeSort(elementData, index, size - 1, true);
    }

    private void mergeSort(N[] array, int left, int right, boolean isAsc) {
        checkIndex(left);
        checkIndex(right);
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(array, left, mid, isAsc);
        mergeSort(array, mid + 1, right, isAsc);
        merge(array, left, mid, right, isAsc);
    }

    private void merge(N[] array, int left, int mid, int right, boolean isAsc) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        N[] L = (N[]) new Number[n1];
        N[] R = (N[]) new Number[n2];
        for (int i = 0; i < n1; i++)
            L[i] = array[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = array[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (isAsc) {
                if (compare(L[i], R[j]) <= 0) {
                    array[k] = L[i];
                    i++;
                } else {
                    array[k] = R[j];
                    j++;
                }
                k++;
            } else {
                if (compare(L[i], R[j]) >= 0) {
                    array[k] = L[i];
                    i++;
                } else {
                    array[k] = R[j];
                    j++;
                }
                k++;
            }
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

    private int compare(Number a, Number b) {
        return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
    }

    @Override
    public int indexOf(N n) {
        for (int i = 0; i < elementData.length; i++) {
            if (elementData[i].equals(n))
                return i;
        }
        return -1;
    }

    @Override
    public N get(int index) {
        checkIndex(index);
        return elementData[index];
    }

    @Override
    public N getMax() {
        N[] tempArray = toArray();
        mergeSort(tempArray, 0, size-1, false);
        return tempArray[0];
    }

    @Override
    public N getMin() {
        N[] tempArray = toArray();
        mergeSort(tempArray, 0, size-1, true);
        return tempArray[0];
    }

    @Override
    public N getAverage() {
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < size; i++) {
            sum = new BigDecimal(elementData[i].toString()).add(new BigDecimal(sum.toString()));
        }
        return (N) sum.divide(new BigDecimal(size), 2, RoundingMode.CEILING);
    }

    @Override
    public N getMedian() {
        N[] tempArray = toArray();
        mergeSort(tempArray, 0, size-1, true);
        if (size % 2 != 0) {
            return tempArray[size / 2];
        } else {
            return (N) new BigDecimal(tempArray[size / 2 - 1].toString())
                    .add(new BigDecimal(tempArray[size / 2].toString()))
                    .divide(new BigDecimal(2), 2, RoundingMode.CEILING);
        }
    }

    @Override
    public N[] toArray() {
        N[] array = (N[]) new Number[size];
        for (int i = 0; i < size; i++) {
            array[i] = elementData[i];
        }
        return array;
    }

    @Override
    public N[] toArray(int firstIndex, int lastIndex) {
        checkIndex(firstIndex);
        checkIndex(lastIndex);
        if (firstIndex <= lastIndex) {
            N[] array = (N[]) new Number[lastIndex - firstIndex + 1];
            int index = 0;
            for (int i = firstIndex; i <= lastIndex; i++) {
                array[index] = elementData[i];
                index++;
            }
            return array;
        } else throw new RuntimeException("First index can`t be bigger than last index");
    }

    @Override
    public MathSet<N> squash(int firstIndex, int lastIndex) {
        return new MathSetImpl<N>(toArray(0, firstIndex-1), toArray(lastIndex + 1, size-1));
    }

    @Override
    public void clear() {
        capacity = DEFAULT_CAPACITY;
        size = 0;
        elementData = DEFAULT_CAPACITY_EMPTY_ARRAY;
    }

    @Override
    public void clear(N[] numbers) {
        int index;
        for (N number : numbers) {
            index = indexOf(number);
            if (index >= 0)
                remove(index);
        }
    }

    private void remove(int index) {
        checkIndex(index);
        N[] tempArray = (N[]) new Number[size - 1];
        for (int i = 0; i < index; i++) {
            tempArray[i] = elementData[i];
        }
        for (int i = index; i < size - 1; i++) {
            tempArray[i] = elementData[i + 1];
        }
        elementData = tempArray;
    }
}
