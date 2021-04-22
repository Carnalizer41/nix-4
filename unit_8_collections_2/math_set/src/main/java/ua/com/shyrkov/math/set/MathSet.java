package ua.com.shyrkov.math.set;

public interface MathSet<N extends Number> {

    int size();
    int indexOf(N n);
    void add(N n);
    void add(N ... n);
    void join(MathSet<N> ms);
    void join(MathSet<N> ... ms);
    void sortDesc();
    void sortDesc(int firstIndex, int lastIndex);
    void sortDesc(N value);
    void sortAsc();
    void sortAsc(int firstIndex, int lastIndex);
    void sortAsc(N value);
    N get(int index);
    N getMax();
    N getMin();
    N getAverage();
    N getMedian();
    N[] toArray();
    N[] toArray(int firstIndex, int lastIndex);
    MathSet<N> squash(int firstIndex, int lastIndex);
    void clear();
    void clear(N[] numbers);
}
