package TestModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.sunrays.proj4.bean.FacultyBean;
import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.FacultyModel;
import in.co.sunrays.proj4.model.StudentModel;

public class facultyModelTest {
	public static FacultyModel model = new FacultyModel();

	/**
	 * Main method to call test methods.
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
	//	testAdd();
		// testDelete();
		 testUpdate();
		// testFindByPK();
		// testFindByName();
		// testSearch();
		// testList();

	}

	/**
	 * Tests add a Role
	 * 
	 * @throws Exception
	 */
	public static void testAdd() throws Exception {

		try {
			FacultyBean bean = new FacultyBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String str = "31/05/1997";
			// bean.setId(1L);
			bean.setFirstName("bittu");
			bean.setLastName("khan");
			bean.setGender("Male");
			bean.setDob(sdf.parse(str));
			bean.setQualification("BE");
			bean.setEmailId("bittukhan@nenosystems.com");
			bean.setMobNo("7966265415");
			bean.setCollegeId(2L);
			bean.setCollegeName("Medicaps");
			bean.setCourseId(1L);
			bean.setCourseName("Java");
			bean.setSubjectId(2L);
			bean.setSubjectName("Threading");
			
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.add(bean);
			// long pk = model.add(bean);
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
	public static void testUpdate() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String str = "31/05/1997";

		try {
			FacultyBean bean = new FacultyBean();
			bean.setId(2L);
			bean.setCollegeId(2L);
			bean.setCollegeName("IPS Academy");
			bean.setCourseId(1L);
			bean.setCourseName("Java");
			bean.setSubjectId(1L);
			bean.setSubjectName("Threading");
			bean.setQualification("BE");
			
			
			bean.setFirstName("Ajay");
			bean.setLastName("khan");
			bean.setDob(sdf.parse(str));
			bean.setMobNo("7966265415");
			bean.setEmailId("ajaybhai@nenosystems.com");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
}
