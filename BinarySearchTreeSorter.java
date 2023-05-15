import java.util.List;

public class BinarySearchTreeSorter
{
    public static <T extends Comparable<T>> void sort(List<T> list) throws DuplicateElementException
    {
        BinarySearchTree<T> tree = new BinarySearchTree<>();

        tree.addAll(list);

        tree.toStringInOrder()
    }
}
