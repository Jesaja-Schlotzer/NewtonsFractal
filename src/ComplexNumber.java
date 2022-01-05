
public class ComplexNumber {

    /**
     * A {@code ComplexNumber} with real and imaginary part set to 0.
     */
    public static final ComplexNumber ZERO = new ComplexNumber(0, 0);
    /**
     * A {@code ComplexNumber} with real and imaginary part set to Double.MIN_VALUE.
     */
    public static final ComplexNumber PRACTICALLY_ZERO = new ComplexNumber(Double.MIN_VALUE, Double.MIN_VALUE);


    private double realPart;
    private double imaginaryPart;


    /**
     * Constructs a {@code ComplexNumber} with real and imaginary part set to 0.
     */
    public ComplexNumber() {
        this(0, 0);
    }

    /**
     * Constructs a {@code ComplexNumber}.
     * @param realPart the real part of the {@code ComplexNumber}
     * @param imaginaryPart the imaginary part of the{@code ComplexNumber}
     */
    public ComplexNumber(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    /**
     * Copys the given {@code ComplexNumber}.
     * @param complexNumber the {@code ComplexNumber} to copy from
     */
    public ComplexNumber(ComplexNumber complexNumber) {
        this.realPart = complexNumber.realPart;
        this.imaginaryPart = complexNumber.imaginaryPart;
    }


    /**
     * Adds the summand to this {@code ComplexNumber}.
     * @param summand the {@code ComplexNumber} to add
     * @return this {@code ComplexNumber}, in case you want to calculate in a chain
     */
    public ComplexNumber add(ComplexNumber summand) {
        this.realPart += summand.realPart;
        this.imaginaryPart += summand.imaginaryPart;

        return this;
    }


    /**
     * Subtracts the subtrahend from this {@code ComplexNumber}.
     * @param subtrahend the {@code ComplexNumber} to subtract from
     * @return this {@code ComplexNumber}, in case you want to calculate in a chain
     */
    public ComplexNumber subtract(ComplexNumber subtrahend) {
        this.realPart -= subtrahend.realPart;
        this.imaginaryPart -= subtrahend.imaginaryPart;

        return this;
    }


    /**
     * Multiplies the factor to this {@code ComplexNumber}.
     * @param factor the {@code ComplexNumber} to multiply with
     * @return this {@code ComplexNumber}, in case you want to calculate in a chain
     */
    public ComplexNumber multiply(ComplexNumber factor) {
        double tempReal = realPart;
        tempReal = (this.realPart * factor.realPart) - (this.imaginaryPart * factor.imaginaryPart);
        this.imaginaryPart = (this.realPart * factor.imaginaryPart) + (this.imaginaryPart * factor.realPart);
        this.realPart = tempReal;

        return this;
    }

    /**
     * Multiplies the real factor to this {@code ComplexNumber}.
     * @param factor the double to multiply with
     * @return this {@code ComplexNumber}, in case you want to calculate in a chain
     */
    public ComplexNumber multiply(double factor) {
        this.realPart = this.realPart * factor;
        this.imaginaryPart = this.imaginaryPart * factor;

        return this;
    }


    /**
     * Divides this {@code ComplexNumber} by the divisor.
     * @param divisor the divisor
     * @return this {@code ComplexNumber}, in case you want to calculate in a chain
     */
    public ComplexNumber divide(ComplexNumber divisor) {
        ComplexNumber conjDiv = new ComplexNumber(divisor);
        conjDiv.conjugate();

        double real = ((this.realPart * conjDiv.realPart) - (this.imaginaryPart * conjDiv.imaginaryPart)) /
                      ((divisor.realPart * conjDiv.realPart) - (divisor.imaginaryPart * conjDiv.imaginaryPart));

        double imaginary = ((this.imaginaryPart * conjDiv.realPart) + (this.realPart * conjDiv.imaginaryPart)) /
                           ((divisor.realPart * conjDiv.realPart) - (divisor.imaginaryPart * conjDiv.imaginaryPart));

        this.realPart = real;
        this.imaginaryPart = imaginary;

        return this;
    }


    /**
     * Conjugates this {@code ComplexNumber}.
     * @return this {@code ComplexNumber}, in case you want to calculate in a chain
     */
    public ComplexNumber conjugate() {
        this.imaginaryPart = -this.imaginaryPart;

        return this;
    }


    /**
     * Calculates the distance between this {@code ComplexNumber} and a {@code ComplexNumber} z.
     * @param z the {@code ComplexNumber} to calculate the distance to
     * @return the distance to the {@code ComplexNumber} z
     */
    public double distanceTo(ComplexNumber z) {
        return Math.sqrt(Math.pow(this.realPart - z.realPart, 2.0) + Math.pow(this.imaginaryPart - z.imaginaryPart, 2.0));
    }



    /**
     * Adds two {@code ComplexNumber} together and returns a new {@code ComplexNumber} which holds the result.
     * @param summand1 the first summand
     * @param summand2 the second summand
     * @return the result as a new {@code ComplexNumber} object
     */
    public static ComplexNumber add(ComplexNumber summand1, ComplexNumber summand2) {
        ComplexNumber z = new ComplexNumber(summand1);
        z.add(summand2);
        return z;
    }


    /**
     * Subtracts one {@code ComplexNumber} from another and returns a new {@code ComplexNumber} which holds the result.
     * @param minuend the minuend
     * @param subtrahend the subtrahend
     * @return the result as a new {@code ComplexNumber} object
     */
    public static ComplexNumber subtract(ComplexNumber minuend, ComplexNumber subtrahend) {
        ComplexNumber z = new ComplexNumber(minuend);
        z.subtract(subtrahend);
        return z;
    }


    /**
     * Multiplies two {@code ComplexNumber} together and returns a new {@code ComplexNumber} which holds the result.
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the result as a new {@code ComplexNumber} object
     */
    public static ComplexNumber multiply(ComplexNumber factor1, ComplexNumber factor2) {
        ComplexNumber z = new ComplexNumber(factor1);
        z.multiply(factor2);
        return z;
    }


    /**
     * Multiplies a {@code ComplexNumber} with a {@code double} number and returns a new {@code ComplexNumber} which holds the result.
     * @param factor1 the {@code ComplexNumber}
     * @param factor2 the double number
     * @return the result as a new {@code ComplexNumber} object
     */
    public static ComplexNumber multiply(ComplexNumber factor1, double factor2) {
        ComplexNumber z = new ComplexNumber(factor1);
        z.multiply(factor2);
        return z;
    }


    /**
     * Divides one {@code ComplexNumber} from another and returns a new {@code ComplexNumber} which holds the result.
     * @param dividend the dividend
     * @param divisor the divisor
     * @return the result as a new {@code ComplexNumber} object
     */
    public static ComplexNumber divide(ComplexNumber dividend, ComplexNumber divisor) {
        ComplexNumber z = new ComplexNumber(dividend);
        z.divide(divisor);
        return z;
    }


    /**
     * Conjugates a {@code ComplexNumber} and returns a new {@code ComplexNumber} which holds the conjugated {@code ComplexNumber}.
     * @param complexNumber the {@code ComplexNumber} to conjugate
     * @return the result as a new {@code ComplexNumber} object
     */
    public static ComplexNumber conjugate(ComplexNumber complexNumber) {
        ComplexNumber z = new ComplexNumber(complexNumber);
        z.conjugate();
        return z;
    }


    /**
     * Calculates the power of a {@code ComplexNumber} base and an integer exponent and returns a new {@code ComplexNumber} which holds the result.
     * @param complexNumber the base
     * @param n the exponent
     * @return the result as a new {@code ComplexNumber} object
     */
    public static ComplexNumber pow(ComplexNumber complexNumber, int n) {
        if(n == 0) {
            return new ComplexNumber(1, 0);
        }
        ComplexNumber z = new ComplexNumber(complexNumber);
        for (int i = 0; i < n-1; i++) {
            z.multiply(complexNumber);
        }
        return z;
    }


    /**
     * @return the real part of the {@code ComplexNumber}
     */
    public double real() {
        return realPart;
    }

    /**
     * @return the imaginary part of the {@code ComplexNumber}
     */
    public double imaginary() {
        return imaginaryPart;
    }

    /**
     * Sets the real part of the {@code ComplexNumber}.
     * @param realPart the value to set
     */
    public void setReal(double realPart) {
        this.realPart = realPart;
    }

    /**
     * Sets the imaginary part of the {@code ComplexNumber}.
     * @param imaginaryPart the value to set
     */
    public void setImaginary(double imaginaryPart) {
        this.imaginaryPart = imaginaryPart;
    }

    /**
     * Sets the real and the imaginary part of the {@code ComplexNumber}.
     * @param complexNumber the {@code ComplexNumber} to copy from
     */
    public void set(ComplexNumber complexNumber) {
        this.realPart = complexNumber.realPart;
        this.imaginaryPart = complexNumber.imaginaryPart;
    }



    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ComplexNumber){
            return realPart == ((ComplexNumber) obj).realPart && imaginaryPart == ((ComplexNumber) obj).imaginaryPart;
        }
        return false;
    }


    @Override
    protected Object clone() {
        return new ComplexNumber(this.realPart, this.imaginaryPart);
    }


    @Override
    public String toString() {
        return (realPart != 0 ? realPart : (imaginaryPart == 0 ? realPart : "")) + (imaginaryPart != 0 ? (realPart != 0 ? (imaginaryPart < 0 ? " " : " +") : "") + imaginaryPart + "i" : "");
    }
}
