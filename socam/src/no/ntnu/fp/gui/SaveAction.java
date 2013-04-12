package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;

import no.ntnu.fp.storage.*;

/**
 * Implements the command for saving the {@link no.ntnu.fp.model.Project}.
 * 
 * @author Thomas &Oslash;sterlie
 * 
 * @version $Revision: 1.4 $ - $Date: 2008-04-24 19:22:23 $
 */
class SaveAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	/**
	 * The parent component.
	 */
	private ProjectPanel projectPanel;
	private FactoryProjectPanel fProjectPanel;
	/**
	 * Default constructor.  Initialises member variables.
	 * 
	 * @param projectPanel The parent component.
	 */
	public SaveAction(ProjectPanel projectPanel) {
		putValue(Action.NAME, "Save");
		this.projectPanel = projectPanel;
		this.fProjectPanel = null;
	}
	public SaveAction(FactoryProjectPanel fProjectPanel) {
		putValue(Action.NAME, "Save");
		this.fProjectPanel = fProjectPanel;
		this.projectPanel = null;
	}
	/**
	 * Invoked when an action occurs.  If the model has not yet been saved,
	 * the {@link SaveAsAction} is invoked instead.
	 * 
	 * @param e The action event.
	 */
	public void actionPerformed(ActionEvent event) {
		if (this.fProjectPanel == null) {
			PersonListModel plm = projectPanel.getModel();
			GarageDbStorage gdbs = new GarageDbStorage();
			gdbs.save(plm.getProject());
		}
		else {
			//implements this.
		}
		/** OLD XML SAVE CODE
		URL path = plm.getUrl();
		
		if (path == null) {
			SaveAsAction saveAs = new SaveAsAction(projectPanel);
			saveAs.actionPerformed(null);

		} else {
			try {
				FileStorage file = new FileStorage();
				file.save(plm.getUrl(), plm.getProject());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		*/
	}
	
}
