package com.calendar.model;

public class CalPojo implements java.io.Serializable{
	private String title;
	private String start;
	private String end;
	private String[] classNames;
	private boolean editable;
	private boolean allDay;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String[] getClassNames() {
		return classNames;
	}
	public void setClassNames(String[] classNames) {
		this.classNames = classNames;
	}	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	@Override
	public String toString() {
		return "CalPojo [title=" + title + ", start=" + start + ", end=" + end + ", classNames=" + classNames
				+ ", editable=" + editable + "]";
	}
}
