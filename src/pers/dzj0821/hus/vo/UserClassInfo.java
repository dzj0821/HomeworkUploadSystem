package pers.dzj0821.hus.vo;

public class UserClassInfo {
	private int homeworkId;
	private String homeworkName;
	private Integer uploadId;
	private String publisherName;
	
	public UserClassInfo() {}
	
	public UserClassInfo(int homeworkId, String homeworkName, Integer uploadId, String publisherName) {
		this.homeworkId = homeworkId;
		this.homeworkName = homeworkName;
		this.uploadId = uploadId;
		this.publisherName = publisherName;
	}
	
	public int getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(int homeworkId) {
		this.homeworkId = homeworkId;
	}
	public String getHomeworkName() {
		return homeworkName;
	}
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}
	
	public Integer getUploadId() {
		return uploadId;
	}

	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}

	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	
}
