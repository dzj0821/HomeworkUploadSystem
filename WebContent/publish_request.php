<?php
require_once 'hidden/session_start.php';
require_once 'hidden/config.php';

if (!isset($_SESSION['account']) || !isset($_SESSION['permission']) || $_SESSION['permission'] != "administrator" || !isset($_POST['class_id']) || !isset($_POST['homework_name']) || !isset($_POST['suffix'])) {
    header('Location: ' . '/index.php');
    exit();
}
$account = $_SESSION['account'];
for ($i = 0; $i < count($_POST['class_id']); $i++) {
    $class_ids[$i] = intval($_POST['class_id'][$i]);
}
$homework_name = $_POST['homework_name'];
$suffix = $_POST['suffix'];
if (isset($_POST['text']) && $_POST['text'] != "") {
    $text = $_POST['text'];
}
$conn = new mysqli($database_host, $database_account, $database_password, $database_name);
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
//设置编码否则默认以GBK读出导致乱码
$conn->query("SET NAMES UTF8");
for ($i = 0; $i < count($class_ids); $i++) {
    //检查权限
    $stmt = $conn->prepare("SELECT id FROM permission WHERE user_account = ? AND class_id = ?");
    $stmt->bind_param("ii", $account, $class_ids[$i]);
    $stmt->execute();
    $stmt->store_result();
    //如果没有这个班的权限，跳过
    if($stmt->num_rows() != 1){
        $stmt->close();
        continue;
    }
    $stmt->close();
    if (isset($text)) {
        $stmt = $conn->prepare("INSERT INTO homework(homework_name, text, suffix, publisher_account, class_id) VALUES(?, ?, ?, ?, ?)");
        $stmt->bind_param("sssii", $homework_name, $text, $suffix, $account, $class_ids[$i]);
    } else {
        $stmt = $conn->prepare("INSERT INTO homework(homework_name, suffix, publisher_account, class_id) VALUES(?, ?, ?, ?)");
        $stmt->bind_param("ssii", $homework_name, $suffix, $account, $class_ids[$i]);
    }
    $stmt->execute();
    if ($stmt->error) {
        $stmt->close();
        $conn->close();
        die($stmt->error);
    }
    $stmt->close();
}
$conn->close();
?>
<script>
    alert("发布成功！");
    window.location.href = "list.php";
</script>