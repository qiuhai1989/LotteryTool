package com.yuncai.modules.lottery.model.Oracle;

/**
 *  竞彩足球-近期战绩实体类(官网)
 * @author blackworm
 *
 */
public class FtNearRecord {
	
	private int id;
	private String mbWin; //主胜
	private String mbDraw;//主平
	private String mbLose;//主负
	private String mbInball;//主-进球
	private String mbMiss;//主-失球
	private String mbJing;//主-净胜球
	
	private String tgWin; //客胜
	private String tgDraw;//客平
	private String tgLose;//客负
	private String tgInball;//客-进球
	private String tgMiss;//客-失球
	private String tgJing;//客-净胜球
	
	private int mid; //对应官网mid

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMbWin() {
		return mbWin;
	}

	public void setMbWin(String mbWin) {
		this.mbWin = mbWin;
	}

	public String getMbDraw() {
		return mbDraw;
	}

	public void setMbDraw(String mbDraw) {
		this.mbDraw = mbDraw;
	}

	public String getMbLose() {
		return mbLose;
	}

	public void setMbLose(String mbLose) {
		this.mbLose = mbLose;
	}

	public String getMbInball() {
		return mbInball;
	}

	public void setMbInball(String mbInball) {
		this.mbInball = mbInball;
	}

	public String getMbMiss() {
		return mbMiss;
	}

	public void setMbMiss(String mbMiss) {
		this.mbMiss = mbMiss;
	}

	public String getMbJing() {
		return mbJing;
	}

	public void setMbJing(String mbJing) {
		this.mbJing = mbJing;
	}

	public String getTgWin() {
		return tgWin;
	}

	public void setTgWin(String tgWin) {
		this.tgWin = tgWin;
	}

	public String getTgDraw() {
		return tgDraw;
	}

	public void setTgDraw(String tgDraw) {
		this.tgDraw = tgDraw;
	}

	public String getTgLose() {
		return tgLose;
	}

	public void setTgLose(String tgLose) {
		this.tgLose = tgLose;
	}

	public String getTgInball() {
		return tgInball;
	}

	public void setTgInball(String tgInball) {
		this.tgInball = tgInball;
	}

	public String getTgMiss() {
		return tgMiss;
	}

	public void setTgMiss(String tgMiss) {
		this.tgMiss = tgMiss;
	}

	public String getTgJing() {
		return tgJing;
	}

	public void setTgJing(String tgJing) {
		this.tgJing = tgJing;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public FtNearRecord(String mbWin, String mbDraw, String mbLose,
			String mbInball, String mbMiss, String mbJing, String tgWin,
			String tgDraw, String tgLose, String tgInball, String tgMiss,
			String tgJing, int mid) {
		super();
		this.mbWin = mbWin;
		this.mbDraw = mbDraw;
		this.mbLose = mbLose;
		this.mbInball = mbInball;
		this.mbMiss = mbMiss;
		this.mbJing = mbJing;
		this.tgWin = tgWin;
		this.tgDraw = tgDraw;
		this.tgLose = tgLose;
		this.tgInball = tgInball;
		this.tgMiss = tgMiss;
		this.tgJing = tgJing;
		this.mid = mid;
	}

	public FtNearRecord() {
		super();
	}

	@Override
	public String toString() {
		return "FtNearRecord [id=" + id + ", mbWin=" + mbWin + ", mbDraw="
				+ mbDraw + ", mbLose=" + mbLose + ", mbInball=" + mbInball
				+ ", mbMiss=" + mbMiss + ", mbJing=" + mbJing + ", tgWin="
				+ tgWin + ", tgDraw=" + tgDraw + ", tgLose=" + tgLose
				+ ", tgInball=" + tgInball + ", tgMiss=" + tgMiss + ", tgJing="
				+ tgJing + ", mid=" + mid + "]";
	}
	
}
