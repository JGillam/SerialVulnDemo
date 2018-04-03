<html>
<head>
</head>
<body>
<h2>Demo for Serialization Vulnerabilities in PHP</h2>
<h3>Use Case</h3>
This is a contrived demonstration. Consider a use case for an application where some kind of ticket is initially generated from form input [1], and base64 encoded.  That ticket is then used at a later time (e.g. perhaps emailed to the user in a link back to their ticket), by submitting it back into the application [2].  This demo also include a tool for generating serialized payloads [3] for the specific vulnerable class (more on that later).

<ol>
<li><a href="ticket_form.php">Create a new ticket (generate_ticket.php)</a></li>
<li><a href="submit_ticket.php">Submit the ticket (submit_ticket.php)</a></li>
<li><a href="serialize_tool.php">Tool to generate serialized payload (serialize_tool.php)</a></li>
</ol>

<h3>The Vulnerability</h3>
The ticket is represented by a serialized PHP array of name-value pairs. The application also includes a mock vulnerable third-party class aptly called "Vulnerable", that has a vulnerable wake function.  The application is vulnerable to a deserialization flaw because it:
<ul>
	<li>Deserializes input from the users (that can therefore be manipulated); and,</li>
	<li>Includes any class containing a deserialization flaw (even if that class is not part of the normal deserialization flow of the application.</li>
</ul>
</body>
</html>