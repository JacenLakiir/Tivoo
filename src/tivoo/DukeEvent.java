package tivoo;

import java.util.Calendar;

public class DukeEvent implements Event{
private String title;
private String description;
private Calendar startTime;
private Calendar endTime;
private String location;
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Calendar getStartTime() {
		return startTime;
	}

	@Override
	public Calendar getEndTime() {
		return endTime;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	@Override
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}

}
