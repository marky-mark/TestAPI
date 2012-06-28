@task-fixture
Feature: Task Functions for the API

  Scenario: User specifies an an ad id that doesn't exist when editing an advert
    Given a recognised user
    When the user tries to update an advert specifying an unrecognised id
    Then the response status code should be 404 (Not Found)