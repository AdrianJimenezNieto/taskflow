package com.taskflow.domain.port.out;

import com.taskflow.domain.model.TaskList;
import java.util.List;

public interface TaskListRepositoryPort {
  
  List<TaskList> findAllByBoard(Long boardId);

  TaskList save(TaskList taskList);
}
