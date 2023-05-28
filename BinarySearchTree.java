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

    public boolean contains(T value)
    {
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

    // node z has exactly 2 children
    private void deleteNodeTwoChildren(Node<T> nodeToDelete)
    {
        Node<T> smallestNode = findSmallestNode(nodeToDelete.right);
        nodeToDelete.value = smallestNode.value;
        deleteNode(smallestNode);
    }

    private Node<T> findSmallestNode(Node<T> current)
    {
        return current.left == null ? current : findSmallestNode(current.left);
    }

    public String toStringPreOrder()
    {
        ArrayList<T> list = new ArrayList<>();
        preOrderValues(list);

        return String.join(", ", listToString(list));
    }

    public void preOrderValues(ArrayList<T> values)
    {
        preOrderValues(root, values);
    }

    public void preOrderValues(Node<T> current, ArrayList<T> values)
    {
        if (current != null)
        {
            values.add(current.value);
            preOrderValues(current.left, values);
            preOrderValues(current.right, values);
        }
    }

    public String toStringInOrder()
    {
        ArrayList<T> list = new ArrayList<>();
        inOrderValues(list);

        return String.join(", ", listToString(list));
    }

    public void inOrderValues(List<T> values)
    {
        inOrderValues(root, values);
    }

    public void inOrderValues(Node<T> current, List<T> values)
    {
        if (current != null)
        {
            inOrderValues(current.left, values);
            values.add(current.value);
            inOrderValues(current.right, values);
        }
    }

    public String toStringPostOrder()
    {
        ArrayList<T> list = new ArrayList<>();
        postOrderValues(list);

        return String.join(", ", listToString(list));
    }

    public void postOrderValues(ArrayList<T> values)
    {
        postOrderValues(root, values);
    }

    public void postOrderValues(Node<T> current, ArrayList<T> values)
    {
        if (current != null)
        {
            postOrderValues(current.left, values);
            postOrderValues(current.right, values);
            values.add(current.value);
        }
    }

    public ArrayList<String> listToString(ArrayList<T> list)
    {
        ArrayList<String> strings = new ArrayList<>();
        for (T val : list)
        {
            strings.add(val.toString());
        }

        return strings;
    }

    private static class Node<T> {
        private T value;
        private Node<T> parent;

        private Node<T> left;
        private Node<T> right;

        public Node(T value)
        {
            this.value = value;
        }

        public Node(T value, Node<T> parent)
        {
            this.value = value;
            this.parent = parent;
        }

        public int childrenAmount()
        {
            return (left != null ? 1 : 0) + (right != null ? 1 : 0);
        }
    }
}
