package com.yuncai.modules.lottery.model.Oracle.weixin;

import java.util.Date;

/**
 * 微信新闻资讯
 * @author blackworm
 *
 */
public class WeiXinNews {
	
	private Integer id;
	private String title;
	private String content;
	private Date editTime;
	private String imgSrc;
	private String author;
	private String source;
	private String keyword;
	private Integer isShow;
	private Integer ntype;
	private Integer expect;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getNtype() {
		return ntype;
	}
	public void setNtype(Integer ntype) {
		this.ntype = ntype;
	}
	
	public Integer getExpect() {
		return expect;
	}
	public void setExpect(Integer expect) {
		this.expect = expect;
	}
	public WeiXinNews(String title, String content, Date editTime, String imgSrc, String author, String source, String keyword, Integer isShow,
			Integer ntype) {
		super();
		this.title = title;
		this.content = content;
		this.editTime = editTime;
		this.imgSrc = imgSrc;
		this.author = author;
		this.source = source;
		this.keyword = keyword;
		this.isShow = isShow;
		this.ntype = ntype;
	}
	
	public WeiXinNews(String title, String content, Date editTime, Integer isShow,Integer ntype) {
		super();
		this.title = title;
		this.content = content;
		this.editTime = editTime;
		this.isShow = isShow;
		this.ntype = ntype;
	}
	public WeiXinNews() {
		super();
	}
	@Override
	public String toString() {
		return "WeiXinNews [id=" + id + ", title=" + title + ", content=" + content + ", editTime=" + editTime + ", imgSrc=" + imgSrc + ", author="
				+ author + ", source=" + source + ", keyword=" + keyword + ", isShow=" + isShow + ", ntype=" + ntype + "]";
	}
	
}
