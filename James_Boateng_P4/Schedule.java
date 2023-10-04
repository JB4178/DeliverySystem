
/**
 * Write a description of interface Schedule here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Schedule
{
    /**
     * Called each hour, allowing the object to perform an action.
     */
    public void action();
    
    /**
     * Will store the object’s current information into a log file.
     */
    public void log_status();
}
