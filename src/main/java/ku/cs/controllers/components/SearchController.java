package ku.cs.controllers.components;

import javafx.scene.control.TextField;
import ku.cs.controllers.components.tables.TableComponentController;
import ku.cs.models.collections.Searchable;

import java.io.IOException;
import java.util.ArrayList;

public class SearchController<T> {
    private TextField searchTextField;
    private TableComponentController tableComponentController;
    private Searchable<T> data;
    private String filterContext;


    public SearchController(TextField searchTextField, TableComponentController table, Searchable<T> data) {
        this.searchTextField = searchTextField;
        this.tableComponentController = table;
        this.data = data;

    }
    public void searchFilter() {
        String searchTerm = searchTextField.getText();
        ArrayList<T> resultSearch;
        resultSearch=data.search(searchTerm);
        if (!(filterContext ==null)) {
            ArrayList<T> resultFilter;
            resultFilter=data.filter(filterContext);
            resultSearch.retainAll(resultFilter);
        }
        try {
            tableComponentController.setDisplayModels(resultSearch);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setFilterContext(String filterContext) {
        this.filterContext = filterContext;
    }

    public void resetFilter(){
        setFilterContext(null);
        ArrayList<T> resultSearch;
        resultSearch=data.search("");
        resultSearch.retainAll(data.search(searchTextField.getText()));
        try {
            tableComponentController.setDisplayModels(resultSearch);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
