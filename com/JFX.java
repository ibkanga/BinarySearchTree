package com;

import com.BST.TreeNode;
import java.util.ArrayList;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class JFX extends Application {
    
    private BST<Integer> tree;
    private ArrayList<TreeNode<Integer>> searchPath;
    private ArrayList<Integer> orderPath;
    
    private final int radius = 30;
    private final int vGap = 85;   
    private final int startH = 45;
    
    Scene scene;
    BorderPane root;
    Pane wrapperPane;
    
    Canvas canvas = new Canvas(1270, 600);
    GraphicsContext gc = canvas.getGraphicsContext2D();   
    int width = (int) canvas.getWidth();
    int height = (int) canvas.getHeight();
        
    Button insert, delete, search, reset, inorder, preorder, postorder;
    
    Label keyL;
    TextField keyTF;
    
    HBox commands, show;
    VBox menu;   
    
    @Override
    public void start(Stage primaryStage) {
        
        tree = new BST<>();
        searchPath = new ArrayList<>();
        orderPath = new ArrayList<>();
        
        gc.clearRect(0, 0, width, height);       
        
        insert = new Button("Insert");
        delete = new Button("Delete");
        search = new Button("Search");
        reset = new Button("Reset");
        inorder = new Button("Show Inorder");
        preorder = new Button("Show Preorder");
        postorder = new Button("Show Postorder");
              
        keyL = new Label("Enter a key: ");
        keyL.setTextFill(Color.WHITE);
               
        keyTF = new TextField();
        keyTF.setPrefWidth(60);
       
        root = new BorderPane();       
        wrapperPane = new Pane();       
        wrapperPane.getChildren().add(canvas);
        wrapperPane.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");
        root.setCenter(wrapperPane);
            
        commands = new HBox();
        commands.setAlignment(Pos.CENTER);
        show = new HBox();
        show.setAlignment(Pos.CENTER);
        menu = new VBox();
        
        commands.getChildren().addAll(keyL, keyTF, insert, delete, search, reset);
        show.getChildren().addAll(preorder, inorder, postorder);   
        menu.getChildren().addAll(commands, show);
        root.setBottom(menu);

        commands.setPadding(new Insets(15));
        commands.setSpacing(10);
        commands.setStyle("-fx-background-color: #ff6600; -fx-border-color: black; -fx-border-width: 2px; -fx-border-style: hidden solid solid solid;");
       
        show.setPadding(new Insets(15));    
        show.setSpacing(10);
        show.setStyle("-fx-background-color: #336699; -fx-border-color: black; -fx-border-width: 2px; -fx-border-style: hidden solid solid solid;");
                    
        insert.setOnAction((ActionEvent event) -> {            
            String key = keyTF.getText().replaceAll("\\s+", "");
            keyTF.clear();
            if (key.equals("")) {               
                return;
            }
            
            insertDeleteSearch(1, key);
        });
        
        delete.setOnAction((ActionEvent event) -> {
            if(tree.isEmpty()) {
                keyTF.clear();
                JOptionPane.showMessageDialog(null, "The tree is empty!", "EMPTY TREE!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String key = keyTF.getText().replaceAll("\\s+", "");
            keyTF.clear();
            if (key.equals("")) {               
                return;
            }
            
            insertDeleteSearch(-1, key);           
        });
        
        search.setOnAction((ActionEvent event) -> {          
            if(tree.isEmpty()) {
                keyTF.clear();
                JOptionPane.showMessageDialog(null, "The tree is empty!", "EMPTY TREE!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
                       
            String key = keyTF.getText().replaceAll("\\s+", "");
            keyTF.clear();
            if (key.equals("")) {               
                return;
            }
            
            insertDeleteSearch(0, key);
            searchPath = new ArrayList<>();           
        }); 
        
        reset.setOnAction((ActionEvent event) -> {
            tree.clear();
            gc.clearRect(0, 0, 1270, 600);
            drawTree(tree.root, width/2, startH, width/4);           
        });
        
        preorder.setOnAction((ActionEvent event) -> {   
            if(tree.isEmpty()) {
                JOptionPane.showMessageDialog(null, "The tree is empty!", "EMPTY TREE!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            ordering(-1);  
        });
        
        inorder.setOnAction((ActionEvent event) -> {   
            if(tree.isEmpty()) {
                JOptionPane.showMessageDialog(null, "The tree is empty!", "EMPTY TREE!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            ordering(0);
        });
        
        postorder.setOnAction((ActionEvent event) -> {    
            if(tree.isEmpty()) {
                JOptionPane.showMessageDialog(null, "The tree is empty!", "EMPTY TREE!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            ordering(1);
        });
        
        
        scene = new Scene(root, 1260, 700);
        
        primaryStage.setTitle("Binary Search Tree!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }   
    
    private void ordering(int orderFunction) {
        
        String messageString, title;
        
        switch (orderFunction) {
            case -1:
                orderPath = tree.preorderPath();
                messageString = "Preorder: ";
                title = "PREORDER";
                break;
            case 0:
                orderPath = tree.inorderPath();
                messageString = "Inorder: ";
                title = "INORDER";
                break;
            default:
                orderPath = tree.postorderPath();
                messageString = "Postorder: ";
                title = "POSTORDER";
                break;
        }
            
        gc.clearRect(0, 0, 1270, 600);
        drawTree(tree.root, width/2, startH, width/4);

        for (Integer orderNum : orderPath) {
            messageString += orderNum + " ";
        }
           
        orderPath = new ArrayList<>();                      
        JOptionPane.showMessageDialog(null, messageString, title, JOptionPane.INFORMATION_MESSAGE);  
    }
    
    private void insertDeleteSearch(int n, String key) {
        
        boolean match;
        match = key.matches("^-?[0-9]{1,3}$");
        if(match) {
            int keyINT = Integer.parseInt(key);
            boolean isInTree = tree.search(keyINT);
            if(isInTree) {
                switch (n) {
                    case -1:
                        tree.delete(keyINT);
                        break;
                    case 0:
                        searchPath = tree.path(keyINT);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, keyINT + " is already in the tree!", "CANNOT INSERT!", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
                    
            }
            else {
                if(n == 1)
                    tree.insert(keyINT);
                else       
                    JOptionPane.showMessageDialog(null, keyINT + " is not in the tree!", "NOT FOUND!", JOptionPane.INFORMATION_MESSAGE);
            }
        }                
        else
            JOptionPane.showMessageDialog(null, "Enter a 1-3 digit (pos or neg) number!\nExample: 0, 14, -724, 3332", "INCORRECT INPUT!", JOptionPane.INFORMATION_MESSAGE);
        
        
        gc.clearRect(0, 0, 1270, 600);
        drawTree(tree.root, width/2, startH, width/4);
    }
           
    private void drawTree(BST.TreeNode<Integer> root, int x, int y, int hGap) {
        if(!tree.isEmpty()) {    
            
            gc.setLineWidth(1.5);
            gc.setStroke(Color.BLACK);
            
            if(!searchPath.isEmpty()) {
                for(TreeNode node : searchPath) {
                    if(Objects.equals(root.element, node.element) ) {
                        gc.setStroke(Color.RED);
                        break;
                    }
                    else {
                        gc.setStroke(Color.BLACK); 
                    }  
                }    
            }

            gc.strokeOval(x - radius, y - radius, 2 * radius, 2 * radius);
            gc.strokeText(root.element + "", x - 6, y + 4);

            for(int pos = 0; pos < orderPath.size(); pos++) {
                if(orderPath.get(pos).equals(root.element)) {
                    gc.strokeText(pos + 1 + "", x-50, y-4);
                }
            }

            if (root.left != null) {
                connectCircles(x - hGap, y + vGap, x, y);
                drawTree(root.left, x - hGap, y + vGap, hGap / 2);
            }

            if (root.right != null) { 
                connectCircles(x + hGap, y + vGap, x, y);
                drawTree(root.right, x + hGap, y + vGap, hGap / 2);
            }
        }     
    }
         
    private void connectCircles(int x1, int y1, int x2, int y2) {
        
        double d = Math.sqrt(Math.pow(vGap, 2)  + Math.pow(x2 - x1, 2));
        int x11 = (int) (x1 - radius * (x1 - x2) / d);
        int y11 = (int) (y1 - radius * (y1 - y2) / d);
        int x21 = (int) (x2 + radius * (x1 - x2) / d);
        int y21 = (int) (y2 + radius * (y1 - y2) / d);
        
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x11, y11, x21, y21);
        
    }
    
}
