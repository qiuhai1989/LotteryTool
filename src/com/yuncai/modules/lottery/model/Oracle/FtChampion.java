package com.yuncai.modules.lottery.model.Oracle;

public class FtChampion implements java.io.Serializable {

	private Integer id;
	private String team_num;
	private Integer team_id;
	private String team_name;
	private String sale_status;
	private Double bouns;
	private String support_rate;
	private String champion_rate;
	private String competition_type;
	private String rank;
	private String iconsrc;
	private Integer detail_id;
	
	public static String COMPETITION_TYPE_OUGUAN="0"; //欧冠
	public static String COMPETITION_TYPE_WORLDCUP="1";//世界杯
	public static String SALE_STATUS_YES="1";   //开售
	public static String SALE_STATUS_NO="0";	//停售
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeam_num() {
		return team_num;
	}

	public void setTeam_num(String team_num) {
		this.team_num = team_num;
	}

	public Integer getTeam_id() {
		return team_id;
	}

	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getSale_status() {
		return sale_status;
	}

	public void setSale_status(String sale_status) {
		this.sale_status = sale_status;
	}

	public Double getBouns() {
		return bouns;
	}

	public void setBouns(Double bouns) {
		this.bouns = bouns;
	}

	public String getSupport_rate() {
		return support_rate;
	}

	public void setSupport_rate(String support_rate) {
		this.support_rate = support_rate;
	}

	public String getChampion_rate() {
		return champion_rate;
	}

	public void setChampion_rate(String champion_rate) {
		this.champion_rate = champion_rate;
	}

	public String getCompetition_type() {
		return competition_type;
	}

	public void setCompetition_type(String competition_type) {
		this.competition_type = competition_type;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getIconsrc() {
		return iconsrc;
	}

	public void setIconsrc(String iconsrc) {
		this.iconsrc = iconsrc;
	}

	public Integer getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Integer detail_id) {
		this.detail_id = detail_id;
	}

	public FtChampion(){
		
	}

	public FtChampion(String team_num, Integer team_id,
			String team_name, String sale_status, Double bouns,
			String support_rate, String champion_rate, String competition_type,
			String rank, String iconsrc) {  
		this.team_num = team_num;
		this.team_id = team_id;
		this.team_name = team_name;
		this.sale_status = sale_status;
		this.bouns = bouns;
		this.support_rate = support_rate;
		this.champion_rate = champion_rate;
		this.competition_type = competition_type;
		this.rank = rank;
		this.iconsrc = iconsrc;
	}

	@Override
	public String toString() {
		return "FtChampion [id=" + id + ", team_num=" + team_num + ", team_id="
				+ team_id + ", team_name=" + team_name + ", sale_status="
				+ sale_status + ", bouns=" + bouns + ", support_rate="
				+ support_rate + ", champion_rate=" + champion_rate
				+ ", competition_type=" + competition_type + ", rank=" + rank
				+ ", iconsrc=" + iconsrc + ",detail_id="+detail_id+"]";
	}
	 
	
}
