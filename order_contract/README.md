# Order Contract Microservice

## Overview

The Order Contract microservice is part of a larger system designed to handle order processing. This service provides endpoints to create and manage order contracts, integrating with a PostgreSQL database and Kafka for message handling.

## Features

- **Create Order Contracts:** Accepts JSON data for orders and processes it.
- **Database Integration:** Stores order details in a PostgreSQL database.
- **Kafka Messaging:** Sends order data to a Kafka topic for further processing by other services.

## Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven** for dependency management
- **PostgreSQL** for the database
- **Kafka** for message brokering

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/doppleware/orders_processing.git
   cd orders_processing

2. **Build the Project**

    ```bash
    mvn clean install
3.  **Run Application**
    ```bash
    mvn spring-boot:run

4. **Accessing the HTML Interface**

```bashOpen the HTML File

Open src/main/resources/static/index.html in your browser. This file provides a simple UI to submit orders.

Submit a Request

Enter or edit the JSON data in the editor.
Click the "Submit Order" button to send the request to the API.