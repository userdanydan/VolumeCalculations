# Ticket Sales Calculation Tool

This project was initially developed to solve a real-world problem for an event organizer who needed a way to calculate ticket sales revenue across multiple venues, based on scanned attendance data. 

## The Challenge

The raw data was provided in `.csv` files from scanners at each venue. The calculation logic to determine how to fairly split revenue across venues based on individual visitor attendance wasn't immediately obvious.

## The Original Solution (PHP)

[The PHP script](index.php)

A "quick and dirty" PHP script was written to address the immediate need. However, it suffered from:

* **Cryptic Logic:** The algorithm was complex and difficult to understand or modify.
* **Maintainability Issues:** The lack of structure made future updates a potential nightmare.

Example code snippets included here to showcase the PHP's lack of clarity in index.php

## Embracing Object-Oriented Programming (Java)

To create a more robust and maintainable solution, the project was reimagined using Java and object-oriented principles. The result:

* **Clear Structure:** The code is organized into classes (`Venue`, `PartyGoer`, `Volume`) that model the problem domain.
* **Testable Code:** Test Driven Development was used, ensuring the logic works as intended.
* **Flexibility:** The design allows for easier adaptation to future changes (e.g., different pricing rules).

## How It Works (Simplified)

1. **Venue Class:** Reads and parses attendance data from `.csv` files.
2. **PartyGoer Class:** Represents an individual attendee, tracking which venues they visited.
3. **Volume Class:**
   * Aggregates all `Venue` and `PartyGoer` objects.
   * Calculates revenue due to each venue based on visits.
   * Computes the total revenue.

## Project Status

The Java implementation is complete and functional.

## Key Takeaways

This project demonstrates the power of object-oriented design for solving complex problems. By embracing OOP, we achieved a solution that is:

* **More understandable**
* **Easier to maintain**
* **Highly extensible**

It's a testament to the value of investing in good software engineering practices, even for seemingly simple tasks.
