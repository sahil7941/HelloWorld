package TestModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.bean.TimeTableBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.model.TimeTableModel;

public class TimeTableTest {
	public static TimeTableModel model = new TimeTableModel();

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
			TimeTableBean bean = new TimeTableBean();
			// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			// bean.setId(1L);
			bean.setSubjectId(1);
			bean.setSubjectName("Core Java");
			bean.setCourseId(1L);
			bean.setCourseName("java");
			bean.setSemester("5");
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
			TimeTableBean bean = new TimeTableBean();
			// long pk = 1L;
			bean.setId(1);
			// model.delete(bean);
			model.delete(bean);
			// RoleBean deletedbean = model.findByPK(pk);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
