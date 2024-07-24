<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Flight Result</title>
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
        p {
            font-size: 18px;
            color: #555555;
            margin: 10px 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #dddddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        button:hover {
            background-color: #45a049;
        }
        .center {
            text-align: center;
        }
        .center-button {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .flight-details {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Search Flight Result</h1>
        <div class="flight-details">
            <p><strong>From:</strong> ${fromAirport}</p>
            <p><strong>To:</strong> ${toAirport}</p>
            <p><strong>Fare:</strong> ${fare}</p>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Flight Number</th>
                    <th>Carrier Name</th>
                    <th>Seat Capacity</th>
                    <th>Seat Booked</th>
                    <th>Seats Available</th>
                    <th>Route Id</th>
                    <th>Departure</th>
                    <th>Arrival</th>
                    <th class="center">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="flight" items="${flightList}">
                    <tr>
                        <td>${flight.flightNumber}</td>
                        <td>${flight.carrierName}</td>
                        <td>${flight.seatCapacity}</td>
                        <td>${flight.seatBooked}</td>
                        <td>${flight.seatCapacity - flight.seatBooked}</td>
                        <td>${flight.routeId}</td>
                        <td>${flight.departure}</td>
                        <td>${flight.arrival}</td>
                        <td class="center">
                            <form action="${pageContext.request.contextPath}/bookTicket" method="get">
                                <input type="hidden" name="flightNumber" value="${flight.flightNumber}">
                                <input type="hidden" name="carrierName" value="${flight.carrierName}">
                                <input type="hidden" name="routeId" value="${flight.routeId}">
                                <input type="hidden" name="seatCapacity" value="${flight.seatCapacity}">
                                <input type="hidden" name="seatBooked" value="${flight.seatCapacity - flight.seatBooked}">
                                <input type="hidden" name="departure" value="${flight.departure}">
                                <input type="hidden" name="arrival" value="${flight.arrival}">
                                <input type="hidden" name="fare" value="${fare}">
                                <input type="hidden" name="fromAirport" value="${fromAirport}">
                                <input type="hidden" name="toAirport" value="${toAirport}">
                                <button type="submit">BOOK</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <div class="center-button">
            <form action="${pageContext.request.contextPath}/index" method="get">
                <button type="submit">Return to Home</button>
            </form>
        </div>
    </div>
</body>
</html>
