<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>View Flights</title>
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
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

        .action-buttons {
            display: flex;
            gap: 10px;
        }

        .modify-button, .cancel-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
        }

        .modify-button:hover {
            background-color: #45a049;
        }

        .cancel-button {
            background-color: red;
        }

        .cancel-button:hover {
            background-color: darkred;
        }

        footer {
            text-align: center;
            color: #ffffff;
            padding: 10px 0;
            background-color: #4CAF50;
            width: 100%;
            box-shadow: 0 -1px 5px rgba(0, 0, 0, 0.1);
            position: relative;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>View Flights</h2>
        <table>
            <thead>
                <tr>
                    <th>Flight Number</th>
                    <th>Carrier Name</th>
                    <th>Seat Capacity</th>
                    <th>Route ID</th>
                    <th>Arrival Time</th>
                    <th>Departure Time</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="flight" items="${flights}">
                    <tr>
                        <td>${flight.flightNumber}</td>
                        <td>${flight.carrierName}</td>
                        <td>${flight.seatCapacity}</td>
                        <td>${flight.routeId}</td>
                        <td>${flight.arrival}</td>
                        <td>${flight.departure}</td>
                        <td class="action-buttons">
                            <a href="modifyFlight?flightNumber=${flight.flightNumber}" class="modify-button">Modify</a>
                            <a href="cancelFlight?flightNumber=${flight.flightNumber}" class="cancel-button">Cancel</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="index" class="home-button">Return</a>
    </div>
    <footer>Flight Management System @ Infosys Springboard</footer>
</body>
</html>
