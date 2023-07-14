package pl.kurs.vetapp.exception.doctor;

public class SalaryCanNotByNegative extends Exception{
    public SalaryCanNotByNegative() {
        super("Salary can not be negative.");
    }
}
