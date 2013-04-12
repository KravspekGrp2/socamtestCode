package no.ntnu.fp.gui;
import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import no.ntnu.fp.model.FactoryProject;
import no.ntnu.fp.model.Software;
import no.ntnu.fp.storage.SoftwareDbStorage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;

public class SoftwarePanel extends JPanel implements PropertyChangeListener, ActionListener, ItemListener, FocusListener {
	private static final long serialVersionUID = 1L;
	private FactoryProjectPanel fpPanel;
	private Software model;
	private Object eventSource = null;
	private JFormattedTextField versionTextField;
	private JFormattedTextField subVersionTextField;
	private JFormattedTextField urlTextField;
	private JPanel btnPanel;
	private JComboBox verCb;
	private JButton newBtn;
	private JButton saveBtn;
	private JLabel explanationLbl;
	private JLabel headerLbl;

	public SoftwarePanel(FactoryProjectPanel fpPanel) {
		this.fpPanel = fpPanel;
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
		));
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		Insets insets = new Insets(2, 2, 2, 2);
		constraints.insets = insets;
		constraints.anchor = GridBagConstraints.LINE_START;
		
		//Header:
		headerLbl = new JLabel("                             Software archive");
		Font curFont = headerLbl.getFont();
		headerLbl.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 16));
		addGridBagComponent(headerLbl, 0, constraints, 1);
		
		String[] cmbItems = {};
		verCb = new JComboBox(cmbItems);
		verCb.setPrototypeDisplayValue("WWWWWWWWW");
		verCb.addItemListener(this);
		addGridBagComponent(verCb, 2, constraints, 1);
		
		versionTextField = new JFormattedTextField();
        versionTextField.addPropertyChangeListener(this);
        versionTextField.setColumns(20);
        addGridBagLabel("Version: ", 3, constraints);
        addGridBagComponent(versionTextField, 3, constraints);
        
        subVersionTextField = new JFormattedTextField();
        subVersionTextField.addPropertyChangeListener(this);
        subVersionTextField.setColumns(20);
        addGridBagLabel("Subversion: ", 4, constraints);
        addGridBagComponent(subVersionTextField, 4, constraints);
        
        urlTextField = new JFormattedTextField();
        urlTextField.addPropertyChangeListener(this);
        urlTextField.setColumns(20);
        addGridBagLabel("Fetch URL: ", 5, constraints);
        addGridBagComponent(urlTextField, 5, constraints, 1);
        
        btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        newBtn = new JButton("New");
        newBtn.addActionListener(this);
        
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(this);
        
        btnPanel.add(newBtn);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(saveBtn);
        btnPanel.add(Box.createHorizontalStrut(5));
        constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 1.0;
		add(btnPanel, constraints);
        
        explanationLbl = new JLabel("Choose version in drop-down box, or make new by typing in the \"Version\"-field.");
        addGridBagComponent(explanationLbl, 7, constraints, 0);
		
		
	}
	
	public void collectSoftware() {
		FactoryProject fp = this.fpPanel.getModel();
		if (fp == null || fp.getSoftwareCount() <= 0)
			return;
		if (this.verCb.getItemCount() >= 1) {
			this.verCb.removeAllItems();
			for (int i=0; i < fp.getSoftwareCount(); i++) {
				this.verCb.addItem(String.valueOf(fp.getSoftware(i).getSwVersion()) + "(" + String.valueOf(fp.getSoftware(i).getSubVersion()) + ")");
			}
			this.verCb.setSelectedIndex(this.verCb.getItemCount()-1);
		}
		else {
			for (int i=0; i < fp.getSoftwareCount(); i++) {
				this.verCb.addItem(String.valueOf(fp.getSoftware(i).getSwVersion()) + "(" + String.valueOf(fp.getSoftware(i).getSubVersion()) + ")");
			}
			this.verCb.setSelectedIndex(0);
		}
	}
	
	private void clearTextFields() {
		versionTextField.setText("");
		subVersionTextField.setText("");
		urlTextField.setText("");
	}
	public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getSource() == versionTextField) {
            sourceChanged(versionTextField);
        } else if (evt.getSource() == subVersionTextField) {
            sourceChanged(subVersionTextField);
        } else if (evt.getSource() == urlTextField) {
            sourceChanged(urlTextField);
        } else {
            updatePanel(evt.getPropertyName());
        }
    }
	
	private void addGridBagLabel(String s, int row, GridBagConstraints constraints) {
		constraints.gridx = 0;
		constraints.gridy = row;
		constraints.gridheight = 1;
		constraints.gridwidth  = 1;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		add(new JLabel(s), constraints);
	}

	private void addGridBagComponent(Component c, int row, GridBagConstraints constraints) {
		constraints.gridx = 1;
		constraints.gridy = row;
		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 1.0;
		add(c, constraints);
	}

	private void addGridBagComponent(Component c, int row, GridBagConstraints constraints, int col) {
		constraints.gridx = col;
		constraints.gridy = row;
		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 1.0;
		add(c, constraints);
	}
	
	public Software getModel() {
		return this.model;
	}
	
	public void setEditable(boolean editable) {
		versionTextField.setEditable(editable);
		subVersionTextField.setEditable(editable);
		urlTextField.setEditable(editable);
	}
	
	public void setModel(Software s) {
		if (s != null) {
			if (model != null)
				model.removePropertyChangeListener(this);
			model = s;
			model.addPropertyChangeListener(this);
			updatePanel("openAct");
		}
	}
	private boolean propertyChanged(String changed, String prop, JTextField tf) {
	        return (changed == null || (changed.equals(prop) && eventSource != tf && eventSource != tf.getDocument()));
	}
	public void updatePanel(String property) {
        if (model == null) {
            setEditable(false);
        }
        if (property == null) {
            int subVersion = (model != null ? model.getSubVersion() : 0);
            subVersionTextField.setText(subVersion != 0 ? String.valueOf(subVersion) : "");
            String url = (model != null ? model.getUrl() : null);
            urlTextField.setText(url != null ? url : "");
        }
        else if (model != null && property.equals("openAct")) {
        	int version = (model != null ? model.getSwVersion() : 0);
            versionTextField.setText(version != 0 ? String.valueOf(version) : "");
            int subVersion = (model != null ? model.getSubVersion() : 0);
            subVersionTextField.setText(subVersion != 0 ? String.valueOf(subVersion) : "");
            String url = (model != null ? model.getUrl() : null);
            urlTextField.setText(url != null ? url : "");
           // verCb.setSelectedIndex(fpPanel.getModel().getSoftwareIndex(model));
        }
 
        if (propertyChanged(property, Software.VERSION_PROPERTY_NAME, versionTextField)) {
            int version = (model != null ? model.getSwVersion() : 0);
            versionTextField.setText(version != 0 ? String.valueOf(version) : "");
        } else if (propertyChanged(property, Software.SUBVERSION_PROPERTY_NAME, subVersionTextField)) {
            int subVersion = (model != null ? model.getSubVersion() : 0);
            subVersionTextField.setText(subVersion != 0 ? String.valueOf(subVersion) : "");
        } else if (propertyChanged(property, Software.URL_PROPERTY_NAME, urlTextField)) {
            String url = (model != null ? model.getUrl() : null);
            urlTextField.setText(url != null ? url : "");
        } 
	}
	private void sourceChanged(Object source) {
		eventSource = source;
		if (model == null && source == verCb) {
			model = fpPanel.getModel().getSoftware(verCb.getSelectedIndex());
			if (source == versionTextField) {
	        	if (versionTextField.getText() != null && !versionTextField.getText().equals("")) 
	        		model.setSwVersion(Integer.parseInt(versionTextField.getText()));
	        } else if (source == subVersionTextField) {
	        	if (subVersionTextField.getText() != null && !subVersionTextField.getText().equals("")) 
	        		model.setSubVersion(Integer.parseInt(subVersionTextField.getText()));
	        } else if (source == urlTextField) {
	        	if (urlTextField.getText() != null && !urlTextField.getText().equals("")) 
	        		model.setUrl(urlTextField.getText());
	        } else if (source == verCb) {
	        	//model.setSwVersion(Integer.parseInt((String)verCb.getSelectedItem()));
	        }
			return;
		}
		
		if (model == null) {
			eventSource = null;
            return;
        }
		//model = fpPanel.getModel().getSoftware(verCb.getSelectedIndex());
		if (source == versionTextField) {
			if (versionTextField.getText() != null && !versionTextField.getText().equals("")) 
				model.setSwVersion(Integer.parseInt(versionTextField.getText()));
        } 
		if (source == subVersionTextField) {
			if (subVersionTextField.getText() != null && !subVersionTextField.getText().equals("")) 
				model.setSubVersion(Integer.parseInt(subVersionTextField.getText()));
        } 
		if (source == urlTextField) {
			if (urlTextField.getText() != null && !urlTextField.getText().equals("")) 
				model.setUrl(urlTextField.getText());
        } 
		if (source == verCb) {
			this.model = fpPanel.getModel().getSoftware(verCb.getSelectedIndex());
        	//model.setSwVersion(Integer.parseInt((String)verCb.getSelectedItem()));
        	updatePanel(null);
        }
		eventSource = null;
    }
	
	public void actionPerformed(ActionEvent event) {
		
		SoftwareDbStorage swStorage = new SoftwareDbStorage();
		if (event.getSource() == newBtn) {
			Software sw = new Software();
			fpPanel.getModel().addSoftware(sw);
			this.setModel(sw);
		}
		else if (event.getSource() == saveBtn) {
			FactoryProject fp = this.fpPanel.getModel();
			this.verCb.addItem(String.valueOf(fp.getLatestSoftware().getSwVersion()) + "(" + String.valueOf(fp.getLatestSoftware().getSubVersion()) + ")");
			this.verCb.setSelectedIndex(verCb.getItemCount()-1);
			
			String message = swStorage.addSoftware(this.model, this);
			FactoryProjectPanel.setStatusBar(message);
		}
		else {
			sourceChanged(event.getSource());
		}
    }
	public void itemStateChanged(ItemEvent event) {
	    sourceChanged(event.getSource());
	}
	public void focusLost(FocusEvent event) {
        sourceChanged(event.getSource());
    }

    public void focusGained(FocusEvent event) {}
}
