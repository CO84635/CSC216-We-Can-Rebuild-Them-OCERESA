public class AVLTree {
    class Node {
        int value;
        int bf;
        int height;
        Node left, right;

        public Node(int value){
            this.value = value;
            this.height = 1;
            this.bf = 0;
            left = right = null;
        }
    }

    private Node root;

    private void update(Node node) {
        int leftNodeHeight = (node.left == null) ? 0 : node.left.height;
        int rightNodeHeight = (node.right == null) ? 0 : node.right.height;

        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
        node.bf = rightNodeHeight - leftNodeHeight;
    }

    private String serialize(Node root) {
        if (root == null) {
            return "X";
        }

        String leftSerialized = serialize(root.left);
        String rightSerialized = serialize(root.right);
        return Integer.toString(root.value) + "," + leftSerialized+ "," + rightSerialized;
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }

        update(node);

        return balance(node);
    }

    public void insert(int value) {
        root = insert(root, value);
    }

    private Node balance(Node node) {
        if (node.bf == -2) {
            if (node.left.bf <= 0) {
                return leftLeftCase(node);
            } else {
                return leftRightCase(node);
            }
        } else if (node.bf == +2){
            if (node.right.bf >= 0) {
                return rightRightCase(node);
            } else {
                return rightLeftCase(node);
            }
        }

        return node;
    }

    private Node leftLeftCase(Node node) {
        return rightRotation(node);
    }

    private Node leftRightCase(Node node) {
        node.left = leftRotation(node.left);
        return leftLeftCase(node);
    }

    private Node rightRightCase(Node node) {
        return leftRotation(node);
    }

    private Node rightLeftCase(Node node) {
        node.right = rightRotation(node.right);
        return rightRightCase(node);
    }

    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;

        update(node);
        update(newParent);

        return newParent;
    }

    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;

        update(node);
        update(newParent);

        return newParent;
    }

    private Node remove(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = remove(node.left, value);
        } else if (value > node.value) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.height > node.right.height) {
                    int sucessorValue = (findMaxNode(node.left)).value;
                    node.value = sucessorValue;

                    node.left = remove(node.left, sucessorValue);
                } else {
                    int sucessorValue = (findMinNode(node.right)).value;
                    node.value = sucessorValue;

                    node.right = remove(node.right, sucessorValue);
                }
            }
        } 
        
        update(node);

        return balance(node);
    }

    void delete(int value) {
        root = remove(root, value);
    }
    
    private Node findMinNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node findMaxNode(Node node) {
        Node current = node;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    String serialize() {
        return serialize(root);
    }
    
    public static void main(String[] args) {
        {
            AVLTree tree = new AVLTree();

            System.out.println(tree.serialize());
        }
        
        {
            AVLTree tree = new AVLTree();
            tree.insert(3);
            tree.insert(4);
            tree.insert(5);
            tree.insert(6);

            System.out.println(tree.serialize());

            tree.delete(6);

            System.out.println(tree.serialize());
        }

        {
            AVLTree tree = new AVLTree();
            tree.insert(10);
            tree.insert(20);
            tree.insert(30);

            System.out.println(tree.serialize());
        }

        {
            AVLTree tree = new AVLTree();
            tree.insert(30);
            tree.insert(20);
            tree.insert(10);

            System.out.println(tree.serialize());
        }

        {
            AVLTree tree = new AVLTree();
            tree.insert(30);
            tree.insert(10);
            tree.insert(20);

            System.out.println(tree.serialize());
        }

        {
            AVLTree tree = new AVLTree();
            tree.insert(10);
            tree.insert(20);
            tree.insert(30);
            tree.delete(20);
            tree.delete(10);
            tree.delete(30);

            System.out.println(tree.serialize());
        }
    }
}
