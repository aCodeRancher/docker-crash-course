package com.in28minutes.springboot.web.service;

import com.in28minutes.springboot.web.model.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(TodoService.class)
class TodoServiceTest {

    @Autowired
    TodoService todoService;

    @Test
    public void retrieveTodos(){
        List<Todo> todos = todoService.retrieveTodos("in28minutes");
        assertTrue( todos.size() == 3);
    }

    @Test
    public void retrieveTodo(){
         Todo todo = todoService.retrieveTodo(1);
         assertTrue(todo.getId()==1);
         assertTrue(todo.getDesc().equals("Learn Spring MVC"));
         assertTrue(todo.getUser().equals("in28minutes"));
    }
}