<html>
<head></head>
<body>
Generating ticket...<br><br>
<?php
$ticketdata = array("first" => $_POST["firstname"], "last" => $_POST["lastname"]);
$serialized = serialize($ticketdata);

echo ($serialized);
?>
<br><br>
Based 64 Encoded Ticket:

<br><br>
<?php

echo (base64_encode($serialized));

?>
</body>
</html>