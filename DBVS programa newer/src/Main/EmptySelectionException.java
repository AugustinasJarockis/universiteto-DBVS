package Main;

public class EmptySelectionException extends Exception{
    String reasonMessage;
    public EmptySelectionException(String reasonMessage) {
        super();
        this.reasonMessage = reasonMessage;
    }
}
