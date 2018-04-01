<?php

class Vulnerable
{
   private $hook;

   function __construct()
   {
      // some PHP code...
      echo("<i>Constructing 'Vulnerable' class...</i><br>");
   }

   function __wakeup()
   {
   	  echo("<br><br>Running wakeup function in 'Vulnerable' class...<br>");
      if (isset($this->hook)) eval($this->hook);
   }
}

echo ("<br></i>Included 'Vulnerable' class...</i><br>");