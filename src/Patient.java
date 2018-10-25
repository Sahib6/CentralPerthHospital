import javafx.beans.property.SimpleStringProperty;

public class Patient
{
    private SimpleStringProperty patientId = new SimpleStringProperty();
    private SimpleStringProperty patientName = new SimpleStringProperty();
    private SimpleStringProperty patientSurname = new SimpleStringProperty();

    public Patient()
    {
        this("","","");
    }

    public Patient(String patientId, String patientName, String patientSurname)
    {
        setPatientId(patientId);
        setPatientName(patientName);
        setPatientSurname(patientSurname);
    }

    public String getPatientId() {
        return patientId.get();
    }

    public SimpleStringProperty patientIdProperty() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId.set(patientId);
    }

    public String getPatientName() {
        return patientName.get();
    }

    public SimpleStringProperty patientNameProperty() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName.set(patientName);
    }

    public String getPatientSurname() {
        return patientSurname.get();
    }

    public SimpleStringProperty patientSurnameProperty() {
        return patientSurname;
    }

    public void setPatientSurname(String patientSurname) {
        this.patientSurname.set(patientSurname);
    }
}
