/**
 * Simulation of N bodies in a universe.
 */
public class NBody {
    /**
     * Reads radius from a universe file.
     * @param path Relative path of universe file
     * @return Radius of universe.
     */
    public static double readRadius(String path){
        In in = new In(path);
        int numBodies = in.readInt();
        return in.readDouble();
    }

    /**
     * Creates an array of bodies from a universe file.
     * @param path path to universe file.
     * @return Array of Body
     */
    public static Body[] readBodies(String path){
        In in = new In(path);
        int num_bodies = in.readInt();
        double radius = in.readDouble();
        Body[] bodies = new Body[num_bodies];
        for(int i = 0; i < num_bodies; i++){
            double xpos = in.readDouble();
            double ypos = in.readDouble();
            double xvel = in.readDouble();
            double yvel = in.readDouble();
            double mass = in.readDouble();
            String img = "images/" + in.readString();
            bodies[i] = new Body(xpos, ypos, xvel, yvel, mass, img);
        }
        return bodies;
    }

    /**
     * Simulates universe.
     * @param totalTime Total time of simulation.
     * @param dt Update interval. smaller interval leads to
     *           longer and more accurate simulation.
     * @param path path of universe file.
     * @param bodies Array of bodies in universe.
     */
    private static void createSimulation(double totalTime, double dt, String path, Body[] bodies){

        StdDraw.enableDoubleBuffering(); // Double buffering for smoother animation.
        double radius = NBody.readRadius(path);
        StdDraw.setScale(-radius, radius); // Scale window to radius of universe
        double time = 0.0D;

        while(!(time >totalTime)){

            StdDraw.clear();
            StdDraw.picture(0,0,"images/starfield.jpg");

            // xForces[i] = Force applied on Body[i] by all other
            // bodies in x direction
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];

            for(int i = 0; i < bodies.length; i++){
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            // Update positions of bodies and draw them.
            for(int i = 0; i < bodies.length; i++){
                bodies[i].update(dt, xForces[i], yForces[i]);
                bodies[i].draw();
            }

            StdDraw.show();
            StdDraw.pause(10);

            time += dt;
        }
    }

    /**
     * Processes input and starts simulation.
     * @param args 1) Total time of simulation.
     *             2) Interval used to update simulation, smaller
     *                value will lead to more accurate but longer
     *                simulation.
     *             3) Universe file path.
     */
    public static void main(String[] args){
        double totalTime = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);

        String filename = args[2];

        Body[] bodies = NBody.readBodies(filename);

        createSimulation(totalTime, dt, filename, bodies);
    }
}
