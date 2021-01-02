package in.co.sunrays.proj3.dto;

import java.util.Date;
/**
 * TimeTableBean JavaBean encapsulates TimeTableBean attributes
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class TimeTableDTO extends BaseDTO{
	
	/*protected long id;
	protected String createdBy;
	protected String modifiedBy;
	protected Timestamp createdDateTime;
	protected Timestamp modifiedDateTime;*/
	
	private long courseId;
	private String courseName;
	private long subjectId;
	private String subjectName;
	private String semester;
	private Date examDate;
	private String time;
	
	
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getKey() {
		return id+"";
	}
	public String getValue() {
		return null;
	}

}
