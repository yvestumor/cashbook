package vo;

public class Stats {
	private String day;
	private int cnt;
	public Stats() {
		super();
	}
	public Stats(String day, int cnt) {
		super();
		this.day = day;
		this.cnt = cnt;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	@Override
	public String toString() {
		return "Stats [day=" + day + ", cnt=" + cnt + "]";
	}
}
