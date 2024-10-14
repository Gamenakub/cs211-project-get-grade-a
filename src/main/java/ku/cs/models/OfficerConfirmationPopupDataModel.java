package ku.cs.models;

import ku.cs.models.requestforms.RequestForm;

public record OfficerConfirmationPopupDataModel(RequestForm formObject, RequestForm.Status nextStatus) {
}
