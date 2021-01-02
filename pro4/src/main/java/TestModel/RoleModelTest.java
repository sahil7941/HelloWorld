package TestModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.RoleModel;

/**
 * Role Model Test classes
 *
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */
public class RoleModelTest {

	/**
	 * Model object to test
	 */

	public static RoleModel model = new RoleModel();

	/**
	 * Main method to call test methods.
	 *
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		 testAdd();
		//testDelete();
		//testUpdate();
		// testFindByPK();
		// testFindByName();
		 testSearch();
		// testList();

	}

	/**
	 * Tests add a Role
	 *
	 * @throws ParseException
	 */
	public static void testAdd() throws ParseException {

		try {
			RoleBean bean = new RoleBean();
			// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			// bean.setId(1L);
			bean.setName("shivam");
			bean.setDescription("kumar");
			bean.setCreatedBy("Ajay bhai");
			bean.setModifiedBy("Navaj bhai");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
//			RoleBean addedbean = model.findByPK(pk);
//			if (addedbean == null) {
//				System.out.println("Test add fail");
//			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests delete a Role
	 */
	public static void testDelete() {

		try {
			RoleBean bean = new RoleBean();
			// long pk = 1L;
			bean.setId(8);
			// model.delete(bean);
			model.delete(bean);
			// RoleBean deletedbean = model.findByPK(pk);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests update a Role
	 */
	public static void testUpdate() {

		try {
			RoleBean bean = new RoleBean();
			bean.setId(8L);
			bean.setName("Bittu Bhai shab");
			bean.setDescription("Engg");
			model.update(bean);

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests find a User by PK.
	 */
	public static void testFindByPK() {
		try {
			RoleBean bean = new RoleBean();
			long pk = 6L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests find a User by Name.
	 */
	public static void testFindByName() {
		try {
			RoleBean bean = new RoleBean();
			bean = model.findByName("Bittu bhai shab");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests get Search
	 */
	  public static void testSearch() {

	        try {
	            RoleBean bean = new RoleBean();
	            List list = new ArrayList();
	            bean.setName("stude");
	            list = model.search(bean, 0, 0);
	            if (list.size() < 0) {
	                System.out.println("Test Serach fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (RoleBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getName());
	                System.out.println(bean.getDescription());
	            }

	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }

	    }

	    /**
	     * Tests get List.
	     */
	    public static void testList() {

	        try {
	            RoleBean bean = new RoleBean();
	            List list = new ArrayList();
	            list = model.list(1, 10);
	            if (list.size() < 0) {
	                System.out.println("Test list fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (RoleBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getName());
	                System.out.println(bean.getDescription());
	            }

	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	    }
	}

