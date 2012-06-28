@task-fixture
Feature: Task Functions for the API

  Scenario: User specifies a Task Id to Get from the API
    Given the user enters a task id of 10
    When the user tries to get the task
    Then the response status code should be 404