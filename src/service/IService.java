package service;

/**
 * Interface for services
 * 
 *
 */
public interface IService<T> {
    /**
     * Add a new item
     * 
     *
     */
    void addNew();

    /**
     * Update an item
     * 
     *
     */
    void update();

    T searchById(String id);

    void saveToFile(String fileName);

    void readFromFile(String fileName);
}
