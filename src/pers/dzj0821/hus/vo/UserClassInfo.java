package pers.dzj0821.hus.vo;

public class UserClassInfo {
	private int homeworkId;
	private String homeworkName;
	private Integer uploadId;
	private String publisherName;
	private String deadline;
	private HomeworkStatus homeworkStatus;
	private boolean publisher;
	private int uploadedNum;
	private int totalNum;

	public UserClassInfo() {
	}

	public UserClassInfo(int homeworkId, String homeworkName, Integer uploadId, String publisherName, String deadline,
			HomeworkStatus homeworkStatus, boolean publisher) {
		this.homeworkId = homeworkId;
		this.homeworkName = homeworkName;
		this.uploadId = uploadId;
		this.publisherName = publisherName;
		this.publisher = publisher;
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

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public HomeworkStatus getHomeworkStatus() {
		return homeworkStatus;
	}

	public void setHomeworkStatus(HomeworkStatus homeworkStatus) {
		this.homeworkStatus = homeworkStatus;
	}

	public boolean isPublisher() {
		return publisher;
	}

	public void setPublisher(boolean publisher) {
		this.publisher = publisher;
	}

	public int getUploadedNum() {
		return uploadedNum;
	}

	public void setUploadedNum(int uploadedNum) {
		this.uploadedNum = uploadedNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

}
