package http;

import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	
	// main method - create a web server with a web page displaying date and client IP Address
    public static void main(String[] args) throws IOException
    {
        //class variable declaration
        int portNum = 9999; // create a server socket /w port #
        ServerSocket socket; // create a server socket object

        // program start up message
        System.out.println("Webserver starting up on port " + portNum + "...");

        // try and catch block - create socket port
        try
            {
            // create the main server socket
            socket = new ServerSocket(portNum);
            System.out.println("Connected to server at port# " + portNum + ".");
            } catch (Exception e)
            {
            // display error message
            System.out.println("Error: " + e);
            return;
            } // end try
        // display listening message
        System.out.println("Waiting for connection");

        try
            {
            // create a socket for client
            Socket client = socket.accept();

            // file writer
            File log = new File("Server.txt");

            // write to text file
            PrintWriter outFile = new PrintWriter(log);

            // send response once request is made - reads the client request into a buffer
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));

            // takes the server output and sends to client socket 
            PrintWriter out = new PrintWriter(client.getOutputStream());

            // Send the Response Headers              
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("Server: Bot");
            out.println(""); // end of headers

            // send the webpage requested as an HTML page
            // Webpage Title
            out.println("<h1 align='center'>Luke Hoang - Lab Assignment 6 - CIS 3329-002 Fall2018</h1>");

            // display the client ip address
            String myIP = client.getInetAddress().toString();
            out.println("<h2>Your IP Address is : " + myIP + "</h2>");

            // display the current date and time
            Date currentDate = new Date();
            String date = currentDate.toString();
            out.println("<h2>The current time is : " + date + "</h2>");

            // Read client request message
            out.println("<h3>Request Header: </h3>");

            // Current time and client IP address
            outFile.println(date);
            outFile.println("IP Address: " + myIP);

            // create string to strore request header
            String header = in.readLine();

            // while looop to retrieve each line in the request header
            while (!header.equals(""))
                {
                header = in.readLine(); // read request header
                // display header
                outFile.println(header); // write to textfile
                out.println("<p>" + header + "</p>"); // write to web
                System.out.println(header); // print to console header
                } // end while

            // empty the buffer to close socket
            outFile.flush();
            out.flush();

            // close the output stream
            outFile.close();
            out.close();

            // close the client socket connection
            client.close();
            } // end try
        catch (Exception e)
            {
            // display error message
            System.out.println("Error: " + e);
            } // end catch

    } // end method main()
} // end class Server
