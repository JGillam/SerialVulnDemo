<?php

class Vulnerable
{
   private $hook;

   function __construct()
   {
      // some PHP code...
      echo("Constructing vulnerable...<br>");
   }

   function __wakeup()
   {
   	  echo("<br><br>Running wakeup...<br>");
      if (isset($this->hook)) eval($this->hook);
   }
}

echo ("<br>included vulnerable class...");