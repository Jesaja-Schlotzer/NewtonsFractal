import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to provide methods to work with the newton method.
 */
public class Newton {

    public static final double EPSILON = 0.000_001;

    /**
     * The range in which the roots will be searched.
     * Greater values take longer to compute, 5 should be ok for most cases.
     * If roots are missing adjust this range until the roots are not missing.
     */
    public static final double RANGE = 5;

    /**
     * The precision of the newton method.
     * Takes longer with smaller values.
     * If the polynomials roots are close together,
     * it may be needed to adjust the value to gain precision.
     */
    public static final double STEP_SIZE = 0.1;


    /**
     * Approximates all real roots of a polynomial.
     * @param polynomial the polynomial to calculate the roots from
     * @param maxIterations the maximal number of times the newton method will be applied
     * @return all real roots of the polynomial
     */
    public static double[] newton(Polynomial polynomial, int maxIterations) {
        ComplexPolynomial complexPolynomial = Polynomial.convertToComplexPolynomial(polynomial);
        List<ComplexNumber> roots = newtonComplex(complexPolynomial, maxIterations);

        ArrayList<Double> realRoots = new ArrayList<>();

        roots.forEach(z -> {
            if(z.imaginary() == 0.0) {
                realRoots.add(z.real());
            }
        });

        return realRoots.stream().mapToDouble(Double::doubleValue).toArray();
    }


    /**
     * Approximates all roots of a polynomial.
     * @param polynomial the polynomial to calculate the roots from
     * @param maxIterations the maximal number of times the newton method will be applied
     * @return all roots of the polynomial
     */
    public static List<ComplexNumber> newtonComplex(ComplexPolynomial polynomial, int maxIterations) {
        List<ComplexNumber> roots = new ArrayList<>();

        boolean rootIsKnown = false;

        ComplexNumber x;
        ComplexNumber startPoint = new ComplexNumber(0, 0);

        for (double i = -RANGE; i < RANGE; i += STEP_SIZE) {
            for (double j = -RANGE; j < RANGE; j += STEP_SIZE) {
                startPoint.setReal(i);
                startPoint.setImaginary(j);

                x = newtonComplex(polynomial, startPoint, maxIterations);

                for (ComplexNumber c : roots) {
                    if (Math.abs(x.distanceTo(c)) < EPSILON) {
                        rootIsKnown = true;
                        break;
                    }
                }

                if (!rootIsKnown) {
                    if(x.imaginary() == 0.0f) {
                        roots.add(new ComplexNumber(x));
                        if(roots.size() == polynomial.degree()) {
                            return roots;
                        }
                    }else {
                        roots.add(new ComplexNumber(x));
                        x.setImaginary(-x.imaginary());
                        roots.add(new ComplexNumber(x));
                        if (roots.size() >= polynomial.degree()) {
                            return roots;
                        }
                    }
                }

                rootIsKnown = false;
            }
        }

        return roots;
    }


    /**
     * Approximates one root of a polynomial.
     * @param polynomial the polynomial to calculate the root from
     * @param startPoint the start point of the newton method
     * @param maxIterations the maximal number of times the newton method will be applied
     * @return a root of the polynomial
     */
    public static ComplexNumber newtonComplex(ComplexPolynomial polynomial, ComplexNumber startPoint, int maxIterations) {
        ComplexNumber slope = new ComplexNumber(), last = startPoint, x = null;
        int iteration = 0;

        while (iteration < maxIterations) {
            slope.set(polynomial.slope(last));
            // Fixing the issue when the slope at a given point is 0 and therefore would lead to a division by zero
            if(slope.equals(ComplexNumber.ZERO)) {
                slope.add(ComplexNumber.PRACTICALLY_ZERO);
            }

            x = last.subtract(polynomial.eval(last).divide((slope)));
            last = x;
            iteration++;

            if(ComplexNumber.ZERO.distanceTo(x) < EPSILON) {
                return x;
            }
        }

        if (iteration == 0) {
            return startPoint;
        }

        return x;
    }

}
