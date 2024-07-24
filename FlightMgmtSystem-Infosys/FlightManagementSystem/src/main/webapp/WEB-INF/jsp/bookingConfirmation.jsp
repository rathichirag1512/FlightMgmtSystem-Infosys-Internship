<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Booking Confirmation</title>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
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
            flex: 1;
        }

        h1, h2 {
            text-align: center;
            color: #333333;
            margin-top: 0;
            padding-top: 10px;
            font-size: 28px;
            font-weight: bold;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        p {
            color: #555555;
            font-size: 16px;
            margin: 10px 0;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #ffffff;
        }

        table, th, td {
            border: 1px solid #cccccc;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:nth-child(odd) {
            background-color: #e6ffe6;
        }

        .home-button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 20px;
            font-weight: bold;
        }

        .home-button:hover {
            background-color: #45a049;
        }

        footer {
            text-align: center;
            color: #ffffff;
            padding: 10px 0;
            background-color: #4CAF50;
            width: 100%;
            box-shadow: 0 -1px 5px rgba(0, 0, 0, 0.1);
            position: fixed;
            bottom: 0;
            left: 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Booking Confirmation</h1>
        <p>Ticket Number: ${ticket.ticketNumber}</p>
        <p>Flight Number: ${ticket.flightNumber}</p>
        <p>Carrier Name: ${ticket.carrierName}</p>
        <p>From: ${fromAirport}</p>
        <p>To: ${toAirport}</p>

        <h2>Passenger Details:</h2>
        <table>
            <thead>
                <tr>
                    <th>Serial Number</th>
                    <th>Name</th>
                    <th>Date of Birth</th>
                    <th>Fare</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="passenger" items="${passengers}">
                    <tr>
                        <td>${passenger.embeddedId.serialNumber}</td>
                        <td>${passenger.passengerName}</td>
                        <td>${passenger.passengerDOB}</td>
                        <td>${passenger.fare}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3" align="right"><strong>Total Amount:</strong></td>
                    <td>${ticket.totalAmount}</td>
                </tr>
            </tbody>
        </table>
        <a href="/index" class="home-button">Return to Home</a>
    </div>
    <footer>Flight Management System @ Infosys Springboard</footer>
</body>
</html>
