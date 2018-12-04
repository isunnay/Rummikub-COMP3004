package com.COMP3004.Rummikub.controller;

import java.util.Collection;

import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
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
        node.setOnMouseReleased(onMouseReleasedEventHandler);

    }

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();

        }
    };



    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();
            node.setManaged(false);

            double offsetX = event.getSceneX() - dragContext.x;
            double offsetY = event.getSceneY() - dragContext.y;

            node.setTranslateX(offsetX);
            node.setTranslateY(offsetY);


        }
    };

    
    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();

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

        node.relocate(node.getLayoutX() + x, node.getLayoutY() + y);

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