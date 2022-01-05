import java.awt.*;
import java.util.*;
import java.util.List;

public class Renderer {

    /**
     * A seed value to make random generated polynomials reproducible
     */
    public static final int POLYNOMIAL_SEED = 42;

    /**
     * A seed value to make the random generated colors reproducible
     */
    public static final int COLOR_SEED = "colour seed".hashCode();

    public static final Random POLYNOMIAL_RANDOM = new Random(POLYNOMIAL_SEED);
    public static final Random COLOR_RANDOM = new Random(COLOR_SEED);


    /**
     * How far the grid is rendered in the x and y direction.
     */
    public static final double RANGE = 2.5;

    /**
     * The precision of the rendered polynomial or fractal
     */
    public static final double STEP_SIZE = (2 * RANGE) / Window.IMAGE_WIDTH;


    /**
     * Draws the polynomial or fractal on an image.
     * @param g the awt graphics object used for drawing
     */
    public void render(Graphics2D g) {


        /*  Polynomial plot

        g.setStroke(new BasicStroke(4));

        drawGrid(g);
        drawAxes(g);

        Polynomial polynomial = new Polynomial(-1, -1, 1, 0, 0, 1);


        g.setColor(new Color(255, 70, 70));
        plotPolynomial(g, polynomial);

        g.setColor(new Color(255, 226, 74));
        plotPolynomial(g, polynomial.derive());

        g.setColor(new Color(107, 255, 74));
        plotPolynomial(g, polynomial.derive().derive());

        g.setColor(new Color(72, 255, 228));
        plotPolynomial(g, polynomial.derive().derive().derive());

        g.setColor(new Color(72, 173, 255));
        plotPolynomial(g, polynomial.derive().derive().derive().derive());


        // Draws the zero points of the polynomial
        System.out.println("---------- ROOTS ----------");
        g.setColor(new Color(74, 186, 255));
        Arrays.stream(Newton.newton(polynomial, 100)).forEach(x -> {

            System.out.println();
            System.out.printf("%.16f%n", x);
            System.out.printf("%.16f%n", polynomial.eval(x));
            System.out.println();

            x *= Window.IMAGE_WIDTH/RANGE;
            x += 0.5 * Window.IMAGE_WIDTH;

            g.fillRect(((int) x)-5, (Window.IMAGE_HEIGHT/2)-5, 10, 10);
        });
        System.out.println();
        System.out.println();
        */


        // Fractals

        plotNewtonsFractal(g, new ComplexPolynomial(1, -1, 1, 0, 0, 1), 25);

        //plotNewtonsFractal(g, new ComplexPolynomial(-1, 0, 0, 1), 25);

        //plotNewtonsFractal(g, ComplexPolynomial.GENERATE_RANDOM(10), 25);

    }



    /**
     * Draws the x- and y-axis.
     * @param g the awt graphics object used for drawing
     */
    private static void drawAxes(Graphics2D g) {
        g.setColor(Color.WHITE);

        g.drawLine(0, Window.IMAGE_HEIGHT /2, Window.IMAGE_WIDTH, Window.IMAGE_HEIGHT /2);
        g.drawLine(Window.IMAGE_WIDTH /2, 0, Window.IMAGE_WIDTH /2, Window.IMAGE_HEIGHT);
    }


    /**
     * Draws a grid.
     * @param g the awt graphics object used for drawing
     */
    private static void drawGrid(Graphics2D g) {
        g.setColor(new Color(100, 100, 100, 50));

        for (int x = -Window.IMAGE_WIDTH /2; x < Window.IMAGE_WIDTH; x += (Window.IMAGE_WIDTH / RANGE)) {
            for (int y = -Window.IMAGE_HEIGHT /2; y < Window.IMAGE_HEIGHT; y += (Window.IMAGE_HEIGHT / RANGE)) {
                g.drawLine(0, y, Window.IMAGE_WIDTH, y);
                g.drawLine(x, 0, x, Window.IMAGE_HEIGHT);
            }
        }
    }


    /**
     * Plots the given polynomial.
     * @param g the awt graphics object used for drawing
     * @param polynomial the polynomial to plot
     */
    public static void plotPolynomial(Graphics2D g, Polynomial polynomial) {
        double lastX = -RANGE, lastY = 0;
        for(double i = -RANGE; i < RANGE; i += STEP_SIZE) {
            double x = i;
            double y = -polynomial.eval(i);

            x *= Window.IMAGE_WIDTH / RANGE;
            y *= Window.IMAGE_HEIGHT / RANGE;

            x += 0.5 * Window.IMAGE_WIDTH;
            y += 0.5 * Window.IMAGE_HEIGHT;

            g.drawLine((int) lastX, (int) lastY, (int) x, (int) y);

            lastX = x;
            lastY = y;
        }
    }


    /**
     * Plots an array of polynomials.
     * The colors of the plotted polynomials are generated randomly.
     * @param g the awt graphics object used for drawing
     * @param polynomials the polynomials to plot
     */
    public static void plotPolynomials(Graphics2D g, Polynomial... polynomials) {
        float hue = COLOR_RANDOM.nextFloat();
         for(Polynomial p : polynomials) {
            g.setColor(Color.getHSBColor((hue += 0.069), 0.6f, 0.9f));
            plotPolynomial(g, p);
        }
    }


    /**
     * Plots the newtons fractal of a given polynomial.
     * @param g the awt graphics object used for drawing
     * @param polynomial the polynomial the fractal will be based on
     * @param maxSteps the maximum number that the newton's method will be applied to determine a pixels color
     */
    public static void plotNewtonsFractal(Graphics2D g, ComplexPolynomial polynomial, int maxSteps) {
        System.out.println("Plotting polynomial: \n" + polynomial.toString() + "\n");

        List<ComplexNumber> roots = Newton.newtonComplex(polynomial, polynomial.degree() * 10);

        System.out.println("Roots found:");
        roots.forEach((z) -> {
            System.out.println("z    = " + z);
            System.out.printf("f(z) = %.10f %.10fi%n%n", polynomial.eval(z).real(), polynomial.eval(z).imaginary());
        });
        System.out.println("\n");

        Map<ComplexNumber, Color> colors = new HashMap<>();
        // Generate random colors for each root
        float hue = COLOR_RANDOM.nextFloat();
        for (ComplexNumber z : roots) {
            colors.put(z, Color.getHSBColor((hue += 0.069), 0.6f, 0.9f));
        }

        int rowCounter = 0;

        ComplexNumber endPoint;
        ComplexNumber startPoint = new ComplexNumber();

        for (double i = -Renderer.RANGE; i < Renderer.RANGE; i += Renderer.STEP_SIZE) {
            for (double j = -Renderer.RANGE; j < Renderer.RANGE; j += Renderer.STEP_SIZE) {
                startPoint.setReal(i);
                startPoint.setImaginary(j);

                endPoint = Newton.newtonComplex(polynomial, startPoint, maxSteps);

                ComplexNumber nearestPoint = new ComplexNumber(Double.MAX_VALUE, Double.MAX_VALUE);
                for (ComplexNumber equalToZeroPoint : roots) {
                    if (nearestPoint.distanceTo(endPoint) > equalToZeroPoint.distanceTo(endPoint)) {
                        nearestPoint = equalToZeroPoint;
                    }
                }

                int x = (int) ((i + RANGE) * (Window.IMAGE_WIDTH / (2 * RANGE)));
                int y = (int) ((j + RANGE) * (Window.IMAGE_HEIGHT / (2 * RANGE)));

                g.setColor(colors.get(nearestPoint));
                g.drawRect(x, y, 1, 1);
            }

            rowCounter++;

            if(rowCounter % (int) (((2 * RANGE) / STEP_SIZE) / 10) == 0) {
                System.out.println((rowCounter / (int) (((2 * RANGE) / STEP_SIZE) / 10)) * 10 + " %");
            }
        }
        System.out.println("Plotting finished");
    }

}
