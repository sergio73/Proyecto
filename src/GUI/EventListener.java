package GUI;

/**
 * Interface for listen events in windows
 * @author Sergio Delgado Baringo
 */
public interface EventListener {
    public enum Type{
        CLOSE
    }
    void event(Type type);
}
