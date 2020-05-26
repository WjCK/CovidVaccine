package database;

import java.io.Serializable;
import java.util.List;

import util.DatabaseException;

public interface GenericDAO<T, ID extends Serializable> {

  /**
   * return class to persist
   */
  public Class<T> getObjectClass();

  /**
   * get the transaction running if it exists
   * 
   * @return
   */
  public TransactionDB getTransaction();

  /**
   * Set transaction running
   * 
   * @param transactionDB
   */
  public void setTransactionDB(TransactionDB transactionDB);

  /**
   * include/create a object T in database
   * 
   * @param object
   * @return
   * @throws DatabaseException
   */
  public T create(T object) throws DatabaseException, DatabaseException;

  /**
   * retrieve object T in database
   * 
   * @param id
   * @return
   * @throws DatabaseException
   */
  public T retrieve(Integer id) throws DatabaseException;

  /**
   * update object T in database
   * 
   * @param object
   * @return
   * @throws DatabaseException
   */
  public T update(T object) throws DatabaseException;

  /**
   * delete object T in database
   * 
   * @param id
   * @return
   * @throws DatabaseException
   */
  public T delete(Integer id) throws DatabaseException;

  /**
   * list objects T in database
   * 
   * @return
   * @throws DatabaseException
   */
  public List<T> list() throws DatabaseException;
}