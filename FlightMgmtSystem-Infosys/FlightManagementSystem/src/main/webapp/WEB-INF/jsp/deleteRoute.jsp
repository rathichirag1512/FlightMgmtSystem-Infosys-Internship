<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Delete Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            position: relative;
        }

        .container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
            padding: 30px;
            width: 80%;
            max-width: 600px;
            text-align: center;
            margin-top: 20px;
        }

        h2 {
            color: #333333;
            font-size: 32px;
            font-weight: bold;
            margin-bottom: 20px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .message {
            font-size: 20px;
            color: #555555;
            margin-bottom: 20px;
        }

        .home-button {
            display: block;
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s;
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
            position: absolute;
            bottom: 0;
        }

        .container {
            margin-bottom: 60px; /* Ensure there is enough space above the footer */
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Delete Route</h2>
        <p class="message">${message}</p>
        <a href="viewRoute" class="home-button">Back to Routes</a>
    </div>
    <footer>Flight Management System @ Infosys Springboard</footer>
</body>
</html>
