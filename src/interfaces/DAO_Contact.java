package interfaces;

/**
 *
 * @author Javier
 */
public interface DAO_Contact {

    /**
     *
     * @throws Exception
     */
    public void show() throws Exception;

    public void add() throws Exception;

    public void update() throws Exception;

    public void search() throws Exception;

    public void delete() throws Exception;

    public void sort() throws Exception;
}
