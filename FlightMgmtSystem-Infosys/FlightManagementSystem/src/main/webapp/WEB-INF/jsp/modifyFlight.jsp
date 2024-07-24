<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Modify Flight</title>
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
            max-width: 600px;
            margin-top: 20px;
        }

        h2 {
            text-align: center;
            color: #333333;
            margin-top: 0;
            padding-top: 10px;
            font-size: 28px;
            font-weight: bold;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-top: 10px;
            font-weight: bold;
        }

        input {
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #cccccc;
            border-radius: 5px;
        }

        .form-buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .submit-button, .cancel-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 16px;
        }

        .submit-button:hover, .cancel-button:hover {
            background-color: #45a049;
        }

        .cancel-button {
            background-color: red;
        }

        .cancel-button:hover {
            background-color: darkred;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Modify Flight</h2>
        <form action="updateFlight" method="post">
            <input type="hidden" name="flightNumber" value="${flight.flightNumber}" />
            <label for="carrierName">Carrier Name:</label>
            <input type="text" id="carrierName" name="carrierName" value="${flight.carrierName}" readonly />
            
            <label for="departure">Departure Time:</label>
            <input type="text" id="departure" name="departure" value="${flight.departure}" />
            
            <label for="arrival">Arrival Time:</label>
            <input type="text" id="arrival" name="arrival" value="${flight.arrival}" />
            
            <div class="form-buttons">
                <button type="submit" class="submit-button">Update</button>
                <a href="viewFlights" class="cancel-button">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>
