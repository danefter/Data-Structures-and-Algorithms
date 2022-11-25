import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Dan Jensen
 * **/
public class MyALDAList<E> implements ALDAList<E> {

    private int size = 0;

    private int modCount = 0;

    private Node<E> head;

    public MyALDAList(){
        this.size = 0;
        this.head = new Node<E>(null, null);
    }

    @Override
    public void add(E element) {
        add(size(), element);
    }

    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException();
        Node<E> node = getNode(index);
        node.next = new Node<>(node.data, node.next);
        node.data = element;
        if (index == 0)
            head.data = element;
        size++;
        modCount++;
    }
    //checks each node to see which to remove then pushes next node higher in list
    @Override
    public E remove(int index) {
        if (size == 0 || index >= size || index < 0) throw new IndexOutOfBoundsException();
        Node<E> toRemove = getNode(index);
        E data = toRemove.data;
        if (toRemove.next != null){
            toRemove.data = toRemove.next.data;
            toRemove.next = toRemove.next.next;
        }
        if (toRemove.next == null){
            toRemove.data = null;
        }
        size--;
        modCount++;
        return data;
    }

    @Override
    public boolean remove(E element) {
        if (!contains(element))
            return false;
        remove(indexOf(element));
        return true;
    }

    @Override
    public E get(int index) {
        if (index >= size || size == 0 || index < 0) throw new IndexOutOfBoundsException();
        return getNode(index).data;
    }

    @Override
    public boolean contains(E element) {
        Node<E> n = head;
        while (iterator().hasNext() && n.next != null && n.data != element)
            n = n.next;
        return n.data == element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> n = head;
        int index = 0;
        while (iterator().hasNext() && n.next != null && n.data != element){
            n = n.next;
            index ++;
        }
        if (index > size || !contains(element))
            return -1;
        return index;
    }

    @Override
    public void clear() {
        doClear();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ALDAListIterator();
    }

    @Override
    public String toString(){
        ArrayList<E> nodes = new ArrayList<>();
        for (int i = 0; i < size(); i++){
            nodes.add(getNode(i).data);
        }
        return Arrays.toString(nodes.toArray());
    }

    private Node<E> getNode(int index){
        Node<E> n = head;
        for (int i = 0; i < index; i++)
            n = n.next;
        return n;
    }
    //clear list
    private void doClear(){
        head = new Node<E>(null, null);
        size = 0;
        modCount ++;
    }

    //iterator using data of each node
    private class ALDAListIterator implements Iterator<E> {
        private Node<E> current = head;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;


        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public E next() {
            if(modCount != expectedModCount)
                throw new java.util.ConcurrentModificationException();
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            E nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        @Override
        public void remove() {
            if(modCount != expectedModCount)
                throw new java.util.ConcurrentModificationException();
            if(!okToRemove)
                throw new IllegalStateException( );
            MyALDAList.this.remove(indexOf(current.data)-1);
            expectedModCount++;
            okToRemove = false;
        }
    }

    private static class Node<E>{

        public E data;

        public Node<E> next;

        public Node(E e, Node<E> n){
            data = e;
            next = n;
        }
    }
}
