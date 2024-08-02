<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tickets Booked</title>
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
        }

        .container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 80%;
            max-width: 800px;
            margin: 20px auto;
            flex: 1;
        }

        h1 {
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
            background-color: #ffffff; /* Ensure the table background is white */
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

        .cancel-button {
            background-color: #FF6347;
            color: white;
            border: none;
            padding: 5px 10px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 5px;
        }

        .cancel-button:hover {
            background-color: #FF4500;
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
        <h1>Tickets Booked</h1>
        <table>
            <thead>
                <tr>
                    <th>Ticket Number</th>
                    <th>Flight Number</th>
                    <th>Carrier Name</th>
                    <th>Total Amount</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="ticket" items="${tickets}">
                    <tr>
                        <td>${ticket.ticketNumber}</td>
                        <td>${ticket.flightNumber}</td>
                        <td>${ticket.carrierName}</td>
                        <td>${ticket.totalAmount}</td>
                        <td>
                            <form action="/cancelTicketA" method="post">
                                <input type="hidden" name="ticketNumber" value="${ticket.ticketNumber}"/>
                                <button type="submit" class="cancel-button">Cancel Ticket</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="/aindex" class="home-button">Return to Home</a>
    </div>
    <footer>Flight Management System @ Infosys Springboard</footer>
</body>
</html>
