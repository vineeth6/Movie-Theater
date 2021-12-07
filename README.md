## Problem Statement
Designing and Implementing an algorithm for assigning seats within a movie theater to allocate seats for ticket booking requests.
Assume the movie theater has the seating arrangement of 10 rows x 20 seats.
Design a program to maximize both customer satisfaction and customer safety.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for executing and testing purposes. 

### Prerequisites
Jdk 1.7+
Java access from command prompt(Java Path Variable set)

### Steps to compile the program solution
Open your terminal window / command prompt.
Go to the folder where the unzipped file is saved.
Go to the src folder 
Run the command:
  ```
  javac Main.java
   ```
### Steps to execute the program
Run the following command to start the application
  ```
  java Main
  ```
It will ask you to enter a path to the input file. For example:
  ```
Enter input file path
/Users/username/Desktop/test.txt
  ```
The test.txt file has a list of request identifiers and the number of seats they requested. Example Input File Rows:
 ```
R001 2
R002 4
R003 4
R004 3
  ```
The order of the lines in the file reflects the order in which the reservation requests were received. Each line in
the file will be comprised of a reservation identifier, followed by a space, and then the number of seats requested. The reservation
identifier will have the format: "R####".

## Expected output
A file with seat allocation data that is optimal for Customer Safety and customer satisfaction.

## Assumptions
* The solution is developed for one venue/theater screen for the above mentioned layout.
* It assumes that the seat rows are labeled in a fashion where, row J is farthest from the screen and A is the closest to the screen.
* It assumes the metric for customer satisfaction is obtaining seating as farther away from the screen and obtaining all requested seats consecutively.
* It assumes the metric for Customer Safety is having as much as distance from customers in different bio-bubble.
* Each booking request is considered different bio-bubble and three seats are left vacant between each booking request.
* Only even rows are filled for customer safety.
* If a single booking request exceeds number of seats per row, the remaining requested seats are allocated in the next even numbered row.