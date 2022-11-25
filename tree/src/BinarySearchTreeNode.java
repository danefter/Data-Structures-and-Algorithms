import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Dan Jensen
 *
 * **/

public class BinarySearchTreeNode<T extends Comparable<T>> {

    private T data;
    private BinarySearchTreeNode<T> left;
    private BinarySearchTreeNode<T> right;

    public BinarySearchTreeNode(T data) {
        this.data = data;
    }

    public boolean add(T t) {
        boolean added = false;
        if (!contains(t)) {
            insert(t);
            added = true;
        }
        return added;
    }

    private void insert(T t) {
        int comparison = compareTo(t);
        if (comparison == -1) {
            if (left != null)
                left.insert(t);
            if (left == null)
                left = new BinarySearchTreeNode<>(t);
        }
        if (comparison == 1) {
            if (right != null)
                right.insert(t);
            if (right == null)
                right = new BinarySearchTreeNode<>(t);
        }
        if (data == null)
            this.data = t;
    }

    public BinarySearchTreeNode<T> remove(T t) {
        return deleteNode(this, t);
    }

    public BinarySearchTreeNode<T> deleteNode(BinarySearchTreeNode<T> root, T t) {
        if(root == null) return root;
        int comparison = root.compareTo(t);
        if(comparison == 1)
            root.right = deleteNode(root.right, t);
        else if(comparison == -1)
            root.left = deleteNode(root.left, t);
        else{
            if(root.left == null && root.right == null){
                root = null;
            }else if(root.right != null){
                root.data = findSuccessor(root); // my worthy successor
                root.right = deleteNode(root.right, root.data);
            }else{
                root.data = findPredecessor(root);
                root.left = deleteNode(root.left, root.data);
            }
        }
        return root;
    }

    private T findSuccessor(BinarySearchTreeNode<T> root){
        root = root.right;
        while(root.left != null){
            root = root.left;
        }
        return root.data;
    }

    private T findPredecessor(BinarySearchTreeNode<T> root){
        root = root.left;
        while(root.right != null){
            root = root.right;
        }
        return root.data;
    }


    private int compareTo(T t) {
        if (data != null) {
            if (t.compareTo(data) > 0)
                return 1;
            if (t.compareTo(data) < 0)
                return -1;}
        return 0;
    }

    public boolean contains(T t) {
        boolean contains = false;
        int comparison = compareTo(t);
        if (left != null && left.contains(t))
            contains = true;
        if (right != null && right.contains(t))
            contains = true;
        if (comparison == 0)
            contains = true;
        return contains;
    }

    public int size() {
        int size = 1;
        if (left != null && right != null)
            size += left.size() + right.size();
        if (left == null && right != null)
            size += right.size();
        if (right == null && left != null)
            size += left.size();
        if (data == null)
            size -= 1;
        return size;
    }

    public int depth() {
        int depth = 1;
        if (left != null && right != null)
            depth += Math.max(left.depth(), right.depth());
        if (left == null && right != null)
            depth = right.depth();
        if (right == null && left != null)
            depth = left.depth();
        return depth;
    }

    private String buildString(){
        StringBuilder sb = new StringBuilder();
        if (left != null)
            sb.append(left.buildString());
        if (data != null)
            sb.append(data).append(", ");
        if (right != null)
            sb.append(right.buildString());
        return sb.toString();
    }

    public String toString() {
        ArrayList<String> strings = new ArrayList<>(Arrays.stream(buildString().split(", ")).toList());
        return strings.toString().replace("[", "").replace("]", "");
    }
}