<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Ticket</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 {
            background-color: #4CAF50; /* Green color */
            color: white;
            text-align: center;
            padding: 20px;
            margin: 0;
            width: 100%;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            font-size: 36px;
            text-transform: uppercase;
        }

        h2 {
            margin: 20px 0;
            font-size: 24px;
            text-align: center;
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #4CAF50;
        }

        .flight-path {
            margin: 0 10px;
            color: #333;
        }

        form {
            max-width: 600px;
            width: 100%;
            background-color: #ffffff;
            padding: 20px;
            margin-top: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        form input[type="text"], form input[type="number"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        form button {
            background-color: #4CAF50; /* Green color */
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
            width: 100%;
        }

        form button:hover {
            background-color: #45a049;
        }

        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50; /* Green color */
            color: white;
        }

        td input {
            width: 95%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <h1>Book Ticket</h1>
    <h2>From: ${fromAirport} <span class="flight-path">----------------------------------></span> ${toAirport}</h2>
    <form action="/bookTicket" method="post">
        <label for="ticketNumber">Ticket Number:</label>
        <input id="ticketNumber" name="ticketNumber" value="${ticket.ticketNumber}" />
        
        <label for="flightNumber">Flight Number:</label>
        <input id="flightNumber" name="flightNumber" value="${ticket.flightNumber}" />
        
        <label for="carrierName">Airline Name:</label>
        <input id="carrierName" name="carrierName" value="${ticket.carrierName}" />
        
        <label for="totalAmount">Fare:</label>
        <input id="totalAmount" name="totalAmount" value="${ticket.totalAmount}" />
        
        <input type="hidden" name="routeId" value="${ticket.routeId}" />
        <input type="hidden" name="fromAirport" value="${fromAirport}" />
        <input type="hidden" name="toAirport" value="${toAirport}" />
         
        <h3>Enter Passenger Details</h3>
        <table>
            <tr>
                <th>Name</th>
                <th>Date of Birth</th>
            </tr>
            <c:forEach var="i" begin="0" end="5">
                <tr>
                    <td><input type="text" name="name" value="${passengerNames[i]}"/></td>
                    <td><input type="text" name="dob" value="${passengerDOBs[i]}"/></td>
                </tr>
            </c:forEach>
        </table>

        <button type="submit">Book</button>
    </form>
</body>
</html>
