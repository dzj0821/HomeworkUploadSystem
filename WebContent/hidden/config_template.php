<?php
$account_pattern = "/^[0-9]{6,10}$/";
$password_pattern = "/^[A-Za-z0-9]{6,18}$/";
$user_name_pattern_js = "/^[\u4E00-\u9FFF]{2,4}/";
$user_name_pattern_php = "/^[\x{4e00}-\x{9fa5}]{2,4}/u";
$class_id_pattern = "/^[0-9]+$/";
$database_host = "localhost:3306";
$database_account = "homework_upload";
$database_password = "";
$database_name = "homework_upload";
$salt = "";
$black_suffix = array("php", "exe", "html", "css", "js");