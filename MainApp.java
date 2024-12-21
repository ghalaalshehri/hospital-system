package mainapp;

import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {
    private static ArrayList<Patient> patients = new ArrayList<>();
    private static ArrayList<Doctor> doctors = new ArrayList<>();
    private static ArrayList<Receptionist> receptionists = new ArrayList<>();
    private static ArrayList<Appointment> appointments = new ArrayList<>();
    private static int patientCounter = 100;
    private static int doctorCounter = 200;
    private static int receptionistCounter = 300;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Hospital Management System ===");
        boolean running = true;
        while (running) {
            Utils.clearScreen();
            System.out.println("Main Menu:");
            System.out.println("1. Create New Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    createNewAccount(sc);
                    break;
                case "2":
                    loginUser(sc);
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        sc.close();
        System.out.println("System closed.");
    }

    private static String readNonEmptyLine(Scanner sc, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static void createNewAccount(Scanner sc) {
        Utils.clearScreen();
        System.out.println("Create Account:");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Receptionist");
        System.out.print("Choose: ");
        String type = sc.nextLine().trim();

        if (!type.equals("1") && !type.equals("2") && !type.equals("3")) {
            System.out.println("Invalid choice.");
            return;
        }

        String name = readNonEmptyLine(sc, "Enter name: ");
        String username = readNonEmptyLine(sc, "Enter username: ");
        String password = readNonEmptyLine(sc, "Enter password: ");

        if (type.equals("1")) {
            String userID = "P" + (patientCounter++);
            String dob = readNonEmptyLine(sc, "Enter Date of Birth: ");
            Patient p = new Patient(userID, name, username, password, userID, dob);
            patients.add(p);
            System.out.println("Patient account created: " + p.toString());
        } else if (type.equals("2")) {
            String userID = "D" + (doctorCounter++);
            String spec = readNonEmptyLine(sc, "Enter Specialty: ");
            String loc = readNonEmptyLine(sc, "Enter Location: ");
            Doctor d = new Doctor(userID, name, username, password, spec, loc);
            doctors.add(d);
            System.out.println("Doctor account created: " + d.toString());
        } else {
            String userID = "R" + (receptionistCounter++);
            String contactInfo = readNonEmptyLine(sc, "Enter contact info: ");
            String empID = "E" + (int)(Math.random()*9999);
            Receptionist r = new Receptionist(userID, name, username, password, empID, contactInfo);
            receptionists.add(r);
            System.out.println("Receptionist account created: " + r.toString());
        }
    }

    private static void loginUser(Scanner sc) {
        Utils.clearScreen();
        System.out.println("Login as:");
        System.out.println("1. Receptionist");
        System.out.println("2. Doctor");
        System.out.println("3. Patient");
        System.out.print("Choose: ");
        String type = sc.nextLine().trim();

        String uname = readNonEmptyLine(sc, "Enter username: ");
        String pass = readNonEmptyLine(sc, "Enter password: ");

        if (type.equals("1")) {
            Receptionist rcp = null;
            for (Receptionist r : receptionists) {
                if (r.authenticate(uname, pass)) {
                    rcp = r;
                    break;
                }
            }
            if (rcp != null) {
                rcp.login();
                receptionistMenu(sc, rcp);
                rcp.logout();
            } else {
                System.out.println("Invalid credentials for Receptionist.");
            }
        } else if (type.equals("2")) {
            Doctor loggedDoc = null;
            for (Doctor d : doctors) {
                if (d.authenticate(uname, pass)) {
                    loggedDoc = d;
                    break;
                }
            }
            if (loggedDoc != null) {
                loggedDoc.login();
                doctorMenu(sc, loggedDoc);
                loggedDoc.logout();
            } else {
                System.out.println("Invalid credentials for Doctor.");
            }
        } else if (type.equals("3")) {
            Patient loggedPat = null;
            for (Patient p : patients) {
                if (p.authenticate(uname, pass)) {
                    loggedPat = p;
                    break;
                }
            }
            if (loggedPat != null) {
                loggedPat.login();
                patientMenu(sc, loggedPat);
                loggedPat.logout();
            } else {
                System.out.println("Invalid credentials for Patient.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void receptionistMenu(Scanner sc, Receptionist receptionist) {
        boolean done = false;
        while (!done) {
            Utils.clearScreen();
            System.out.println("Receptionist Menu:");
            System.out.println("1. Add Patient");
            System.out.println("2. Remove Patient");
            System.out.println("3. Add Doctor");
            System.out.println("4. Remove Doctor");
            System.out.println("5. Schedule Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. List Patients");
            System.out.println("8. List Doctors");
            System.out.println("9. List Appointments");
            System.out.println("0. Logout");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    String pName = readNonEmptyLine(sc, "Enter name: ");
                    String pUser = readNonEmptyLine(sc, "Enter username: ");
                    String pPass = readNonEmptyLine(sc, "Enter password: ");
                    String pID = "P" + (patientCounter++);
                    String dob = readNonEmptyLine(sc, "Enter DOB: ");
                    Patient newP = new Patient(pID, pName, pUser, pPass, pID, dob);
                    receptionist.addPatient(patients, newP);
                    break;
                case "2":
                    String remPID = readNonEmptyLine(sc, "Enter Patient ID to remove: ");
                    receptionist.removePatient(patients, remPID);
                    break;
                case "3":
                    String dName = readNonEmptyLine(sc, "Enter Doctor name: ");
                    String dUser = readNonEmptyLine(sc, "Enter username: ");
                    String dPass = readNonEmptyLine(sc, "Enter password: ");
                    String dID = "D" + (doctorCounter++);
                    String spec = readNonEmptyLine(sc, "Enter Specialty: ");
                    String loc = readNonEmptyLine(sc, "Enter Location: ");
                    Doctor newD = new Doctor(dID, dName, dUser, dPass, spec, loc);
                    receptionist.addDoctor(doctors, newD);
                    break;
                case "4":
                    String remDID = readNonEmptyLine(sc, "Enter Doctor ID to remove: ");
                    receptionist.removeDoctor(doctors, remDID);
                    break;
                case "5":
                    if (patients.isEmpty() || doctors.isEmpty()) {
                        System.out.println("Need at least one patient and one doctor.");
                    } else {
                        String aID = readNonEmptyLine(sc, "Enter Appointment ID: ");
                        String aDate = readNonEmptyLine(sc, "Enter date: ");
                        String aTime = readNonEmptyLine(sc, "Enter time: ");
                        String aType = readNonEmptyLine(sc, "Enter type: ");
                        System.out.println("Select Patient by index:");
                        for (int i = 0; i < patients.size(); i++) {
                            System.out.println(i + ": " + patients.get(i).getName());
                        }
                        int pIndex = readIndex(sc, patients.size());
                        Patient selP = patients.get(pIndex);
                        System.out.println("Select Doctor by index:");
                        for (int i = 0; i < doctors.size(); i++) {
                            System.out.println(i + ": " + doctors.get(i).getName() + " (" + doctors.get(i).getSpecialty() + ")");
                        }
                        int dIndex = readIndex(sc, doctors.size());
                        Doctor selD = doctors.get(dIndex);
                        Appointment app = new Appointment(aID, aDate, aTime, aType, selD, selP);
                        receptionist.scheduleAppointment(appointments, app);
                    }
                    break;
                case "6":
                    String cAppID = readNonEmptyLine(sc, "Enter Appointment ID to cancel: ");
                    receptionist.cancelAppointment(appointments, cAppID);
                    break;
                case "7":
                    if (patients.isEmpty()) {
                        System.out.println("No patients.");
                    } else {
                        Utils.printList(patients);
                    }
                    waitForEnter(sc);
                    break;
                case "8":
                    if (doctors.isEmpty()) {
                        System.out.println("No doctors.");
                    } else {
                        Utils.printList(doctors);
                    }
                    waitForEnter(sc);
                    break;
                case "9":
                    if (appointments.isEmpty()) {
                        System.out.println("No appointments.");
                    } else {
                        Utils.printList(appointments);
                    }
                    waitForEnter(sc);
                    break;
                case "0":
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    private static void doctorMenu(Scanner sc, Doctor doc) {
        boolean done = false;
        while(!done) {
            Utils.clearScreen();
            System.out.println("Doctor Menu:");
            System.out.println("1. View My Appointments");
            System.out.println("2. Update Info");
            System.out.println("3. Set Availability");
            System.out.println("0. Logout");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    doc.viewMyAppointments(appointments);
                    waitForEnter(sc);
                    break;
                case "2":
                    String nspec = readNonEmptyLine(sc, "Enter new Specialty: ");
                    String nloc = readNonEmptyLine(sc, "Enter new Location: ");
                    doc.updateDoctorInfo(nspec, nloc);
                    waitForEnter(sc);
                    break;
                case "3":
                    System.out.println("Set availability:");
                    System.out.println("1. Available");
                    System.out.println("2. Not Available");
                    String av = sc.nextLine().trim();
                    boolean available = av.equals("1");
                    doc.setDoctorAvailability(available);
                    waitForEnter(sc);
                    break;
                case "0":
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    waitForEnter(sc);
                    break;
            }
        }
    }

    private static void patientMenu(Scanner sc, Patient pat) {
        boolean done = false;
        while(!done) {
            Utils.clearScreen();
            System.out.println("Patient Menu:");
            System.out.println("1. Book Appointment");
            System.out.println("2. Cancel Appointment");
            System.out.println("3. Reschedule Appointment");
            System.out.println("4. Update Info");
            System.out.println("5. View My Appointments");
            System.out.println("0. Logout");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    if (doctors.isEmpty()) {
                        System.out.println("No doctors available.");
                    } else {
                        String apID = readNonEmptyLine(sc, "Enter Appointment ID: ");
                        String apDate = readNonEmptyLine(sc, "Enter date: ");
                        String apTime = readNonEmptyLine(sc, "Enter time: ");
                        String apType = readNonEmptyLine(sc, "Enter type: ");
                        System.out.println("Select Doctor by index:");
                        for (int i = 0; i < doctors.size(); i++) {
                            Doctor d = doctors.get(i);
                            System.out.println(i + ": " + d.getName() + " (" + d.getSpecialty() + ", " +
                                    (d.getDoctorAvailability() ? "Available" : "Not Available") + ")");
                        }
                        int di = readIndex(sc, doctors.size());
                        Doctor chosenD = doctors.get(di);
                        if (!chosenD.getDoctorAvailability()) {
                            System.out.println("This doctor is not available.");
                        } else {
                            Appointment newA = new Appointment(apID, apDate, apTime, apType, chosenD, pat);
                            pat.bookAppointment(appointments, newA);
                        }
                    }
                    waitForEnter(sc);
                    break;
                case "2":
                    String capID = readNonEmptyLine(sc, "Enter Appointment ID to cancel: ");
                    pat.cancelAppointment(appointments, capID);
                    waitForEnter(sc);
                    break;
                case "3":
                    String rapID = readNonEmptyLine(sc, "Enter Appointment ID to reschedule: ");
                    String rDate = readNonEmptyLine(sc, "Enter new date: ");
                    String rTime = readNonEmptyLine(sc, "Enter new time: ");
                    pat.rescheduleAppointment(appointments, rapID, rDate, rTime);
                    waitForEnter(sc);
                    break;
                case "4":
                    String nName = readNonEmptyLine(sc, "Enter new Name: ");
                    String nDOB = readNonEmptyLine(sc, "Enter new DOB: ");
                    pat.updatePatientInfo(nName, nDOB);
                    waitForEnter(sc);
                    break;
                case "5":
                    pat.viewMyAppointments(appointments);
                    waitForEnter(sc);
                    break;
                case "0":
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    waitForEnter(sc);
                    break;
            }
        }
    }

    private static int readIndex(Scanner sc, int size) {
        int index = -1;
        while (true) {
            System.out.print("Enter index: ");
            String input = sc.nextLine().trim();
            try {
                index = Integer.parseInt(input);
                if (index < 0 || index >= size) {
                    System.out.println("Index out of range, try again.");
                } else {
                    return index;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    private static void waitForEnter(Scanner sc) {
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }
}
