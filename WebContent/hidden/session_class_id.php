<?php
require_once 'session_start.php';
if (!isset($_SESSION['class_id'])) {
    require_once 'config.php';
    $temp = $conn;
    $conn = new mysqli($database_host, $database_account, $database_password, $database_name);
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }
    $conn->query("SET NAMES UTF8");
    $stmt = $conn->prepare("SELECT class_id FROM user WHERE account = ?");
    $stmt->bind_param("i", $account);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($class_id);
    $stmt->fetch();
    $_SESSION['class_id'] = $class_id;
    $stmt->close();
    $conn->close();
    $conn = $temp;
}