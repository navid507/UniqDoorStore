package com.sai.udstore.DataBase.Models;

public class Certificate {
	private long id;
	private long styler;
	private long barber;
	private int priority;
	String certificate;
	private boolean State;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBarber() {
		return barber;
	}

	public void setBarber(long barber) {
		this.barber = barber;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getStyler() {
		return styler;
	}

	public void setStyler(long styler) {
		this.styler = styler;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public boolean isState() {
		return State;
	}

	public void setState(boolean state) {
		State = state;
	}

}
