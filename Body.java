/**
 *  Represents a celestial body.
 */

public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double G = 6.67E-11D;

    /**
     * Creates a body from given parameters.
     * @param xP x position
     * @param yP y position
     * @param xV x Velocity
     * @param yV y Velocity
     * @param m mass
     * @param img body image location
     */
    public Body(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /**
     * Creates  copy of a body.
     * @param b Body whose copy is to be made.
     */
    public Body(Body b){
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    /**
     * Calculates the distance between this and Body b.
     * @param b the body to which distance is measured.
     * @return distance between THIS and b as double.
     */
    public double calcDistance(Body b){
        return Math.sqrt(Math.pow(b.xxPos - xxPos, 2) +
                Math.pow(b.yyPos - yyPos, 2));
    }

    /**
     * Calculates the force exerted by a body b on THIS.
     * @param b The body which is exerting force.
     * @return The force exerted by b on THIS.
     */
    public double calcForceExertedBy(Body b){
        if(this.equals(b)){
            return 0.0D;
        }
        return (G * b.mass * mass) / Math.pow(calcDistance(b),2);
    }

    /**
     * Signed x distance between Body a and Body b, i.e. b.xxpos - a.xxpos
     * @param a Body a
     * @param b Body b
     * @return dx = x distance between a and b.
     */
    private static double dx(Body a, Body b){
        return b.xxPos - a.xxPos;
    }

    /**
     * Signed y distance between Body a and Body b, i.e. b.yypos - a.yypos
     * @param a Body a
     * @param b Body b
     * @return dy = y distance between a and b.
     */
    private static double dy(Body a, Body b){
        return b.yyPos - a.yyPos;
    }

    /**
     * Calculates force exerted by b on THIS in x direction.
     * @param b Body exerting force on THIS.
     * @return Force exerted by b on THIS in x direction.
     */
    public double calcForceExertedByX(Body b){
        return calcForceExertedBy(b) * Body.dx(this, b) / calcDistance(b);
    }

    /**
     * Calculates force exerted by b on THIS in y direction.
     * @param b Body exerting force on THIS.
     * @return Force exerted by b on THIS in y direction.
     */
    public double calcForceExertedByY(Body b){
        return calcForceExertedBy(b) * Body.dy(this, b) / calcDistance(b);
    }

    /**
     * Calculates net force exerted in x direction by all bodies in bodyArray.
     * @param bodyArray An array of bodies in the simulation.
     * @return The net force exerted by all bodies in x direction.
     */
    public double calcNetForceExertedByX(Body[] bodyArray){
        double netForceX = 0;
        for(Body b : bodyArray){
            if(!this.equals(b)){
              netForceX += calcForceExertedByX(b);
            }
        }
        return netForceX;
    }

    /**
     * Calculates net force exerted in y direction by all bodies in bodyArray.
     * @param bodyArray An array of bodies in the simulation.
     * @return The net force exerted by all bodies in y direction.
     */
    public double calcNetForceExertedByY(Body[] bodyArray){
        double netForceY = 0;
        for(Body b : bodyArray){
            if(!this.equals(b)){
                netForceY += calcForceExertedByY(b);
            }
        }
        return netForceY;
    }

    /**
     * Updates velocity and position of an object when force fx and fy is
     * applied in x and y directions respectively for a time dt.
     * @param dt The time duration for which force is applied.
     * @param fX Force applied in x direction.
     * @param fY Force applied in y direction.
     */
    public void update(double dt, double fX, double fY){
        double accX = fX/mass;
        double accY = fY/mass;

        xxVel += accX * dt;
        yyVel += accY * dt;

        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    /**
     * Uses StdDraw.picture() to draw the body on the screen.
     */
    public void draw(){
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}
