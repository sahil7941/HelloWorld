package TestModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.model.RoleModel;

public class testCollegeModel {
	public static CollegeModel model = new CollegeModel();
	public static void main(String[] args) throws ApplicationException, Exception {
		//testAdd();
		//testDelete();
		//testUpdate();
		// testFindByPK();
		// testFindByName();
		// testSearch();
		 testList();

	}

	public static void testAdd() throws ParseException {

		try {
			CollegeBean bean = new CollegeBean();
			// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			// bean.setId(1L);
			bean.setName("JIT Borawan");
			bean.setAddress("Indore");
			bean.setState("MP");
			bean.setCity("Indore");
			
			bean.setPhoneNo("855466456");
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
   public static void testDelete() {

       try {
           CollegeBean bean = new CollegeBean();
           long pk = 15L;
           bean.setId(pk);
           model.delete(bean);
           System.out.println("Test Delete succ");
           CollegeBean deletedBean = model.findByPK(pk);
           if (deletedBean != null) {
               System.out.println("Test Delete fail");
           }
       } catch (ApplicationException e) {
           e.printStackTrace();
       }
   }

   /**
    * Tests update a College
 * @throws Exception 
 * @throws ApplicationException 
    */
   public static void testUpdate() throws ApplicationException, Exception {

           CollegeBean bean =new CollegeBean();
           bean.setId(15);
           bean.setName("oit");
           bean.setAddress("vv");
           model.update(bean);

   }

   /**
    * Tests find a College by Name.
    */

   public static void testFindByName() {

       try {
           CollegeBean bean = model.findByName("IPS");
           if (bean == null) {
               System.out.println("Test Find By Name fail");
           }
           System.out.println(bean.getId());
           System.out.println(bean.getName());
           System.out.println(bean.getAddress());
           System.out.println(bean.getState());
           System.out.println(bean.getCity());
           System.out.println(bean.getPhoneNo());
           System.out.println(bean.getCreatedBy());
           System.out.println(bean.getCreatedDatetime());
           System.out.println(bean.getModifiedBy());
           System.out.println(bean.getModifiedDatetime());
       } catch (ApplicationException e) {
           e.printStackTrace();
       }

   }

   /**
    * Tests find a College by PK.
    */
   public static void testFindByPK() {
       try {
           CollegeBean bean = new CollegeBean();
           long pk = 15L;
           bean = model.findByPK(pk);
           if (bean == null) {
               System.out.println("Test Find By PK fail");
           }
           System.out.println(bean.getId());
           System.out.println(bean.getName());
           System.out.println(bean.getAddress());
           System.out.println(bean.getState());
           System.out.println(bean.getCity());
           System.out.println(bean.getPhoneNo());
           System.out.println(bean.getCreatedBy());
           System.out.println(bean.getCreatedDatetime());
           System.out.println(bean.getModifiedBy());
           System.out.println(bean.getModifiedDatetime());
       } catch (ApplicationException e) {
           e.printStackTrace();
       }

   }

   /**
    * Tests search a College by Name
    */

   public static void testSearch() {
       try {
           CollegeBean bean = new CollegeBean();
           List list = new ArrayList();
           bean.setName("LNC");
           // bean.setAddress("borawan");
           list = model.search(bean, 1, 10);
           if (list.size() < 0) {
               System.out.println("Test Search fail");
           }
           Iterator it = list.iterator();
           while (it.hasNext()) {
               bean = (CollegeBean) it.next();
               System.out.println(bean.getId());
               System.out.println(bean.getName());
               System.out.println(bean.getAddress());
               System.out.println(bean.getState());
               System.out.println(bean.getCity());
               System.out.println(bean.getPhoneNo());
               System.out.println(bean.getCreatedBy());
               System.out.println(bean.getCreatedDatetime());
               System.out.println(bean.getModifiedBy());
               System.out.println(bean.getModifiedDatetime());
           }
       } catch (ApplicationException e) {
           e.printStackTrace();
       }
   }

   /**
    * Tests get List a College.
    */
   public static void testList() {

       try {
           CollegeBean bean = new CollegeBean();
           List list = new ArrayList();
           list = model.list(1, 10);
           if (list.size() < 0) {
               System.out.println("Test list fail");
           }
           Iterator it = list.iterator();
           while (it.hasNext()) {
               bean = (CollegeBean) it.next();
               System.out.println(bean.getId());
               System.out.println(bean.getName());
               System.out.println(bean.getAddress());
               System.out.println(bean.getState());
               System.out.println(bean.getCity());
               System.out.println(bean.getPhoneNo());
               System.out.println(bean.getCreatedBy());
               System.out.println(bean.getCreatedDatetime());
               System.out.println(bean.getModifiedBy());
               System.out.println(bean.getModifiedDatetime());
           }

       } catch (ApplicationException e) {
           e.printStackTrace();
       }
   }

}

