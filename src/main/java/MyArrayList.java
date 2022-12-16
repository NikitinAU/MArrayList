import java.util.*;
import java.util.stream.Collectors;

/**
 * Realization of ArrayList using dynamic array
 * This array is initialized with capacity (256 by default), which is used to allocate enough memory for elements
 * If the amount of elements exceeds capacity, it is increased by a factor of 1.5
 *
 * Used in educational purposes
 *
 * @author Nikitin Andrei
 * @param <E> type of array that will be created
 */
public class MyArrayList<E> implements List<E>, RandomAccess, Cloneable {

    private static final int DEFAULT_CAPACITY = 256;
    /**
     * Dynamic array to hold elements of given type
     */
    private E[] array;
    /**
     * Amount of elements currently stored in array
     */
    private int size;
    /**
     * Amount of allocated cells in array
     */
    private final int capacity;

    /**
     * Method to ensure that any element(s) added will fit in array
     * @param newSize soon to be size of array
     */
    private void ensureCapacity(int newSize){
        if (newSize > capacity){
            resize(Math.max(capacity + capacity/2 + 1, capacity + newSize));
        }
    }

    /**
     * Method to resize the array
     * @param capacity of this array
     */
    private void resize(int capacity){
        E[] newArr = (E[])new Object[capacity];
        System.arraycopy(array,0,newArr,0,size);
        array = newArr;
    }

    /**
     * <p>Constructor for main.java.MyArrayList
     * Initializes dynamic array with 256 empty cells
     * </p>
     */
    public MyArrayList(){
        size = 0;
        capacity = DEFAULT_CAPACITY;
        array = (E[])new Object[capacity];
    }
    /**
     *<p> Parametrized constructor for main.java.MyArrayList
     *     Initialized using int parameter capacity
     *</p>
     * @param capacity amount of cells in the array
     * @throws IllegalArgumentException
     */
    public MyArrayList(int capacity){
        if (capacity <= 0){
            throw new IllegalArgumentException("Cannot initialize array with capacity <= 0!");
        }
        this.size = 0;
        this.capacity = capacity;
        array = (E[])new Object[capacity];
    }

    /**
     * Parametrized constructor for main.java.MyArrayList
     * Copies all elements from passed collection into array
     * @param c Collection
     */
    public MyArrayList(Collection<? extends E> c){
        size = c.size();
        capacity = Math.max(c.size(), DEFAULT_CAPACITY);
        addAll(c);
    }
    /**
     * <p>Method to return amount of elements in the array</p>
     * @return the amount of elements in the array
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * <p>Checks if the array is empty</p>
     * @return true if the array is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * <p>Checks if the object is in the array
     *     Search is linear time (O(n))
     * </p>
     *
     * The algorithm goes through the array once, looking for the first element matching passed
     *
     * @param o element whose presence in this list is to be tested
     * @return true if the element is in the array, false otherwise
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /**
     * Iterator starts at index 0
     * @return iterator for object of main.java.MyArrayList
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            /**
             * Checks if there is a next element that is not null
             * @return true if there is element, false otherwise
             */
            @Override
            public boolean hasNext() {
                return currentIndex < size && array[currentIndex]!=null;
            }

            /**
             * Returns next element relative to current iterator
             * @return next element
             */
            @Override
            public E next() {
                return array[currentIndex + 1];
            }
        };
    }
    /**
     * Returns an array of type Object with all elements of main.java.MyArrayList object
     * @return array of type object
     */
    @Override
    public Object[] toArray() {
        resize(size);
        return Arrays.stream(array).toArray();
    }
    /**
     *Returns an array containing all the elements in this list in proper sequence (from first to last element);
     * the runtime type of the returned array is that of the specified array. If the list fits in the specified array,
     * it is returned therein. Otherwise, a new array is allocated with the runtime type of the specified array and the size of this list.
     * If the list fits in the specified array with room to spare (i.e., the array has more elements than the list),
     * the element in the array immediately following the end of the list is set to null.
     * (This is useful in determining the length of the list only if the caller knows that the list does not contain any null elements.)
     * Like the toArray() method, this method acts as bridge between array-based and collection-based APIs.
     * Further, this method allows precise control over the runtime type of the output array, and may, under certain circumstances, be used to save allocation costs.
     *
     * @param a the array into which the elements of this list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of this list
     */
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length >= size){
            System.arraycopy(array, 0, a, 0, size);
            if (a.length > size)
                a[size] = null;
            return a;
        }
        return (T[])Arrays.copyOf(array, size);
    }
    /**
     * Adds element to the end of array
     * @param e element whose presence in this collection is to be ensured
     * @return true
     */
    @Override
    public boolean add(E e) {
        ensureCapacity(size+1);
        array[size++] = e;
        return true;
    }

    /**
     * Removes the first occurrence of the element from the array
     * Search for the element is linear time (O(n))
     * @param o element to be removed from this list, if present
     * @return true
     */
    @Override
    public boolean remove(Object o) {
        int ind = indexOf(o);
        if (ind == -1)
            return false;
        remove(ind);
        return true;
    }

    /**
     * Check if the array holds ALL the elements from passed Collection
     * @param c collection to be checked for containment in this list
     * @return true if all the elements of the passed Collection are in the array, false otherwise
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return c.parallelStream().allMatch(this::contains);
    }

    /**
     * Adds ALL the elements from passed Collection to the end of the array
     * @param c collection containing elements to be added to this collection
     * @return true
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        ensureCapacity(size + c.size());
        System.arraycopy(c.toArray(), 0, array, size, c.size());
        size+=c.size();
        return true;
    }

    /**
     * Adds ALL the elements from passed Collection to the array at given index, shifting all other elements after the inserted Collection
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c collection containing elements to be added to this list
     * @return true
     * @throws IndexOutOfBoundsException
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException("Index of get out of bounds! Index: " + index);
        }
        ensureCapacity(size+c.size());
        System.arraycopy(array, index, array, index + c.size(), size-index);
        System.arraycopy(c.toArray(), 0, array, index, c.size());
        size+=c.size();
        return true;
    }

    /**
     * Removes ALL occurrences of elements from passed Collection from array
     * @param c collection containing elements to be removed from this list
     * @return true
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Arrays.stream(array).filter(c::contains).forEach(this::remove);
        return true;
    }

    /**
     * Removes ALL elements from array that are not in the passed Collection
     * @param c collection containing elements to be retained in this list
     * @return true
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        Arrays.stream(array).filter(elem -> !(c.contains(elem))).forEach(this::remove);
        return true;
    }

    /**
     * Removes ALL elements from array
     * Capacity of the array is unchanged after this operation
     */
    @Override
    public void clear() {
        Arrays.fill(array, null);
        size = 0;
    }

    /**
     * Returns element at given index from array
     * @param index index of the element to return
     * @return element of array at the given index
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E get(int index) {
        if (index >= size || index < 0){;
            throw new IndexOutOfBoundsException("Index of get out of bounds! Index: " + index);
        }
        return array[index];
    }

    /**
     * Replaces the current value at index with a given one
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return element of the array at the given index
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E set(int index, E element) {
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException("Index of get out of bounds! Index: " + index);
        }
        return (array[index] = element);
    }

    /**
     * Adds an element in the array at the index, shifting all elements after index to the right
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    @Override
    public void add(int index, E element) {
        ensureCapacity(size+1);
        System.arraycopy(array,index,array,index+1,size-index);
        array[index] = element;
        ++size;
    }

    /**
     * Removes element at the index, shifting all following elements to the left
     * @param index the index of the element to be removed
     * @return removed element
     * @throws IndexOutOfBoundsException
     */
    @Override
    public E remove(int index) {
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException("Index of get out of bounds! Index: " + index);
        }
        E removed = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
        return removed;
    }

    /**
     * Finds the first element equal to passed Object
     * Search is linear time (O(n))
     * @param o element to search for
     * @return index if found, -1 otherwise
     */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; ++i){
            if (array[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the last element equal to passed Object
     * Search is linear time (O(n))
     * @param o element to search for
     * @return index if found, -1 otherwise
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size-1; i >= 0; --i){
            if (array[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns ListIterator for object of main.java.MyArrayList
     * ListIterator is placed in the beginning of array
     * @return ListIterator of object main.java.MyArrayList
     */
    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    /**
     * Returns ListIterator for object of main.java.MyArrayList at index
     * @param index index of the first element to be returned from the
     *        list iterator (by a call to {@link ListIterator#next next})
     * @return ListIterator of object main.java.MyArrayList at index
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            private int currentIndex = index;
            @Override
            public boolean hasNext() {
                return currentIndex < size && array[currentIndex]!=null;
            }

            @Override
            public E next() {
                return hasNext() ? array[++currentIndex] : null;
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex > 0 && array[currentIndex]!=null;
            }

            @Override
            public E previous() {
                return hasPrevious() ? array[--currentIndex] : null;
            }

            @Override
            public int nextIndex() {
                return currentIndex + 1;
            }

            @Override
            public int previousIndex() {
                return currentIndex - 1;
            }

            @Override
            public void remove() {
                System.arraycopy(array, currentIndex + 1, array, currentIndex, size - currentIndex - 1);
                array[--size] = null;
            }

            @Override
            public void set(E e) {
                array[currentIndex] = e;
            }

            @Override
            public void add(E e) {
                ensureCapacity(size+1);
                System.arraycopy(array,currentIndex + 1 ,array,currentIndex +2,size-currentIndex -1 );
                array[currentIndex + 1] = e;
                ++size;
            }
        };
    }

    /**
     * Returns a sublist consisting of all elements starting at fromIndex and ending in toIndex
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return new main.java.MyArrayList object
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        MyArrayList<E> newArr = new MyArrayList<>();
        if (fromIndex > toIndex){
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }
        Arrays.stream(array, fromIndex, toIndex).forEach(newArr::add);
        return newArr;
    }

    /**
     * Creates a clone of main.java.MyArrayList
     * @return new main.java.MyArrayList object
     * @throws CloneNotSupportedException
     */
    @Override
    public MyArrayList<E> clone() throws CloneNotSupportedException {
        MyArrayList<E> a = new MyArrayList<>();
        a.addAll(this);
        return a;
    }

    /**
     * Creates a string consisting of all elements in main.java.MyArrayList, prefixed by '[', and suffixed by ']'
     * @return a string consisting of all elements in main.java.MyArrayList
     */
    public String toString(){
        resize(size);
        return Arrays.stream(array).map(aToM->aToM==null ? "null" : aToM.toString()).collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Method to remove all cells after size
     */
    public void trimToSize(){
        resize(size);
    }

    /**
     * Sorts this list according to the order induced by the specified
     * {@link Comparator}.  The sort is <i>stable</i>: this method must not
     * reorder equal elements.
     *
     * <p>All elements in this list must be <i>mutually comparable</i> using the
     * specified comparator (that is, {@code c.compare(e1, e2)} must not throw
     * a {@code ClassCastException} for any elements {@code e1} and {@code e2}
     * in the list).
     *
     * <p>If the specified comparator is {@code null} then all elements in this
     * list must implement the {@link Comparable} interface and the elements'
     * {@linkplain Comparable natural ordering} should be used.
     *
     * This implementation uses Single Pivot Quicksort and has the best case runtime of O(nlog(n)),
     * average runtime is also O(nlog(n)), and the worst case runtime of O(n<sup>2</sup>)
     *
     * This implementation uses single pivot quicksort with pivot in the middle of array
     * The array is divided into 2 parts by this pivot: left and right
     * All the elements in the left part which are more than pivot are then moved to the right,
     * and all the elements in the right which are less than pivot are moved to the left
     * After splitting the array into 2 parts, quicksort is then called on those 2 parts of the array
     *
     * Worst-case analysis
     * The most unbalanced partition occurs when one of the sub-lists returned by the partitioning routine is of size n − 1.
     * This may occur if the pivot happens to be the smallest or largest element in the list,
     * or in some implementations when all the elements are equal.
     * If this happens repeatedly in every partition, then each recursive call processes a list of size one less than the previous list.
     * Consequently, we can make n − 1 nested calls before we reach a list of size 1.
     * This means that the call tree is a linear chain of n − 1 nested calls.
     * The ith call does O(n − i) work to do the partition, and {\displaystyle \textstyle \sum _{i=0}^{n}(n-i)=O(n^{2})}\textstyle \sum _{i=0}^{n}(n-i)=O(n^{2}),
     * so in that case quicksort takes O(n2) time.
     *
     * Best-case analysis
     * In the most balanced case, each time we perform a partition we divide the list into two nearly equal pieces.
     * This means each recursive call processes a list of half the size.
     * Consequently, we can make only log2 n nested calls before we reach a list of size 1.
     * This means that the depth of the call tree is log2 n.
     * But no two calls at the same level of the call tree process the same part of the original list;
     * thus, each level of calls needs only O(n) time all together
     * (each call has some constant overhead, but since there are only O(n) calls at each level, this is subsumed in the O(n) factor).
     * The result is that the algorithm uses only O(n log n) time.
     *
     * Average-case analysis
     * To sort an array of n distinct elements, quicksort takes O(n log n) time in expectation, averaged over all n! permutations of n elements with equal probability.
     * Alternatively, if the algorithm selects the pivot uniformly at random from the input array,
     * the same analysis can be used to bound the expected running time for any input sequence;
     * the expectation is then take over the random choices made by the algorithm
     *
     * <p>The implementation is adapted from my realization of c++ version of quicksort with minor adjustments
     * (<a href="https://github.com/NikitinAU/task_strings/blob/master/task_strings/Source.cpp"></a>),
     * which realizes the single pivot quicksort by Hoare, C. A. R. (1961). "Algorithm 64: Quicksort"
     *
     * @param c the {@code Comparator} used to compare list elements.
     *          A {@code null} value indicates that the elements'
     *          {@linkplain Comparable natural ordering} should be used
     */
    @Override
    public void sort(Comparator c){
        quicksort(array, 0, size - 1, c);
    }

    private void quicksort(E[] arr, int lb, int ub, Comparator c){
        int left = lb, right = ub;
        E pivot = arr[(left + right) / 2];
        do{
            while (left < ub && c.compare(arr[left], pivot) < 0) {
                ++left;
            }
            while (right > lb && c.compare(arr[right], pivot) > 0){
                --right;
            }
            if (left <= right){
                E tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;
                ++left; -- right;
            }
        } while (left <= right);
        if (lb < right){
            quicksort(arr, lb, right, c);
        }
        if (left < ub){
            quicksort(arr, left, ub, c);
        }
    }
}
