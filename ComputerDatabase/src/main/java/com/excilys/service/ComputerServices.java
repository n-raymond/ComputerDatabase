package com.excilys.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Computer;
import com.excilys.persistence.AbstractDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionException;
import com.excilys.persistence.DAOException;

/**
 * Groups the different services to use computers.
 */
public class ComputerServices {

    /**
     * The access to comapanie's DAO.
     */
    AbstractDAO<Computer> dao = ComputerDAO.getInstance();

    private Logger logger = LoggerFactory.getLogger(ComputerServices.class);

    /* Singleton */

    private static ComputerServices instance;

    /**
     * Constructs a ComputerServices.
     */
    private ComputerServices() {
    }

    /**
     * @return The unique instance of a ComputerServices.
     */
    public static ComputerServices getInstance() {
        if (instance == null) {
            synchronized (ComputerServices.class) {
                if (instance == null) {
                    instance = new ComputerServices();
                }
            }
        }
        return instance;
    }

    /* Functionalities */

    /**
     * Prints the size computers from the from-th company found by the DAO.
     * @param from The number of the first computer to print
     * @param size The number of computers to print
     * @throws ServiceException If a connection problem or a DAO problem was
     *         encounter
     */
    public void printComputers(int from, int size) throws ServiceException {
        List<Computer> computers;
        try {
            computers = dao.findSeveral(size, from);
            System.out.println("Computers :");

            for (Computer computer : computers) {
                System.out.println(computer);
            }
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            logger.warn("[Throw] <ServiceException>");
            throw new ServiceException(e);
        }
    }

    /**
     * Adds a new computer in the DAO.
     * @param c The new computer to add
     * @throws ServiceException If a connection problem or a DAO problem was
     *         encounter
     */
    public void addNewComputer(Computer c) throws ServiceException {
        try {
            dao.insert(c);
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            logger.warn("[Throw] <ServiceException>");
            throw new ServiceException(e);
        }
    }

    /**
     * Update a computer in the DAO.
     * @param id The id of the computer to update
     * @param freshValue The new value of the computer
     * @throws ServiceException If a connection problem or a DAO problem was
     *         encounter
     */
    public void updateComputer(long id, Computer freshValue) throws ServiceException {
        try {
            dao.update(id, freshValue);
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            logger.warn("[Throw] <ServiceException>");
            throw new ServiceException(e);
        }
    }

}