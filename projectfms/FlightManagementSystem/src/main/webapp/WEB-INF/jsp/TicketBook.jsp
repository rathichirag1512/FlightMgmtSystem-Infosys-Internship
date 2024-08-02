<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ticket Booking</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            min-height: 100vh;
        }

        .container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 80%;
            max-width: 800px;
            margin-top: 20px;
        }

        h1 {
            text-align: center;
            color: #333333;
            margin-top: 0;
            padding-top: 10px;
            font-size: 24px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        label {
            font-size: 16px;
            color: #555555;
        }

        input[type="text"], input[type="number"], input[type="date"] {
            padding: 8px;
            width: calc(100% - 16px);
            border: 1px solid #dddddd;
            border-radius: 4px;
            font-size: 14px;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            align-self: center;
        }

        button:hover {
            background-color: #45a049;
        }

        .passenger {
            display: flex;
            flex-direction: column;
            gap: 5px;
            margin-bottom: 10px;
        }

        .passenger input {
            width: calc(50% - 16px);
        }

        .add-passenger {
            align-self: flex-start;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Ticket Booking</h1>
        <form action="${pageContext.request.contextPath}/flight/bookTicket" method="post">
            <input type="hidden" name="flightNumber" value="${flightNumber}">
            <input type="hidden" name="carrierName" value="${carrierName}">
            <input type="hidden" name="departure" value="${departure}">
            <input type="hidden" name="arrival" value="${arrival}">

            <label for="ticketNumber">Ticket Number:</label>
            <input type="text" id="ticketNumber" name="ticketNumber" required>

            <label for="fromAirport">From:</label>
            <input type="text" id="fromAirport" name="fromAirport" value="${fromAirport}" readonly>

            <label for="toAirport">To:</label>
            <input type="text" id="toAirport" name="toAirport" value="${toAirport}" readonly>

            <label for="fare">Fare:</label>
            <input type="text" id="fare" name="fare" value="${fare}" readonly>

            <div id="passengers">
                <div class="passenger">
                    <label>Passenger 1 Name:</label>
                    <input type="text" name="passengerNames" required>
                    <label>Passenger 1 DOB:</label>
                    <input type="date" name="passengerDobs" required>
                </div>
            </div>
            <button type="button" class="add-passenger" onclick="addPassenger()">Add Passenger</button>
            <button type="submit">Book Ticket</button>
        </form>
    </div>

    <script>
        let passengerCount = 1;

        function addPassenger() {
            passengerCount++;
            const passengersDiv = document.getElementById("passengers");

            const newPassengerDiv = document.createElement("div");
            newPassengerDiv.className = "passenger";

            const nameLabel = document.createElement("label");
            nameLabel.innerText = `Passenger ${passengerCount} Name:`;
            newPassengerDiv.appendChild(nameLabel);

            const nameInput = document.createElement("input");
            nameInput.type = "text";
            nameInput.name = "passengerNames";
            nameInput.required = true;
            newPassengerDiv.appendChild(nameInput);

            const dobLabel = document.createElement("label");
            dobLabel.innerText = `Passenger ${passengerCount} DOB:`;
            newPassengerDiv.appendChild(dobLabel);

            const dobInput = document.createElement("input");
            dobInput.type = "date";
            dobInput.name = "passengerDobs";
            dobInput.required = true;
            newPassengerDiv.appendChild(dobInput);

            passengersDiv.appendChild(newPassengerDiv);
        }
    </script>
</body>
</html>
