package exceptions;

public class NegativeAmountException extends Exception {
    public NegativeAmountException() {
        super("Amount cannot be a negative number!");
    }
}
