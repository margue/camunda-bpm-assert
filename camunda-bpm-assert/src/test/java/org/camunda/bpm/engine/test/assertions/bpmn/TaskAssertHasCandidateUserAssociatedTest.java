package org.camunda.bpm.engine.test.assertions.bpmn;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TaskAssertHasCandidateUserAssociatedTest extends ProcessAssertTestCase {

  private static final String CANDIDATE_USER = "candidateUser";
  private static final String ASSIGNEE = "assignee";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociatedPreDefined_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // Then
    assertThat(processInstance).task().hasCandidateUserAssociated(CANDIDATE_USER);
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_PreDefined_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUserAssociated(CANDIDATE_USER);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_Predefined_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    taskService().deleteCandidateUser(taskQuery().singleResult().getId(), CANDIDATE_USER);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUserAssociated(CANDIDATE_USER);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_PreDefined_Other_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    taskService().deleteCandidateUser(taskQuery().singleResult().getId(), CANDIDATE_USER);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUserAssociated("otherCandidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_ExplicitelySet_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    assertThat(processInstance).task().hasCandidateUserAssociated("explicitCandidateUserId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_ExplicitelySet_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUserAssociated(CANDIDATE_USER);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_ExplicitelySet_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // When
    taskService().deleteCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUserAssociated("explicitCandidateUserId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_ExplicitelySet_Other_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // When
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUserAssociated("otherCandidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_MoreThanOne_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    assertThat(processInstance).task().hasCandidateUserAssociated(CANDIDATE_USER);
    // And
    assertThat(processInstance).task().hasCandidateUserAssociated("explicitCandidateUserId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_MoreThanOne_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    taskService().addCandidateUser(taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUserAssociated("otherCandidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_Null_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasCandidateUserAssociated(null);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_NonExistingTask_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    final Task task = taskQuery().singleResult();
    complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(task).hasCandidateUserAssociated(CANDIDATE_USER);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_Assigned_Success() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    final Task task = taskQuery().singleResult();
    taskService().setAssignee(task.getId(), ASSIGNEE);
    // Then
    assertThat(task).hasCandidateUserAssociated(CANDIDATE_USER);
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUserAssociated.bpmn"
  })
  public void testhasCandidateUserAssociated_Assigned_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUserAssociated"
    );
    // When
    final Task task = taskQuery().singleResult();
    taskService().deleteCandidateUser(task.getId(), CANDIDATE_USER);
    // And
    taskService().setAssignee(task.getId(), ASSIGNEE);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(task).hasCandidateUserAssociated(CANDIDATE_USER);
      }
    });
  }

}
