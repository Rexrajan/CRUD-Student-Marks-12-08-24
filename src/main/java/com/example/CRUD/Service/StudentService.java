package com.example.CRUD.Service;

import com.example.CRUD.Model.Eligibility;
import com.example.CRUD.Model.Status;
import com.example.CRUD.Model.Student;
import com.example.CRUD.Repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;


    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> showById(Integer key){
        return studentRepository.findById(key);
    }

    public List<Student> displayByName(String name){
        return studentRepository.findByName(name);
    }

    public Student register(Student student){
        Integer total =  student.getTamil() + student.getEnglish() + student.getMaths()
                + student.getScience() + student.getSocial();

        student.setTotalMarks(total);

        Double percentage = (total/500d) * 100;

        student.setPercentage(percentage);

        if(student.getTotalMarks() >= 490)
            student.setStatus(Status.EXCELLENT);
        else if(student.getTotalMarks() >= 450)
            student.setStatus(Status.GOOD);
        else if(student.getTotalMarks() >= 350)
            student.setStatus(Status.AVERAGE);
        else if(student.getTotalMarks() >= 175)
            student.setStatus(Status.POOR);

        if(student.getTamil()  < 35 || student.getEnglish()  < 35 || student.getMaths() < 35 || student.getScience() < 35 || student.getSocial() < 35 ){
            student.setP_OR_f(Eligibility.F);
            student.setStatus(Status.POOR);
        }
        else{
            student.setP_OR_f(Eligibility.P);
        }

        return studentRepository.save(student);

    }

    public String erase(Integer id) {
        Optional<Student> recoveredStudent = studentRepository.findById(id);
        if (recoveredStudent.isPresent()){
            Student studentClass = recoveredStudent.get();
            studentRepository.deleteById(id);
            return "The Student "+studentClass.getName()+" is deleted";
        }
        return "The Given StudentId is not present in the database";
    }

    public void eraseAll(){
        studentRepository.deleteAll();
    }

    public Student update(Integer id,Student student){
        Student existStudent = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student ID not found in student table"));

        if(student.getName() != null){
            existStudent.setName(student.getName());
        }
        if(student.getAge() != null){
            existStudent.setAge(student.getAge());
        }
        if(student.getTamil() != null){
            existStudent.setTamil(student.getTamil());
        }
        if(student.getEnglish() != null){
            existStudent.setEnglish(student.getEnglish());
        }
        if(student.getMaths() != null){
            existStudent.setMaths(student.getMaths());
        }
        if(student.getScience() != null){
            existStudent.setScience(student.getScience());
        }
        if(student.getSocial() != null){
            existStudent.setSocial(student.getSocial());
        }

        Integer totalMarks = existStudent.getTamil() + existStudent.getEnglish() + existStudent.getMaths() + existStudent.getScience()
                              + existStudent.getSocial();

        existStudent.setTotalMarks(totalMarks);

        Double percent = (totalMarks/500d) * 100;
        existStudent.setPercentage(percent);

            if(existStudent.getTotalMarks() >= 490)
                existStudent.setStatus(Status.EXCELLENT);
            else if(existStudent.getTotalMarks() >= 450)
                existStudent.setStatus(Status.GOOD);
            else if(existStudent.getTotalMarks() >= 350)
                existStudent.setStatus(Status.AVERAGE);
            else if(existStudent.getTotalMarks() >= 175)
                existStudent.setStatus(Status.POOR);

            if(existStudent.getTamil()  < 35 || existStudent.getEnglish()  < 35 || existStudent.getMaths() < 35 || existStudent.getScience() < 35 || existStudent.getSocial() < 35 ){
                existStudent.setP_OR_f(Eligibility.F);
                existStudent.setStatus(Status.POOR);
            }
            else{
                existStudent.setP_OR_f(Eligibility.P);
            }

            return studentRepository.save(existStudent);
    }
}
