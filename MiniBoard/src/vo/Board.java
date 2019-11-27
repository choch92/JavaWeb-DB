package vo;

import java.util.Date;

public class Board {
	private int num;
	private String title;
	private String content;
	private String name;
	private int cnt;
	private Date writedate;
	// 작성일을 출력하기 위한 속성
	private String dispdate;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public Date getWritedate() {
		return writedate;
	}
	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
	public String getDispdate() {
		return dispdate;
	}
	public void setDispdate(String dispdate) {
		this.dispdate = dispdate;
	}
	@Override
	public String toString() {
		return "Board [num=" + num + ", title=" + title + ", content=" + content + ", name=" + name + ", cnt=" + cnt
				+ ", writedate=" + writedate + ", dispdate=" + dispdate + "]";
	}
	
}
