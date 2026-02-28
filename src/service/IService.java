package service;

import java.util.List;

/**
 * Interface for services
 * 
 * @param
 */
public interface IService<T> {
    /**
     * Add a new item
     * 
     * @param
     */
    void addNew();

    /**
     * Update an item
     * 
     * @param
     */
    void update();
    T searchById(String id);
    void saveToFile(String fileName);
    void readFromFile(String fileName);
}
