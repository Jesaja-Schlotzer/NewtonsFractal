import java.lang.Math;
import java.util.Arrays;

public class Polynomial {

    private final double[] coefficients;
    private final int degree;

    private Polynomial derivative;



    /**
     * Takes in the coefficients of the polynomial.
     * The first parameter takes place at x^0 the second at x^1 and so forth.
     * Invalid values (NaN and Infinity) are mapped to 0.
     * @param coefficients of the polynomial
     */
    public Polynomial(double... coefficients) {
        boolean foundNonZeroCoeff = false;
        int zeroCoeffCount = 0;
        for (int i = coefficients.length-1; i >= 0; i--) {
            if(!foundNonZeroCoeff && (coefficients[i] == 0 || !Double.isFinite(coefficients[i]))) {
                zeroCoeffCount++;
                continue;
            }else {
                foundNonZeroCoeff = true;
            }

            if(!Double.isFinite(coefficients[i])){
                coefficients[i] = 0;
            }
        }

        this.coefficients = Arrays.copyOfRange(coefficients, 0, coefficients.length-zeroCoeffCount);

        this.degree = this.coefficients.length-1;
    }


    /**
     * @return the degree of the polynomial
     */
    public int degree() {
        return degree;
    }


    /**
     * Evaluates the polynomial at a point x.
     * @param x Evaluation value
     * @return Evaluated value of the polynomial at a point x
     */
    public double eval(double x) {
        double y = 0;
        for(int i = 0; i < coefficients.length; i++) {
            y += (coefficients[i] * Math.pow(x, i));
        }
        return y;
    }


    /**
     * Returns the slope at a given point x.
     * @param x the point where to return the slope
     * @return the slope at the point x
     */
    public double slope(double x) {
        return this.derive().eval(x);
    }


    /**
     * Calculates the derivative of the polynomial.
     * @return the derivative of the polynomial
     */
    public Polynomial derive() {
        if(derivative != null) {
            return derivative;
        }

        if(coefficients.length == 1) {
            derivative = new Polynomial(0);
            return derivative;
        }

        double[] deriveCoeff = new double[coefficients.length-1];

        for (int i = 1; i < coefficients.length; i++) {
            deriveCoeff[i-1] = i * coefficients[i];
        }

        derivative = new Polynomial(deriveCoeff);

        return derivative;
    }



    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Polynomial){
            return Arrays.equals(((Polynomial) obj).coefficients, this.coefficients);
        }
        return false;
    }


    @Override
    protected Object clone() {
        return new Polynomial(this.coefficients);
    }


    @Override
    public String toString() {
        StringBuilder asStr = new StringBuilder();

        if(coefficients[0] != 0) {
            asStr.append(coefficients[0]);
        }

        if(coefficients.length > 1) {
            if(coefficients[1] == 0) {
                // do nothing
            } else if(coefficients[1] == 1 || coefficients[1] == -1) {
                asStr.append(coefficients[1] < 0 ? "-" : "+").append("x");
            }else {
                asStr.append(coefficients[1] < 0 ? "" : "+").append(coefficients[1]).append("x");
            }
        }

        for (int i = 2; i < coefficients.length; i++) {
            if(coefficients[i] != 0) {
                if(coefficients[i] == 0) {
                    // do nothing
                } else if(coefficients[i] == 1 || coefficients[i] == -1) {
                    asStr.append(coefficients[i] < 0 ? "-" : "+").append("x^").append(i);
                }else {
                    asStr.append(coefficients[i] < 0 ? "" : "+").append(coefficients[i]).append("x^").append(i);
                }
            }
        }
        return asStr.toString();
    }


    /**
     * Generates a random polynomial with a given degree.
     * @param degree the desired degree of the polynomial
     * @return the generated polynomial
     */
    public static Polynomial GENERATE_RANDOM(int degree) {
        double[] coeff = new double[degree];

        coeff[0] = Renderer.POLYNOMIAL_RANDOM.nextDouble();

        for (int i = 1; i < degree; i++) {
            coeff[i] = Renderer.POLYNOMIAL_RANDOM.nextDouble() * 4.5;
        }

        return new Polynomial(coeff);
    }


    /**
     * Converts a {@code Polynomial} to a {@code ComplexPolynomial}.
     * @param polynomial the {@code Polynomial} to convert
     * @return the converted {@code Polynomial}
     */
    public static ComplexPolynomial convertToComplexPolynomial(Polynomial polynomial) {
        return new ComplexPolynomial(polynomial.coefficients);
    }
}
