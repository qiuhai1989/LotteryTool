package com.yuncai.modules.lottery.model.Oracle.weixin;

/**
 * 赛事-专家点评关联表
 * @author blackworm
 *
 */
public class FtMatchComment {
	
	private Integer id;
	private int newsId;
	private int mid;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}
	
	public FtMatchComment(int newsId, int mid) {
		super();
		this.newsId = newsId;
		this.mid = mid;
	}

	public FtMatchComment() {
		super();
	}
	
}
