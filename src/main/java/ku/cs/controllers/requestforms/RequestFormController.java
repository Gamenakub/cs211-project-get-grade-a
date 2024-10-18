package ku.cs.controllers.requestforms;

import ku.cs.models.requestforms.RequestForm;

public interface RequestFormController {
    void initializeForm();

    void prepareDataForPDF(RequestForm form);
}
