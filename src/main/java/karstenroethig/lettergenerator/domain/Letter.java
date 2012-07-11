package karstenroethig.lettergenerator.domain;

public class Letter {

	private String senderName;
	private String senderStreet;
	private String senderPostcode;
	private String senderCity;
	
	private String addressee;
	
	private String formattedDate;
	
	private String summary;
	private String text;
	
	public Letter() {
	}
	
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderStreet() {
		return senderStreet;
	}

	public void setSenderStreet(String senderStreet) {
		this.senderStreet = senderStreet;
	}

	public String getSenderPostcode() {
		return senderPostcode;
	}

	public void setSenderPostcode(String senderPostcode) {
		this.senderPostcode = senderPostcode;
	}

	public String getSenderCity() {
		return senderCity;
	}

	public void setSenderCity(String senderCity) {
		this.senderCity = senderCity;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getFormattedDate() {
		return formattedDate;
	}
	
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}
}
