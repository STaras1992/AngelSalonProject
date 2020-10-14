package driver;

import java.io.File;

import controller.AngelController;
import controller.Controller;
import model.AngelModel;
import model.Model;
import view.AngelView;
import view.View;

public class AngelDriver {

	public static void main(String[] args) {
		Model model = new AngelModel();
		View view = new AngelView();
		Controller controller = new AngelController(model, view);
		((AngelModel)model).addPropertyChangeListener(controller);
		((AngelView)view).addPropertyChangeListener(controller);
		model.setFileName("Angel_Database");//set name of file will save our database.
		if(new File("Angel_Database").exists())//if database is not empty,load database
		    model.loadData(model.getFileName());//load data from file
		view.start();
		
	}
}
