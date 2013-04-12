package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Software {
	//Software-objektets egenskaper/attributter
	private int swVersion;
	private int subVersion;
	private String url;
	//Slutt egenskaper
	
	private PropertyChangeSupport propChangeSupp;
	
	public final static String VERSION_PROPERTY_NAME = "version";
	public final static String SUBVERSION_PROPERTY_NAME = "subVersion";
	public final static String URL_PROPERTY_NAME = "url";
	
	public Software() {
		this.swVersion = 0;
		this.subVersion = 0;
		this.url = "";
		propChangeSupp = new PropertyChangeSupport(this);
	}

	public Software(int swVersion, int subVersion, String url) {
		this.swVersion = swVersion;
		this.subVersion = subVersion;
		this.url = url;
		propChangeSupp = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.removePropertyChangeListener(listener);
	}

	public int getSwVersion() {
		return swVersion;
	}

	public void setSwVersion(int swVersion) {
		int oldswVersion = this.swVersion;
		this.swVersion = swVersion;
		PropertyChangeEvent event = new PropertyChangeEvent(this, VERSION_PROPERTY_NAME, oldswVersion, swVersion);
		propChangeSupp.firePropertyChange(event);
	}

	public int getSubVersion() {
		return subVersion;
	}

	public void setSubVersion(int subVersion) {
		int oldsubVersion = this.subVersion;
		this.subVersion = subVersion;
		PropertyChangeEvent event = new PropertyChangeEvent(this, SUBVERSION_PROPERTY_NAME, oldsubVersion, subVersion);
		propChangeSupp.firePropertyChange(event);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		String oldUrl = this.url;
		this.url = url;
		PropertyChangeEvent event = new PropertyChangeEvent(this, URL_PROPERTY_NAME, oldUrl, url);
		propChangeSupp.firePropertyChange(event);
	}

}
