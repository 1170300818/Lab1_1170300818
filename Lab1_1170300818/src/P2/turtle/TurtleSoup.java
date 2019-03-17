/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;

import java.util.Set;
import java.lang.Double;


//import com.sun.prism.impl.Disposer.Target;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        double degrees;
        degrees=(180-(360/(double)sides));
    	return degrees;
    	//throw new RuntimeException("implement me!");
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        int sides;
        sides=(int)Math.round(360/(180-angle));
        return sides;
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle=calculateRegularPolygonAngle(sides);
    	for (int i=0;i<sides;i++) {
        	turtle.forward(sideLength);
        	turtle.turn(angle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double targetbearing=0;
        if (currentX==targetX) {
        	targetbearing=(-currentBearing);
        	return (targetbearing>=0)?targetbearing:(targetbearing+360);
        }
        double at=Math.toDegrees(Math.atan((double)(targetY - currentY)/(targetX - currentX)));
    	targetbearing=90-at-currentBearing;
    	return (targetbearing>=0)?targetbearing:(targetbearing+360 );  
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> ans=new ArrayList<Double>();
        int x1=0,x2=0,y1=0,y2=0;
        double tmp1=0;
		double tmp2=0;
        Iterator<Integer> x=xCoords.iterator();
        Iterator<Integer> y=yCoords.iterator();
        x1=x.next();
        y1=y.next();
        while ( x.hasNext() && y.hasNext() )
    	{   
        	x2 = x.next();
    		y2 = y.next();
    		tmp1 = calculateBearingToPoint(tmp2,x1,y1,x2,y2);
    		tmp2 = tmp2 + tmp1;
    		tmp2 = (tmp2<360)? tmp2 : (tmp2-360);
    		x1 = x2;
    		y1 = y2;

    		ans.add(tmp1);
    	}
        return ans;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    
	static Set<Point> ans = new HashSet<Point>();
    public static Set<Point> convexHull(Set<Point> points) {
    	if ( points.size() <= 2 )
    		return points;
    	List<Point> ps = new ArrayList<Point>(points);
    	ch(ps,true);
    	ch(ps,false);
    	return ans;
    }
    
    public static void ch(List<Point> points, boolean up){
    	Collections.sort(points, new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                return (int) (o1.x() != o2.x() ? o1.x() - o2.x() : o1.y() - o2.y());
            }
        });
    	int fir = 0;
        int lst = points.size() - 1;
        ans.add(points.get(fir));
        ans.add(points.get(lst));
        if (points.size() == 2)
            return;
        boolean isLine = true;
        for (int i = 0; i < points.size(); i++) {
            if (i == fir || i == lst)
                continue;
            if (calcuTriangle(points.get(fir), points.get(lst), points.get(i)) != 0) {
                isLine = false;
                break;
            }
        }
        if (isLine) {
            return;
        }
        int maxIndex = -1;
        double max = 0;
        for (int i = 0; i < points.size(); i++) {
            if (i == fir || i == lst)
                continue;
            if (up && calcuTriangle(points.get(fir), points.get(lst), points.get(i)) > max) {
                maxIndex = i;
                max = calcuTriangle(points.get(fir), points.get(lst), points.get(i));
            }
            if (!up && -calcuTriangle(points.get(fir), points.get(lst), points.get(i)) > max) {
                maxIndex = i;
                max = -calcuTriangle(points.get(fir), points.get(lst), points.get(i));
            }
        }
        if (maxIndex == -1) {
            return;
        }
        List<Point> c1 = new ArrayList<>();
        split(fir, maxIndex, points, c1, up);
        ch(c1,up);

        List<Point> c2 = new ArrayList<>();
        split(maxIndex, lst, points, c2, up);
        ch(c2,up);
        return;
    }
    private static void split(int a1, int a2, List<Point> points, List<Point> part, boolean up) {
        for (int i = 0; i < points.size(); i++) {
            if (i == a1 || i == a2) {
                part.add(points.get(i));
                continue;
            }
            if (up && calcuTriangle(points.get(a1), points.get(a2), points.get(i)) >= 0) {
                part.add(points.get(i));
            }

            if (!up && calcuTriangle(points.get(a1), points.get(a2), points.get(i)) <= 0) {
                part.add(points.get(i));
            }
        }
    }   
    
    public static double calcuTriangle(Point a1, Point a2, Point a3) {
        return a1.x() * a2.y() + a3.x() * a1.y() + a2.x() * a3.y() 
        		- a3.x() * a2.y() - a2.x() * a1.y() - a1.x() * a3.y();
    }   
    
    
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        for (int i=0;i<360;i++) {
        	turtle.forward(1);
		    turtle.turn(1);
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawSquare(turtle, 40);

        // draw the window
        turtle.draw();
        System.out.println(Math.toDegrees((Math.atan(6.6))));
    }

}
