# ✈️ Airline Booking System Prototype

## Overview

This prototype demonstrates the implementation of **shared** and **exclusive** database locks within an airline booking system. It serves as a practical example to understand how different locking mechanisms affect data integrity and concurrency during seat reservation processes.

## Features

- **Shared Locks**: Implemented to allow multiple transactions to read seat availability concurrently without interference.
- **Exclusive Locks**: Ensures that once a seat is being booked, no other transaction can read or write to that seat until the booking is complete.
- **Concurrency Handling**: Manages multiple booking requests simultaneously, maintaining data consistency and preventing issues like double bookings.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Ensure you have JDK installed on your machine.
- **Database Setup**: A relational database (e.g., MySQL, PostgreSQL) with appropriate tables for flights and bookings.

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/saurabhs13/airline-booking.git
   cd airline-booking
   ```

2. **Configure the Database**:
   - Update the database connection settings in the project's configuration file to match your database credentials.

3. **Build the Project**:
   - Use your preferred build tool (e.g., Maven, Gradle) to compile the project.

4. **Run the Application**:
   - Execute the main class to start the application.

## Usage

- **Booking a Seat**: When a booking request is made, the system applies an exclusive lock on the selected seat to prevent other transactions from accessing it until the booking is finalized.
- **Viewing Available Seats**: Users can view available seats, during which shared locks are applied, allowing multiple users to read the seat availability concurrently.

## Understanding Database Locks

- **Shared Locks**: Allow multiple transactions to read a resource but prevent any from modifying it. Ideal for operations where data consistency during reads is crucial.
- **Exclusive Locks**: Restrict both read and write access to a resource by other transactions. Essential during operations that modify data to prevent conflicts and ensure integrity.

For a deeper dive into database locking mechanisms, consider exploring the following resources:

- [Transaction Locking and Row Versioning Guide - SQL Server](https://learn.microsoft.com/en-us/sql/relational-databases/sql-server-transaction-locking-and-row-versioning-guide)
- [Understanding Database Locks: Managing Concurrency in Databases](https://codecurated.com/blog/understanding-database-locks-managing-concurrency-in-databases/)

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your enhancements.

## License

This project is licensed under the Apache-2.0 License. See the [LICENSE](LICENSE) file for details.

