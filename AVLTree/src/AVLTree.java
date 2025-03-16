public class AVLTree {
    class Node {
        int value;

        Node left, right;

        public Node(int value){
            this.value = value;
            left = right = null;
        }
    }

    private String serialize(Node root) {
        if (root == null) {
            return "X";
        }

        String leftSerialized = serialize(root.left);

        String rightSerialized = serialize(root.right);

        return Integer.toString(root.value) + "," + leftSerialized+ "," + rightSerialized;
    }

    private Node root;

    private int nodeCount = 0;

    void insert(int value) {
        if (root == null) {
            root = new Node(value);
            return;
        }

        Node current = root;
        Node parentNode = null;

        while (current != null) {
            parentNode = current;

            if (value < current.value) {
                current = current.left;
            } else if (value > current.value) {
                current = current.right;
            } else {
                current = current.right;
            }
        }

        if (value < parentNode.value) {
            parentNode.left = new Node(value);
        } else {
            parentNode.right = new Node(value);
        }

        return;
    }

    void delete(int value) {
        Node current = root;
        Node parent = null;
    
        while (current != null && current.value != value) {
            parent = current;
            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
    
        if (current == null) {
            return;
        }
    
        if (current.left == null && current.right == null) {
            if (parent == null) {
                root = null;
            } else if (parent.left == current) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (current.left == null || current.right == null) {
            Node child = (current.left != null) ? current.left : current.right;
    
            if (parent == null) {
                root = child;
            } else if (parent.left == current) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        } else {
            Node successorParent = current;
            Node successor = current.right;
    
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
    
            current.value = successor.value;
    
            if (successorParent.left == successor) {
                successorParent.left = successor.right;
            } else {
                successorParent.right = successor.right;
            }
        }
    }

    String serialize() {
        return serialize(root);
    }
    
    
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
    
        // Insert values
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(7);
        tree.insert(13);
        tree.insert(17);
        
        System.out.println("Tree after insertion: " + tree.serialize());
    
        // Delete a leaf node
        tree.delete(3);
        System.out.println("Tree after deleting leaf (3): " + tree.serialize());
    
        // Delete a node with one child
        tree.delete(15);
        System.out.println("Tree after deleting node with one child (15): " + tree.serialize());
    
        // Delete a node with two children
        tree.delete(10);
        System.out.println("Tree after deleting node with two children (10): " + tree.serialize());
    }
    
}
