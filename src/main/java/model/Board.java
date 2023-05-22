package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the boards database table.
 * 
 */
@Entity
@Table(name="boards")
@NamedQuery(name="Board.findAll", query="SELECT b FROM Board b")
public class Board implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int bno;

	@Lob
	private String bcontent;

	@Temporal(TemporalType.TIMESTAMP)
	private Date bdate;

	@Lob
	private byte[] bfiledata;

	private String bfilename;

	private String btitle;

	private String bwriter;

	private int views;

	public Board() {
	}

	public int getBno() {
		return this.bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getBcontent() {
		return this.bcontent;
	}

	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}

	public Date getBdate() {
		return this.bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public byte[] getBfiledata() {
		return this.bfiledata;
	}

	public void setBfiledata(byte[] bfiledata) {
		this.bfiledata = bfiledata;
	}

	public String getBfilename() {
		return this.bfilename;
	}

	public void setBfilename(String bfilename) {
		this.bfilename = bfilename;
	}

	public String getBtitle() {
		return this.btitle;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public String getBwriter() {
		return this.bwriter;
	}

	public void setBwriter(String bwriter) {
		this.bwriter = bwriter;
	}

	public int getViews() {
		return this.views;
	}

	public void setViews(int views) {
		this.views = views;
	}

}