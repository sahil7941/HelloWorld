package TestModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.model.SubjectModel;

public class testSubjectMdel {
	public static SubjectModel model = new SubjectModel();

	public static void main(String[] args) throws ParseException {
		 testAdd();
		//testDelete();
		//testUpdate();
		// testFindByPK();
		// testFindByName();
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
			SubjectBean bean = new SubjectBean();
			// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			// bean.setId(1L);
			bean.setName("OOP");
			bean.setDescription("Concept");
			bean.setCourseId(1L);
			bean.setCourseName("JAVA");
			
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
			SubjectBean bean = new SubjectBean();
			// long pk = 1L;
			bean.setId(1);
			// model.delete(bean);
			model.delete(bean);
			// RoleBean deletedbean = model.findByPK(pk);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		try {
			SubjectBean bean = new SubjectBean();
			bean.setId(1);
			bean.setName("Bhai");
			bean.setDescription("kumar");
			bean.setCourseId(1L);
			bean.setCourseName("JAVA");
		//	bean.setDescription("Engg");
			model.update(bean);

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
}
