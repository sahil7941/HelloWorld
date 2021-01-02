package in.co.sunrays.proj3.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import in.co.sunrays.proj3.dto.DropdownList;

/**
 * HTML Utility class to produce HTML contents like Dropdown List.
 * 
 * @author Adapterpattern
 * @version 1.0
 * @copyright (c) Adapterpattern
 * 
 */
public class HTMLUtility {

	/**
	 * Create HTML SELECT list from MAP paramters values
	 * 
	 * @param name
	 * @param selectedVal
	 * @param map
	 * @return
	 */

	public static String getList(String name, String selectedVal, HashMap<String, String> map,String error) {
		String hi=(error.equalsIgnoreCase(""))?"form-control":"form-control border border-danger";
		StringBuffer sb = new StringBuffer("<select class='"+hi+"' id='sel1' name='" + name + "'>");

		Set<String> keys = map.keySet();
		String val = null;
		String select="Select";
		sb.append("<option selected value=' '>" +select+ "</option>");
		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}
	 /**
     * Create HTML SELECT List from List parameter
     *
     * @param name
     * @param selectedVal
     * @param list
     * @return
     */
    //save key in database
	
    public static String getList(String name, String selectedVal, List list,String error) {
      
        Collections.sort(list);
        //System.out.println("compare"); nkn
        List<DropdownList> dd = (List<DropdownList>) list;
   
        String hi=(error.equalsIgnoreCase(""))?"form-control":"form-control border border-danger";
		StringBuffer sb = new StringBuffer("<select class='"+hi+"' id='sel1' name='" + name + "'>");

        String key = null;
        String val = null;

        String select="Select";
        
        if(name.equalsIgnoreCase("collegeIdN")){
        	select="Select College";
        }
        if(name.equalsIgnoreCase("courseIdN")){
        	select="Select Course";
        }
        if(name.equalsIgnoreCase("mIdRoll")){
        	select="Select RollNo";
        }   
        
        
        
        sb.append("<option selected value=' '>" +select+ "</option>");
        
        
        for (DropdownList obj : dd) {
            key = obj.getKey();
            val = obj.getValue();
            
            if (key.trim().equals(selectedVal)) {
            	//System.out.println(key+":y:"+val);
            	sb.append("<option selected value='" + key + "'>" + val
                        + "</option>");
            } else {
            	//System.out.println(key+":n:"+val);
                sb.append("<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("</select>");
        return sb.toString();
    }
	
	
    
    
    
    
    public static String getList(String name, String selectedVal, String lname, List list) {
        
        Collections.sort(list);
        //System.out.println("compare"); 
        List<DropdownList> dd = (List<DropdownList>) list;
   
		StringBuffer sb = new StringBuffer("<select class='form-control' id='sel1' name='" + name + "'>");
		

        String key = null;
        String val = null;

        String select="Select "+lname;
        
        if(name.equalsIgnoreCase("collegeIdN")){
        	select="Select CollegeName";
        }
        if(name.equalsIgnoreCase("courseIdN")){
        	select="Select Course";
        }
        if(name.equalsIgnoreCase("mIdRoll")){
        	select="Select RollNo";
        }   
        
        
        
        sb.append("<option selected value=' '>" +select+ "</option>");
        
        
        for (DropdownList obj : dd) {
            key = obj.getKey();
            val = obj.getValue();
            
            if (key.trim().equals(selectedVal)) {
            	//System.out.println(key+":y:"+val);
            	sb.append("<option selected value='" + key + "'>" + val
                        + "</option>");
            } else {
            	//System.out.println(key+":n:"+val);
                sb.append("<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("</select>");
        return sb.toString();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Get Selected value from List parameter
	 * 
	 * @param name
	 * @param selectedVal
	 * @param list
	 * @return
	 */

	
	/*public static String getUserRole(String name, String selectedVal, List list) {

		Collections.sort(list);

		List<DropdownListDTO> dd = (List<DropdownListDTO>) list;

		String roleName = null;

		String key = null;
		String val = null;

		for (DropdownListDTO obj : dd) {
			key = obj.getKey();
			val = obj.getValue();

			if (key.trim().equals(selectedVal)) {
				roleName = val;
			}
		}
		return roleName;
	}
*/
	/**
	 * Create HTML SELECT List from List parameter
	 * 
	 * @param name
	 * @param selectedVal
	 * @param list
	 * @return
	 */
	/*public static String getList(String name, String selectedVal, List list) {

		Collections.sort(list);

		List<DropdownListDTO> dd = (List<DropdownListDTO>) list;

		StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'>");

		String key = null;
		String val = null;
		sb.append("<option selected value=''> --Select-- </option>");
		for (DropdownListDTO obj : dd) {
			key = obj.getKey();
			val = obj.getValue();

			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}*/

	/**
	 * Create HTML SELECT List from HashMap parameter
	 * 
	 * @param name
	 * @param selectedVal
	 * @param map
	 * @param select
	 * @return
	 */
	/*s*/

	/**
	 * Creates submit button if user has access permission.
	 * 
	 * @param label
	 * @param access
	 * @param request
	 * @return
	 */
	public static String getSubmitButton(String label, boolean access, HttpServletRequest request) {

		String button = "";

		if (access) {
			button = "<input type='submit' name='operation'    value='" + label + "' >";
		}
		return button;
	}

	/**
	 * Create HTML SELECT Course List from List parameter
	 * 
	 * @param name
	 * @param selectedVal
	 * @param map
	 * @param select
	 * @return
	 */
	/*public static String getCourseList(String name, String selectedVal, List list) {

		Collections.sort(list);

		List<DropdownListDTO> dd = (List<DropdownListDTO>) list;

		StringBuffer sb = new StringBuffer(
				"<select onchange='this.form.submit()' class='form-control' name='" + name + "'>");

		String key = null;
		String val = null;
		sb.append("<option selected value=''> --Select-- </option>");
		for (DropdownListDTO obj : dd) {
			key = obj.getKey();
			val = obj.getValue();

			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}
*/
}
