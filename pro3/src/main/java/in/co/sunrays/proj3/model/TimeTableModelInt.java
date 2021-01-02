package in.co.sunrays.proj3.model;

import java.util.Date;
import java.util.List;

import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;

/**
 * Data Access Object of TimeTable
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public interface TimeTableModelInt {
	
	/**
     * Add a TimeTable
     * 
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : throws when Student already exists
     */
    public long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException;

    /**
     * Update a TimeTable
     * 
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     *             : if updated user record is already exist
     */
    public void update(TimeTableDTO dto) throws ApplicationException,DuplicateRecordException;

    /**
     * Delete a TimeTable
     * 
     * @param dto
     * @throws ApplicationException
     */
    public void delete(TimeTableDTO dto) throws ApplicationException;
    
    /**
	 * Find TimeTableDuplicacy
	 * 
	 * @throws ApplicationException
	 */
    public TimeTableDTO findTimeTableDuplicacy(long courseId, String semester, Date examDate) throws ApplicationException ;
    
    /**
	 * Find TimeTableDuplicacy
	 * 
	 * @throws ApplicationException
	 */
    public TimeTableDTO findTimeTableDuplicacy(long courseId, String semester, long subjectId) throws ApplicationException ;
    
    /**
     * Find TimeTable by PK
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
    public TimeTableDTO findByPK(long pk) throws ApplicationException;
    
    /**
     * Search TimeTable with pagination
     * 
     * @return list : List of TimeTable
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List search(TimeTableDTO dto, int pageNo, int pageSize)throws ApplicationException;
    
    /**
     * Search TimeTable
     * 
     * @return list : List of TimeTable
     * @param dto
     *            : Search Parameters
     * @throws ApplicationException
     */
    public List search(TimeTableDTO dto) throws ApplicationException;
    
    
    /**
     * Gets List of TimeTable
     * 
     * @return list : List of TimeTable
     * @throws DatabaseException
     */
    public List list() throws ApplicationException;
    
    /**
     * get List of TimeTable with pagination
     * 
     * @return list : List of TimeTable
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws ApplicationException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException;


}
