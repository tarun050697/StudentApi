package student.models;

import java.util.List;

public class StudentInputRequest {
    private List<Student> students;

    public StudentInputRequest() {
    }

    public StudentInputRequest(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
