<?php
require_once 'hidden/session_start.php';
require_once 'hidden/config.php';

if (!isset($_SESSION['account']) || !isset($_GET['id'])) {
    header('Location: ' . '/index.php');
    exit();
}

$account = $_SESSION['account'];
$id = $_GET['id'];

$conn = new mysqli($database_host, $database_account, $database_password, $database_name);
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
$conn->query("SET NAMES UTF8");
//查询限定的后缀，用于显示同时也用于判断用户是否属于此作业的班级
$stmt = $conn->prepare("SELECT path FROM upload WHERE user_account = ? AND homework_id = ?");
$stmt->bind_param("ii", $account, $id);
$stmt->execute();
$stmt->store_result();
$stmt->bind_result($path);
if ($stmt->num_rows() != 1) {
    $stmt->close();
    $conn->close();
    header('Location: ' . '/index.php');
    exit();
}
$stmt->fetch();
unlink(iconv("UTF-8", "gbk", $path));
$stmt->close();
$stmt = $conn->prepare("DELETE FROM upload WHERE user_account = ? AND homework_id = ?");
$stmt->bind_param("ii", $account, $id);
$stmt->execute();
if ($stmt->error) {
    $stmt->close();
    $conn->close();
    header('Location: ' . '/index.php');
    exit();
}
?>
<script>
    alert("删除成功！");
    window.location.href = '/index.php';
</script>