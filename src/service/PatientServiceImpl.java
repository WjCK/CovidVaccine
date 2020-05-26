package service;

import java.util.List;

import database.PatientDAO;
import database.PatientDAOImpl;
import database.TransactionDB;
import model.Patient;
import util.CustomException;
import util.DatabaseException;

public class PatientServiceImpl implements PatientService {

    private PatientDAO patientDAO;

    public PatientDAO getPatientDAO() {
        return patientDAO;
    }

    public void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    /**
     * Create student
     */
    public Patient create(Patient patient) throws CustomException {
        try {
            patientDAO = new PatientDAOImpl();
            /* instantiate persistence and prepare the business transaction */
            getPatientDAO().setTransactionDB(new TransactionDB());
            /* open transaction */
            getPatientDAO().getTransaction().openTransaction(false);
            /* create patient */
            getPatientDAO().create(patient);
            /* close transaction */
            getPatientDAO().getTransaction().closeTransaction();
        } catch (DatabaseException e) {
            throw new CustomException(e, e.getMsg());
        } catch (Exception e) {
            throw new CustomException(e, e.getMessage());
        }

        return patient;
    }

    /**
     * retrieve a patient using id
     */
    public Patient retrieve(Integer id) throws CustomException {
        Patient patient = new Patient();
        try {
            patientDAO = new PatientDAOImpl();

            getPatientDAO().setTransactionDB(new TransactionDB());

            getPatientDAO().getTransaction().openTransaction(true);

            patient = getPatientDAO().retrieve(id);

            getPatientDAO().getTransaction().closeTransaction();
        } catch (DatabaseException e) {
            throw new CustomException(e, e.getMsg());
        } catch (Exception e) {
            throw new CustomException(e, e.getMessage());
        }
        return patient;
    }

    /**
     * Update patient
     */
    public Patient update(Patient patient) throws CustomException {
        try {
            patientDAO = new PatientDAOImpl();
            /* instantiate persistence and prepare the business transaction */
            getPatientDAO().setTransactionDB(new TransactionDB());
            /* open transaction */
            getPatientDAO().getTransaction().openTransaction(false);
            /* create patient */
            getPatientDAO().update(patient);
            /* close transaction */
            getPatientDAO().getTransaction().closeTransaction();
        } catch (DatabaseException e) {
            throw new CustomException(e, e.getMsg());
        } catch (Exception e) {
            throw new CustomException(e, e.getMessage());
        }
        return patient;
    }

    /**
     * delete patient
     */
    public void delete(Integer id) throws CustomException {
        try {
            patientDAO = new PatientDAOImpl();

            getPatientDAO().setTransactionDB(new TransactionDB());

            getPatientDAO().getTransaction().openTransaction(true);

            getPatientDAO().delete(id);

            getPatientDAO().getTransaction().closeTransaction();
        } catch (DatabaseException e) {
            throw new CustomException(e, e.getMsg());
        } catch (Exception e) {
            throw new CustomException(e, e.getMessage());
        }

    }

    /**
     * List all patients
     */
    public List<Patient> list() throws CustomException {
        List<Patient> list = null;
        try {
            patientDAO = new PatientDAOImpl();
            getPatientDAO().setTransactionDB(new TransactionDB());

            getPatientDAO().getTransaction().openTransaction(true);

            list = getPatientDAO().list();

            getPatientDAO().getTransaction().closeTransaction();

            return list;
        } catch (DatabaseException e) {
            throw new CustomException(e, e.getMsg());
        } catch (Exception e) {
            throw new CustomException(e, e.getMessage());
        }
    }

}