package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface Controller extends PropertyChangeListener {
	
	@Override
	default void propertyChange(PropertyChangeEvent evt) {
	}
   
}
