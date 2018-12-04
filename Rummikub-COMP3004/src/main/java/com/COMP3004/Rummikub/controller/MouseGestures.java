package com.COMP3004.Rummikub.controller;

import java.util.Collection;

import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Duration;
import com.COMP3004.Rummikub.models.Tile;
import javafx.scene.shape.Rectangle;

public class MouseGestures {
	
	private double mouseX;
	private double mouseY;

    final DragContext dragContext = new DragContext();

    public void makeDraggable(final Node node) {

        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
     //   node.setOnMouseReleased(onMouseReleasedEventHandler);

    }

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
        	System.out.println("Mouse pressed");
         //   dragContext.x = event.getSceneX();
         //   dragContext.y = event.getSceneY();
        	
        	mouseX = event.getSceneX();
        	mouseY = event.getSceneY();
            
            //System.out.println(dragContext.x);
            //System.out.println(dragContext.y);

        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();
          
          

           //double offsetX = event.getSceneX() - dragContext.x;
           //double offsetY = event.getSceneY() - dragContext.y;
            double offsetX = event.getSceneX() - mouseX;
            double offsetY = event.getSceneY() - mouseY;
        //   System.out.println(node.getLayoutX());
        //   System.out.println(node.getLayoutY());
            
            node.setTranslateX(offsetX);
            node.setTranslateY(offsetY);
            node.relocate(node.getLayoutX()+offsetX, node.getLayoutY()+offsetY);
            mouseX = event.getSceneX();
        	mouseY = event.getSceneY();
           // node.setTranslateX(offsetX);
            //node.setTranslateY(offsetY);
            
            
            //node.setLayoutX(node.getLayoutX() - 10);
            //node.setLayoutY(node.getLayoutY() - 25);
            //((Tile)node).setX(node.getLayoutX() - 10);
            //((Tile)node).setY(node.getLayoutY() - 25);
           // node.relocate(node.getLayoutX() + offsetX, node.getLayoutY() + offsetY);
           // dragContext.x = offsetX ;
          //  dragContext.y = offsetY;
         //   node.setTranslateX(0);
          //  node.setTranslateY(0);


        }
    };

    
    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

    	 @Override
         public void handle(MouseEvent event) {

             Node node = (Node) event.getSource();
             
             System.out.println(node.getLayoutX());
             
        //      System.out.println(node.getLayoutX());
             // System.out.println(node.getTranslateX());
       //       System.out.println(node.getLayoutY());
           //   System.out.println(node.getTranslateY());
              //node.setTranslateY(0);

            // moveToSource(node);

             

             // if you find out that the cards are on a valid position, you need to fix it, ie invoke relocate and set the translation to 0
              fixPosition( node);

         }
    };

    private void moveToSource( Node node) {
        double sourceX = node.getLayoutX() + node.getTranslateX();
        double sourceY = node.getLayoutY() + node.getTranslateY();

        double targetX = node.getLayoutX();
        double targetY = node.getLayoutY();

        Path path = new Path();
        path.getElements().addAll( new MoveToAbs( node, sourceX, sourceY));
        path.getElements().addAll(new LineToAbs( node, targetX, targetY));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(node);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);

        pathTransition.play();
    }

    /**
     * Relocate card to current position and set translate to 0.
     * @param node
     */
    private void fixPosition( Node node) {

        double x = node.getTranslateX();
        double y = node.getTranslateY();
       

        node.relocate(node.getLayoutX(), node.getLayoutY());
        //node.relocate(5, 5);
    /*    System.out.println(node.getLayoutX());
        ((Tile)node).setX(node.getLayoutX());
        System.out.println(node.getLayoutY());
         ((Tile)node).setY(node.getLayoutY());
*/
        node.setTranslateX(0);
        node.setTranslateY(0);

    }

    class DragContext {

        double x;
        double y;

    }

    // pathtransition works with the center of the node => we need to consider that
    public static class MoveToAbs extends MoveTo {

        public MoveToAbs( Node node, double x, double y) {
            super( x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2, y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);

        }

    }

    // pathtransition works with the center of the node => we need to consider that
    public static class LineToAbs extends LineTo {

        public LineToAbs( Node node, double x, double y) {
            super( x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2, y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
        }

    }

}