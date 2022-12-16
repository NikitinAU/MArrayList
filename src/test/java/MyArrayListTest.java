import jdk.jfr.Name;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.lang.Math.abs;

public class MyArrayListTest {
    private static MyArrayList<Integer> integerMyArrayList;
    private static MyArrayList<Double> doubleMyArrayList;
    private static MyArrayList<String> stringMyArrayList;
    private static MyArrayList<Long> longMyArrayList;
    private static MyArrayList<Float> floatMyArrayList;

    @Before
    public void reset(){
        integerMyArrayList = new MyArrayList<>();
        doubleMyArrayList = new MyArrayList<>();
        stringMyArrayList = new MyArrayList<>();
        longMyArrayList = new MyArrayList<>();
        floatMyArrayList = new MyArrayList<>();
    }
    @Test
    @Name("Put Default Collection")
    public void putDefaultCollection_success(){
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (int i = 0; i < 1000; i+=2){
            integerArrayList.add(i);
        }
        Assert.assertTrue(integerMyArrayList.addAll(integerArrayList));
        Assert.assertArrayEquals(integerArrayList.toArray(), integerMyArrayList.toArray());
    }

    @Test
    @Name("Add element")
    public void addElement_success(){
        ArrayList<Integer> al = new ArrayList<>();
        al.add(100);
        al.add(200);
        integerMyArrayList.add(100);
        integerMyArrayList.add(200);
        Assert.assertArrayEquals(al.toArray(), integerMyArrayList.toArray());
    }
    @Test
    @Name("Remove element")
    public void removeElement_success(){
        stringMyArrayList.add("8");
        Assert.assertEquals("8", stringMyArrayList.remove(0));
    }

    @Test
    @Name("Put Collection at index")
    public void putCollectionAtIndex_success(){
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (int i = 0; i < 1000; ++i){
            integerMyArrayList.add(i);
            integerArrayList.add(1000-i);
        }
        Assert.assertTrue(integerMyArrayList.addAll(400, integerArrayList));
    }

    @Test
    @Name("Remove all elements of another Collection")
    public void removeAll_success(){
        ArrayList<Integer> integerArrayList = new ArrayList<>(),
                           integerArrayListRetained = new ArrayList<>();
        for (int i = 0; i < 1000; ++i){
            integerMyArrayList.add(i);
            if (i%2 == 0)
                integerArrayList.add(i);
            else integerArrayListRetained.add(i);
        }
        Assert.assertTrue(integerMyArrayList.removeAll(integerArrayList));
        Assert.assertArrayEquals(integerArrayListRetained.toArray(), integerMyArrayList.toArray());
    }

    @Test
    @Name("Retain all elements of another Collection")
    public void retainAll_success(){
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (int i = 0; i < 1000; ++i){
            integerMyArrayList.add(i);
            if (i%2 == 0)
                integerArrayList.add(i);
        }
        Assert.assertTrue(integerMyArrayList.retainAll(integerArrayList));
        Assert.assertArrayEquals(integerArrayList.toArray(), integerMyArrayList.toArray());
    }

    @Test
    @Name("Check if contains all elements of another Collection")
    public void containsAll_success(){
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (int i = 0; i < 1000; ++i){
            integerMyArrayList.add(i);
            if (i%2 == 0)
                integerArrayList.add(i);
        }
        Assert.assertTrue(integerMyArrayList.containsAll(integerArrayList));
    }

    @Test
    @Name("Sort with different types")
    public void sortWithDifferentTypes(){
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<Double> doubleArrayList = new ArrayList<>();
        ArrayList<Long> longArrayList = new ArrayList<>();
        ArrayList<Float> floatArrayList = new ArrayList<>();

        for (int i = 0; i < 500; ++i){
            integerMyArrayList.add(i);
            integerMyArrayList.add(i*2);
            integerArrayList.add(i);
            integerArrayList.add(i*2);

            stringMyArrayList.add(String.valueOf(i));
            stringMyArrayList.add(String.valueOf(i*2));
            stringArrayList.add(String.valueOf(i));
            stringArrayList.add(String.valueOf(i*2));
        }

        for (long i = 0; i < 500; ++i){
            longMyArrayList.add(i);
            longMyArrayList.add(i*2);
            longArrayList.add(i);
            longArrayList.add(i*2);
        }

        for (double d = -10.0; d < 10.0; d+=0.1){
            doubleMyArrayList.add(d);
            doubleMyArrayList.add(d * 2);
            doubleArrayList.add(d);
            doubleArrayList.add(d*2);

            floatMyArrayList.add((float) d);
            floatMyArrayList.add((float) (d*2));
            floatArrayList.add((float) d);
            floatArrayList.add((float) (d*2));
        }
        ALComparator<Integer> integerALComparator = new ALComparator<>();
        ALComparator<Double> doubleALComparator = new ALComparator<>();
        ALComparator<String> stringALComparator = new ALComparator<>();
        ALComparator<Long> longALComparator = new ALComparator<>();
        ALComparator<Float> floatALComparator = new ALComparator<>();

        integerMyArrayList.sort(integerALComparator);
        integerArrayList.sort(integerALComparator);

        doubleMyArrayList.sort(doubleALComparator);
        doubleArrayList.sort(doubleALComparator);

        stringMyArrayList.sort(stringALComparator);
        stringArrayList.sort(stringALComparator);

        longMyArrayList.sort(longALComparator);
        longArrayList.sort(longALComparator);

        floatMyArrayList.sort(floatALComparator);
        floatArrayList.sort(floatALComparator);

        Assert.assertArrayEquals(integerArrayList.toArray(), integerMyArrayList.toArray());
        Assert.assertArrayEquals(doubleArrayList.toArray(), doubleMyArrayList.toArray());
        Assert.assertArrayEquals(stringArrayList.toArray(), stringMyArrayList.toArray());
        Assert.assertArrayEquals(longArrayList.toArray(), longMyArrayList.toArray());
        Assert.assertArrayEquals(floatArrayList.toArray(), floatMyArrayList.toArray());
    }

    @Test
    @Name("Check if empty")
    public void isEmpty_success(){
        Assert.assertTrue(integerMyArrayList.isEmpty());
    }

    @Test
    @Name("Clear array")
    public void clear_success(){
        integerMyArrayList.add(8);
        integerMyArrayList.clear();
        Assert.assertTrue(integerMyArrayList.isEmpty());
    }

    @Test
    @Name("Check indexOf")
    public void checkIndexOf_success(){
        for (int i = -10; i <= 10; ++i){
            integerMyArrayList.add(abs(i));
        }
        Assert.assertEquals(10, integerMyArrayList.indexOf(0));
        Assert.assertEquals(5, integerMyArrayList.indexOf(5));
        Assert.assertEquals(-1, integerMyArrayList.indexOf(12));
    }

    @Test
    @Name("Check lastIndexOf")
    public void checkLastIndexOf_success(){
        for (int i = -10; i <= 10; ++i){
            integerMyArrayList.add(abs(i));
        }
        Assert.assertEquals(10, integerMyArrayList.lastIndexOf(0));
        Assert.assertEquals(15, integerMyArrayList.lastIndexOf(5));
        Assert.assertEquals(-1, integerMyArrayList.lastIndexOf(12));
    }

    @Test
    @Name("Iterator test")
    public void iterator_success(){
        integerMyArrayList.add(0);
        integerMyArrayList.add(1);
        Iterator<Integer> it = integerMyArrayList.iterator();

        Assert.assertTrue(it.hasNext());
        Assert.assertEquals((Integer) 1, it.next());
    }

    @Test
    @Name("ListIterator without index")
    public void listIterator_success(){
        for (int i = 0; i < 10; ++i){
            integerMyArrayList.add(i);
        }
        ListIterator<Integer> it = integerMyArrayList.listIterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertFalse(it.hasPrevious());
        Assert.assertEquals(1, it.nextIndex());
        Assert.assertEquals(-1, it.previousIndex());
        Assert.assertEquals((Integer) 1, it.next());
        Assert.assertEquals((Integer) 0, it.previous());
        it.add(100);
        Assert.assertEquals((Integer) 100, it.next());
    }

    @Test
    @Name("Iterator with index")
    public void listIteratorIndexed_success(){
        for (int i = 0; i < 10; ++i){
            integerMyArrayList.add(i);
        }
        ListIterator<Integer> it = integerMyArrayList.listIterator(5);
        Assert.assertTrue(it.hasNext());
        Assert.assertTrue(it.hasPrevious());
        Assert.assertEquals(6, it.nextIndex());
        Assert.assertEquals(4, it.previousIndex());
        Assert.assertEquals((Integer) 6, it.next());
        Assert.assertEquals((Integer) 5, it.previous());
        it.add(100);
        Assert.assertEquals((Integer) 100, it.next());


    }

    @Test
    @Name("Sub-list test")
    public void subList_success(){
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (int i = 0; i < 100; ++i){
            integerArrayList.add(i);
            integerMyArrayList.add(i);
        }
        Assert.assertArrayEquals(integerArrayList.subList(20, 80).toArray(), integerMyArrayList.subList(20,80).toArray());
    }

    @Test
    @Name("To Different Array")
    public void toDifferentArray_success(){
        Number[] nar = new Number[12];
        for(int i = 0; i < 11; ++i){
            integerMyArrayList.add(i);
            nar[i] = i;
        }
        Assert.assertArrayEquals(nar, integerMyArrayList.toArray(new Number[12]));
    }

    @Test
    @Name("Convert to String")
    public void convertToString_success(){
        ArrayList<Integer> integerArrayList = new ArrayList<>();

        for (int i = 0; i < 10; ++i){
            integerArrayList.add(i);
            integerMyArrayList.add(i);
        }

        Assert.assertEquals(integerArrayList.toString(), integerMyArrayList.toString());
    }

    @Test
    @Name("Check clone")
    public void clone_success(){
        integerMyArrayList.add(0);
        integerMyArrayList.add(5);
        try {
            MyArrayList<Integer> integerMyArrayListClone = integerMyArrayList.clone();
            Assert.assertArrayEquals(integerMyArrayList.toArray(), integerMyArrayListClone.toArray());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
