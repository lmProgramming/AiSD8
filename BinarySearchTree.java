import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BinarySearchTree<T extends Comparable<T>>
{
    private Node<T> root;

    public void add(T value) throws DuplicateElementException
    {
        root = addRecursive(root, value, null);
    }

    public void addAll(List<T> list) throws DuplicateElementException
    {
        for (T t : list)
        {
            add(t);
        }
    }

    private Node<T> addRecursive(Node<T> current, T value, Node<T> parent)  throws DuplicateElementException
    {
        if (current == null)
        {
            return new Node<>(value, parent);
        }
        else if (value.equals(current.value))
        {
            throw new DuplicateElementException();
        }

        if (value.compareTo(current.value) < 0)
        {
            current.left = addRecursive(current.left, value, current);
        }
        else
        {
            current.right = addRecursive(current.right, value, current);
        }

        return current;
    }

    public boolean contains(T value) {
        return findNode(value) != null;
    }

    private Node<T> findNode(T value)
    {
        return findNodeRecursive(value, root);
    }

    private Node<T> findNodeRecursive(T value, Node<T> current)
    {
        if (current == null)
        {
            return null;
        }

        if (value.equals(current.value))
        {
            return current;
        }

        if (value.compareTo(current.value) < 0)
        {
            return findNodeRecursive(value, current.left);
        }
        else
        {
            return findNodeRecursive(value, current.right);
        }
    }

    public void delete(T value)
    {
        Node<T> foundNode = findNode(value);

        deleteNode(foundNode);
    }

    public void deleteNode(Node<T> node)
    {
        if (node != null)
        {
            int childrenAmount = node.childrenAmount();

            if (childrenAmount == 0)
            {
                deleteNodeNoChildren(node);
            }
            else if (childrenAmount == 1)
            {
                deleteNodeOneChild(node);
            }
            else
            {
                deleteNodeTwoChildren(node);
            }
        }
    }

    // node z has no children
    private void deleteNodeNoChildren(Node<T> foundNode)
    {
        Node<T> parent = foundNode.parent;

        if (parent == null)
        {
            root = null;
            return;
        }

        if (parent.left == foundNode)
        {
            parent.left = null;
        }
        else
        {
            parent.right = null;
        }
    }

    // node z has exactly one child
    private void deleteNodeOneChild(Node<T> foundNode)
    {
        Node<T> parent = foundNode.parent;
        Node<T> child = foundNode.left != null? foundNode.left : foundNode.right;

        if (parent == null)
        {
            root = child;
            return;
        }

        if (parent.left == foundNode)
        {
            parent.left = child;
        }
        else
        {
            parent.right = child;
        }
    }

    private void deleteNodeTwoChildren(Node<T> foundNode)
    {
        Node<T> smallestNode = findSmallestNode(foundNode.right);
        foundNode.value = smallestNode.value;
        deleteNode(smallestNode);
    }

    private Node<T> findSmallestNode(Node<T> current)
    {
        return current.left == null ? current : findSmallestNode(current.left);
    }

    public String toStringPreOrder()
    {
        StringBuilder sb = new StringBuilder();
        toStringPreOrderRecursive(root, sb);
        removeTrailingComma(sb);
        return sb.toString();
    }

    private void toStringPreOrderRecursive(Node<T> current, StringBuilder sb) {
        if (current != null)
        {
            sb.append(current.value).append(", ");
            toStringPreOrderRecursive(current.left, sb);
            toStringPreOrderRecursive(current.right, sb);
        }
    }

    public String toStringInOrder()
    {
        ArrayList<T> list = inOrderValues(root, new ArrayList<>());

        return String.join(", ", list);
    }

    public ArrayList<T> inOrderValues(Node<T> current, ArrayList<T> values)
    {
        if (current != null)
        {
            inOrderValues(current.left, values);
            values.add(current.value);
            inOrderValues(current.right, values);
        }
    }

    private void toStringInOrderRecursive(Node<T> current, StringBuilder sb) {
        if (current != null)
        {
            toStringInOrderRecursive(current.left, sb);
            sb.append(current.value).append(", ");
            toStringInOrderRecursive(current.right, sb);
        }
    }

    public String toStringPostOrder() {
        StringBuilder sb = new StringBuilder();
        toStringPostOrderRecursive(root, sb);
        removeTrailingComma(sb);
        return sb.toString();
    }

    private void toStringPostOrderRecursive(Node<T> current, StringBuilder sb) {
        if (current != null) {
            toStringPostOrderRecursive(current.left, sb);
            toStringPostOrderRecursive(current.right, sb);
            sb.append(current.value).append(", ");
        }
    }

    private void removeTrailingComma(StringBuilder sb) {
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        private Node<T> parent;

        public Node(T value)
        {
            this.value = value;
        }

        public Node(T value, Node<T> parent)
        {
            this.value = value;
            this.parent = parent;
        }

        public int childrenAmount(){
            return (left != null ? 1 : 0) + (right != null ? 1 : 0);
        }
    }
}
