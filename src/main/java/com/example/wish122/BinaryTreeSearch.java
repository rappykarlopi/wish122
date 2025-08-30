package com.example.wish122;

import javafx.collections.ObservableList;

public class BinaryTreeSearch {

    private TreeNode root;

    public void insert(String name, Song song) {
        root = insertRec(root, name, song);
    }

    private TreeNode insertRec(TreeNode root, String name, Song song) {
        if (root == null) {
            root = new TreeNode(song);
            return root;
        }

        String[] words = name.split("\\s");

        // Compare the first word of each sentence
        int compareResult = words[0].compareTo(root.data.getName().split("\\s")[0]);

        if (compareResult < 0) {
            root.left = insertRec(root.left, name, song);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, name, song);
        }

        return root;
    }

    public void inOrderTraversal(ObservableList<Song> likedSongList) {
        inOrderTraversalRec(root, likedSongList);
    }

    public void inOrderTraversalRec(TreeNode root, ObservableList<Song> likedSongList) {
        if(root != null) {
            inOrderTraversalRec(root.left, likedSongList);
            likedSongList.add(root.data);
            inOrderTraversalRec(root.right, likedSongList);
        }
    }


}
