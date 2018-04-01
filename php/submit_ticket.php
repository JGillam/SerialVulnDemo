<html>
<head></head>
<body>
	<?PHP
	include "vulnerable.php";

	if (isset($_POST["ticket"])) {
		$serialized = base64_decode($_POST["ticket"]);

		?>

		Serialized value:  
		<?PHP 
		echo ($serialized); 

		// Hint: try using serialize_tool.php to generate a serialized payload!
		$ticket = unserialize($serialized);

		echo ("<br><br>First: ".$ticket["first"]);
		echo ("<br>Last: ".$ticket["last"]);

	} else { ?>
<form method="post" action="submit_ticket.php">
  Submit your ticket:<br>
  <input type="text" name="ticket"><br>
  <input type="submit" value="Submit">
</form>

<?PHP	}; ?>
<br><br><a href="index.php"><--- Back</a>
</body>
</html>