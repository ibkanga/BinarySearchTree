package com;

import java.util.ArrayList;

public class BST<E extends Comparable<E>> {
    
    protected TreeNode<E> root;
    protected int size = 0;

    /** Create a default binary tree */
    public BST() {
    }

    /** Create a binary tree from an array of objects */
    public BST(E[] objects) {
        for (E object : objects) {
            insert(object);
        }
    }

    /** Returns true if the element is in the tree */
    public boolean search(E e) {
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else // element matches current.element
                return true; // Element is found
        }

        return false;
    }
  
    /** Insert element e into the binary tree
    * Return true if the element is inserted successfully */
    public boolean insert(E e) {
        if (root == null)
            root = createNewNode(e); // Create a new root
        else {
            // Locate the parent node
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null)
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                }
                else
                    return false; // Duplicate node not inserted

            // Create the new node and attach it to the parent node
            if (e.compareTo(parent.element) < 0)
                parent.left = createNewNode(e);
            else
                parent.right = createNewNode(e);
        }

        size++;
        return true; // Element inserted
    }

    /** Create a new node */
    protected TreeNode<E> createNewNode(E e) {
        return new TreeNode<>(e);
    }

    /** Inorder traversal from the root */
    public void inorder() {
        inorder(root);
    }

    /** Inorder traversal from a subtree */
    protected void inorder(TreeNode<E> root) {
        if (root == null) 
            return;
        inorder(root.left);
        System.out.print(root.element + " ");
        inorder(root.right);
    }

    /** Postorder traversal from the root */
    public void postorder() {
        postorder(root);
    }

    /** Postorder traversal from a subtree */
    protected void postorder(TreeNode<E> root) {
        if (root == null) 
            return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.element + " ");
    }

    /** Preorder traversal from the root */
    public void preorder() {
        preorder(root);
    }

    /** Preorder traversal from a subtree */
    protected void preorder(TreeNode<E> root) {
        if (root == null) 
            return;

        System.out.print(root.element + " ");
        preorder(root.left);
        preorder(root.right);
    }
  
    /** Saving elements in the ArrayList while preorder traversing from the root 
    *  Return ArrayList of elements */
    public ArrayList<E> preorderPath() {
        ArrayList<E> path = new ArrayList<>();
        preorderPath(path, root);
        return path;
    }
  
    /** Saving elements in the ArrayList while preorder traversing from a subtree */
    protected void preorderPath(ArrayList<E> result, TreeNode<E> root) {
        if (root == null)
            return;
        result.add(root.element); 
        preorderPath(result, root.left);
        preorderPath(result, root.right);
    } 
    
    /** Saving elements in the ArrayList while inorder traversing from the root 
    *  Return ArrayList of elements */
    public ArrayList<E> inorderPath() {
        ArrayList<E> result = new ArrayList<>();
        inorderPath(result, root);
        return result;
    }
  
    /** Saving elements in the ArrayList while inorder traversing from a subtree */
    protected void inorderPath(ArrayList<E> result, TreeNode<E> root) {
        if (root == null)
            return;
        inorderPath(result, root.left);
        result.add(root.element);
        inorderPath(result, root.right);
    } 
    
    /** Saving elements in the ArrayList while postorder traversing from the root 
    *  Return ArrayList of elements */
    public ArrayList<E> postorderPath() {
        ArrayList<E> result = new ArrayList<>();
        postorderPath(result, root);
        return result;
    }
  
    /** Saving elements in the ArrayList while postorder traversing from a subtree */
    protected void postorderPath(ArrayList<E> result, TreeNode<E> root) {
        if (root == null)
            return;
        postorderPath(result, root.left);
        postorderPath(result, root.right);
        result.add(root.element);
    } 

    /** This inner class is static, because it does not access 
        any instance members defined in its outer class */
    public static class TreeNode<E extends Comparable<E>> {
        protected E element;
        protected TreeNode<E> left;
        protected TreeNode<E> right;

        public TreeNode(E e) {
            element = e;
        }
    }

    /** Get the number of nodes in the tree */
    public int getSize() {
        return size;
    }
    
    /** Return true if the tree is empty */
    public boolean isEmpty() {
        return getSize() == 0;
    }

    /** Returns the root of the tree */
    public TreeNode<E> getRoot() {
        return root;
    }

    /** Returns a path from the root leading to the specified element */
    public ArrayList<TreeNode<E>> path(E e) {
        ArrayList<TreeNode<E>> list =  new ArrayList<>();
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            list.add(current); // Add the node to the list
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else
                break;
        }

        return list; // Return an array of nodes
    }
    
    /** Delete an element from the binary tree.
    * Return true if the element is deleted successfully
    * Return false if the element is not in the tree */
    public boolean delete(E e) {
        // Locate the node to be deleted and also locate its parent node
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
                
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            }
            else
                break; // Element is in the tree pointed at by current
        }

        if (current == null)
            return false; // Element is not in the tree

        // Case 1: current has no left children
        if (current.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = current.right;
            }
            else {
                if (e.compareTo(parent.element) < 0)
                    parent.left = current.right;
                else
                     parent.right = current.right;
            }
        }
        else {
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost)
                parentOfRightMost.right = rightMost.left;
            else
                // Special case: parentOfRightMost == current
                parentOfRightMost.left = rightMost.left;     
        }

        size--;
        return true; // Element inserted
    }

    /** Remove all elements from the tree */
    public void clear() {
        root = null;
        size = 0;
    }
}

