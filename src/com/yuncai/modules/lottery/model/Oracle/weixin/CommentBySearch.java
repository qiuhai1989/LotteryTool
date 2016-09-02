package com.yuncai.modules.lottery.model.Oracle.weixin;

/**
 * 专家点评赛事查询实体类
 * @author blackworm
 *
 */
public class CommentBySearch {
	
	private Integer mid;
	private String content;
	
	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CommentBySearch() {
		super();
	}

	public CommentBySearch(Integer mid, String content) {
		super();
		this.mid = mid;
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentBySearch [mid=" + mid + ", content=" + content + "]";
	}
	
}
