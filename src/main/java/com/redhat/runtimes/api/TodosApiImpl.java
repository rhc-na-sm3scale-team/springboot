package com.redhat.runtimes.api;

import com.redhat.runtimes.data.TodoRepository;
import com.redhat.runtimes.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TodosApiImpl implements TodosApi {
	
	private final TodoRepository repository;
	
	public TodosApiImpl(@Autowired TodoRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Optional<NativeWebRequest> getRequest() {
		return TodosApi.super.getRequest();
	}
	
	@Override
	public ResponseEntity<List<Todo>> gettodos() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@Override
	public ResponseEntity<Todo> getTodo(UUID todoId) {
		return ResponseEntity.of(Optional.of(repository.getOne(todoId)));
	}
	
	@Override
	public ResponseEntity<Todo> createTodo(Todo todo) {
		return ResponseEntity.of(Optional.of(repository.save(todo)));
	}
	
	@Override
	public ResponseEntity<Void> deleteTodo(UUID todoId) {
		try {
			Todo todo = repository.getOne(todoId);
			repository.delete(todo);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntityNotFoundException notFound) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Override
	public ResponseEntity<Todo> updateTodo(UUID todoId, Todo todo) {
		try {
			Todo existingTodo = repository.getOne(todoId);
			existingTodo.setTitle(todo.getTitle());
			existingTodo.setDueDate(todo.getDueDate());
			existingTodo.setDescription(todo.getDescription());
			existingTodo.setComplete(todo.getComplete());
			existingTodo.setDueDate(todo.getDueDate());
			repository.save(existingTodo);
			return ResponseEntity.ok(existingTodo);
		} catch (EntityNotFoundException notFoundException) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
