Feature: Search
    As a traveller,
    I want to search for available buses between two cities for a selected date so that I can view and evaluate travel options based on my requirements.

    Background:
        Given the home is open
    
    @smoke
        Scenario: Search A Bus
        When I set the source location chennai
        And I set the destination to banglore
        And Select the date
        Then I hit the search
        And I take the first bus card
        Then I checked the Bus driver is there
        Then I checked Departure and Arival time is there
        Then I checked available seat count is there
        Then I set the filter AC
        Then I checked the result has AC
        Then I check whether the seat details is there 
        


        Acceptance Criteria

The user should be able to enter valid source and destination cities and select them from suggested options.
The user should be able to select a future journey date.
On initiating search, the system should display a list of available buses.
Each result should display:

Bus operator name
Departure and arrival timings
Fare
Available seat count (if applicable)


The user should be able to apply filters (e.g., AC, Sleeper) and see refined results.
The system should dynamically update results when filters are applied.
The user should be able to view seat details for a selected bus.

