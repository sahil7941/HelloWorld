package TestModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CourseModel;

public class CourseModelTest {

	public static CourseModel model = new CourseModel();

	/**
	 * Main method to call test methods.
	 *
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// testAdd();
		// testDelete();
		// testUpdate();
	//	testFindByPK();
		 testFindByName();
		// testSearch();
		// testList();

	}

	/**
	 * Tests add a Role
	 *
	 * @throws ParseException
	 */
	public static void testAdd() throws ParseException {

		try {
			CourseBean bean = new CourseBean();
			// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			// bean.setId(1L);
			bean.setName("Hibernate");
			bean.setDescription("Programming Language");
			bean.setDuration("20 Days");
			bean.setCreatedBy("Ajay bhai");
			bean.setModifiedBy("Navaj bhai");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			// RoleBean addedbean = model.findByPK(pk);
			// if (addedbean == null) {
			// System.out.println("Test add fail");
			// }
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		try {
			CourseBean bean = new CourseBean();
			// long pk = 1L;
			bean.setId(3);
			// model.delete(bean);
			model.delete(bean);
			// RoleBean deletedbean = model.findByPK(pk);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 2L;
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

	public static void testUpdate() {

		try {
			CourseBean bean = new CourseBean();
			bean.setId(4L);
			bean.setName("Spring");
			bean.setDescription("Frameworks");
			bean.setDuration("5 Month");
			model.update(bean);

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	public static void testFindByName() {
		try {
			CourseBean bean = new CourseBean();
			bean = model.findByName("Spring");
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

}
