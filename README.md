# CSC-2002S-Assignment-2

## Objective
In this assignment, the goal is to correct and extend the existing code for a multithreaded Java simulation of patrons in a club. The objective is to utilize synchronization mechanisms to ensure that the simulation adheres to specified synchronization constraints, maintains safety and liveness, and follows defined behavioral rules.

## Club Simulation
The Java simulation represents a club with various components like entrance and exit doors, a dance area, and a bar. Patrons enter, engage in activities, and eventually leave the club. The simulation involves handling multiple patrons concurrently while ensuring the simulation follows specific rules.

## Behavioral Rules
- The Start button initiates the simulation.
- The Pause button pauses/resumes the simulation.
- The Quit button terminates the simulation.
- Patrons enter and exit through respective doors, one at a time.
- The maximum number of patrons inside the club is limited.
- Patrons wait if the club limit is reached or the entrance door is occupied.
- Patrons maintain distance inside the club.
- Patrons move simultaneously block by block.
- The simulation must be deadlock-free.

## Andre the Barman 
An additional component, Andre the Barman, was added in to this submission. Club goers who want a drink must wait at the bar until they have been served by Andre.

## Code Description
The provided code consists of several classes:
- `ClubSimulation.java`: Main class for setting up and starting the simulation.
- `ClubView.java`: JPanel class for visualization.
- `CounterDisplay.java`: Class for displaying/updating counters.
- `Clubgoer.java`: Class representing each patron as a thread.
- `PeopleLocation.java`: Class for storing patron locations.
- `PeopleCounter.java`: Class for tracking people inside and outside the club.
- `ClubGrid.java`: Class representing the club grid.
- `GridBlock.java`: Class representing grid blocks.
- 'AndreBarman.java': Class representing Andre the Barman

## Running the Program
To run this program, first run the command line 
`make`
After this, run
`make run ARGS="<noClubgoers> <gridX> <gridY. <max>"`
Or, to use the standard arguments run
`make run`.


## Author
Dan Rom
RMXDAN002

