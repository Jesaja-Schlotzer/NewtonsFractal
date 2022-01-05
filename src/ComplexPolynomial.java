import java.util.Arrays;

public class ComplexPolynomial {

    private final double[] coefficients;
    private final int degree;

    private ComplexPolynomial derivative;


    /**
     * Takes in the coefficients of the polynomial.
     * The first parameter takes place at x^0 the second at x^1 and so forth.
     * Invalid values (NaN and Infinity) are mapped to 0.
     * @param coefficients of the polynomial
     */
    public ComplexPolynomial(double... coefficients) {
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
     * Evaluates the polynomial at a point z.
     * @param z Evaluation value
     * @return Evaluated value of the polynomial at a point z
     */
    public ComplexNumber eval(ComplexNumber z) {
        ComplexNumber y = new ComplexNumber();
        for(int i = 0; i < coefficients.length; i++) {
            y.add(ComplexNumber.multiply(ComplexNumber.pow(z, i), coefficients[i]));
        }
        return y;
    }


    /**
     * Returns the slope at a given point z.
     * @param z the point where to return the slope
     * @return the slope at the point z
     */
    public ComplexNumber slope(ComplexNumber z) {
        return this.derive().eval(z);
    }


    /**
     * Calculates the derivative of the polynomial.
     * @return the derivative of the polynomial
     */
    public ComplexPolynomial derive() {
        if(derivative != null) {
            return derivative;
        }

        if(coefficients.length == 1) {
            derivative = new ComplexPolynomial(0);
            return derivative;
        }

        double[] deriveCoeff = new double[coefficients.length-1];

        for (int i = 1; i < coefficients.length; i++) {
            deriveCoeff[i-1] = i * coefficients[i];
        }

        derivative = new ComplexPolynomial(deriveCoeff);

        return derivative;
    }



    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ComplexPolynomial){
            return Arrays.equals(((ComplexPolynomial) obj).coefficients, this.coefficients);
        }
        return false;
    }


    @Override
    protected Object clone() {
        return new ComplexPolynomial(this.coefficients);
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
            if(coefficients[i] == 0) {
                // do nothing
            } else if(coefficients[i] == 1 || coefficients[i] == -1) {
                asStr.append(coefficients[i] < 0 ? "-" : "+").append("x^").append(i);
            }else {
                asStr.append(coefficients[i] < 0 ? "" : "+").append(coefficients[i]).append("x^").append(i);
            }
        }
        return asStr.toString();
    }


    /**
     * Generates a random polynomial with a given degree.
     * @param degree the desired degree of the polynomial
     * @return the generated polynomial
     */
    public static ComplexPolynomial GENERATE_RANDOM(int degree) {
        double[] coeff = new double[degree+1];

        coeff[0] = Renderer.POLYNOMIAL_RANDOM.nextDouble();

        for (int i = 1; i <= degree; i++) {
            coeff[i] = Renderer.POLYNOMIAL_RANDOM.nextDouble() * 4.5;
        }

        return new ComplexPolynomial(coeff);
    }


    /**
     * Converts a {@code ComplexPolynomial} to a {@code Polynomial}.
     * @param polynomial the {@code ComplexPolynomial} to convert
     * @return the converted {@code ComplexPolynomial}
     */
    public static Polynomial convertToPolynomial(ComplexPolynomial polynomial) {
        return new Polynomial(polynomial.coefficients);
    }
}
