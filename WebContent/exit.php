<?php
require_once 'hidden/session_start.php';
if(isset($_SESSION['account'])){
    unset($_SESSION);
    session_destroy();
}
header('Location: '.'/index.php');