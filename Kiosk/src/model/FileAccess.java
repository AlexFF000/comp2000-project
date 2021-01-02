package model;

import java.io.File;
import java.util.ArrayList;

public abstract class FileAccess {

    public final ArrayList<JsonObject> loadItems(){
        // Get file object representing the file to be read from
        File dataFile = getFile();
        // Read the file data into a list of JsonObjects and return
        return readItems(dataFile);
    }

    public final void saveItems(ArrayList<JsonObject> items){
        // Get file object representing the file to be written to
        File dataFile = getFile();
        writeItems(items, dataFile);
    }

    // Hook for getting correct file depending on type of object
    protected abstract File getFile();
    // Hook for reading objects from the file (as each type of object will be stored with different fields)
    protected abstract ArrayList<JsonObject> readItems(File file);
    // Hook for writing objects to the file
    protected abstract void writeItems(ArrayList<JsonObject> items, File file);
}
