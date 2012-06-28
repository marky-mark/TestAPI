@task-fixture
Feature: Task Functions for the API

  Scenario: User specifies a Task Id to Get from the API
    Given the user enters a task id of 10
    When the user tries to get the task
    Then the response status code should be 404

  Scenario: User specifies a valid task Id
    Given the user enters a task id of 1
    When the user tries to get the task
    Then the response status code should be 200
    And the title field should be "title 1"
    And the description field should be "description 1"
    And the checked field should be "false"
    And the user_email field should be "mkelly"

  Scenario: User tries to create a task with validation errors
    Given the user wants to create a new task
    And the title field is set to "<html>"
    And the userId field is set to "1"
    When the user tries to create the task
    Then the response status code should be 400
    And the response should contain an error "task.title.html" for the title field
    And the response should contain an error "task.description.blank" for the description field

  Scenario: User tries to create a task with no validation errors
    Given the user wants to create a new task
    And the title field is set to "title"
    And the description field is set to "description"
    And the userId field is set to "1"
    When the user tries to create the task
    Then the response status code should be 201
