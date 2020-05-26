package service;

import java.util.List;

import model.Patient;
import util.CustomException;
import util.DatabaseException;

public interface PatientService {

    /* create patient */
    public Patient create(Patient patient) throws CustomException, DatabaseException;

    /* retrieve a patient using id */
    public Patient retrieve(Integer id) throws CustomException;

    /* update patient */
    public Patient update(Patient patient) throws CustomException;

    /* delete patient */
    public void delete(Integer id) throws CustomException;

    /* List all patients */
    public List<Patient> list() throws CustomException;
}