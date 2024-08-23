package com.example.CRUD.Controller;

import com.example.CRUD.Model.Student;
import com.example.CRUD.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping
    public List<Student> printAll(){
        return studentService.getAll();
    }

    @GetMapping("getByid/{id}")
    public Optional<Student> display(@PathVariable Integer id){
        return studentService.showById(id);
    }

    @GetMapping("getByName/{name}")
    public List<Student> read(@PathVariable String name){
        return studentService.displayByName(name);
    }

    @PostMapping
    public Student insert(@RequestBody Student student){
        return studentService.register(student);
    }


    @PutMapping("/update/{id}")
    public Student modify(@PathVariable Integer id,@RequestBody Student student){
        return studentService.update(id,student);
    }

    @DeleteMapping("deleteAll")
    public String removeAll(){
        studentService.eraseAll();
        return "Every value are removed";
    }
    @DeleteMapping("/{id}")
    public String remove(@PathVariable (value = "id") Integer id){
        return studentService.erase(id);
    }

}
