<?php
require_once 'hidden/session_start.php';
require_once 'hidden/config.php';

if (!isset($_SESSION['account']) || !isset($_POST['id']) || !isset($_FILES['file'])) {
    header('Location: ' . '/index.php');
    exit();
}
$account = $_SESSION['account'];
$name = $_SESSION['name'];
$id = $_POST['id'];
$file = $_FILES['file'];
//需要用户的class_id
require_once 'hidden/session_class_id.php';
$class_id = $_SESSION['class_id'];

$conn = new mysqli($database_host, $database_account, $database_password, $database_name);
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
$conn->query("SET NAMES UTF8");
//查询限定的后缀，用于显示同时也用于判断用户是否属于此作业的班级
$stmt = $conn->prepare("SELECT suffix FROM homework WHERE id = ? AND class_id = ?");
$stmt->bind_param("is", $id, $class_id);
$stmt->execute();
$stmt->store_result();
$stmt->bind_result($suffix);
if ($stmt->num_rows() == 1) {
    $stmt->fetch();
    if ($suffix != "*") {
        $suffix = explode("|", $suffix);
    }
} else {
    $stmt->close();
    $conn->close();
    header('Location: ' . '/index.php');
    exit();
}
$stmt->close();
//如果已经上传过
$stmt = $conn->prepare("SELECT id FROM upload WHERE user_account = ? AND homework_id = ?");
$stmt->bind_param("ii", $account, $id);
$stmt->execute();
$stmt->store_result();
if ($stmt->num_rows() != 0) {
    header('Location: /index.php');
    exit();
}
//
if ($file["error"] > 0) {
    switch ($file["error"]) { 
            case UPLOAD_ERR_INI_SIZE: 
                $message = "文件大小超过限制"; 
                break; 
            case UPLOAD_ERR_FORM_SIZE: 
                $message = "文件大小超过限制";
                break; 
            case UPLOAD_ERR_PARTIAL: 
                $message = "文件上传失败"; 
                break; 
            case UPLOAD_ERR_NO_FILE: 
                $message = "文件上传失败"; 
                break; 
            case UPLOAD_ERR_NO_TMP_DIR: 
                $message = "服务器找不到临时文件夹"; 
                break; 
            case UPLOAD_ERR_CANT_WRITE: 
                $message = "服务器写入磁盘失败"; 
                break; 
            default: 
                $message = "未知错误"; 
                break; 
        } 
    echo "Error: " . $file["error"] .$message. "<br />";
    exit();
}
if ($file['size'] / 1024 / 1024 > 128) {
    ?>
    <script>
        alert("你上传的文件超过规定大小（128M）！");
        self.location = document.referrer;
    </script>
    <?php
    exit();
}
$pos = strrpos($file['name'], '.');
if ($pos == -1 || substr($file['name'], $pos + 1) != 'zip') {
    ?>
    <script>
        alert("你上传的文件不是zip类型！完整文件名：<?php echo $file['name']; ?>");
        self.location = document.referrer;
    </script>
    <?php
    exit();
}
$dir = iconv("UTF-8", "GBK", "hidden/upload/" . $id);
if (!file_exists($dir)) {
    mkdir($dir, 0777, true);
}
$path = $dir . "/" . $account . $name . ".zip";
move_uploaded_file($_FILES["file"]["tmp_name"], iconv("UTF-8", "gbk", $path));
$stmt = $conn->prepare("INSERT INTO upload(user_account, homework_id, path, update_time) VALUES(?, ?, ?, NOW())");
$stmt->bind_param("iis", $account, $id, $path);
$stmt->execute();
if ($stmt->error) {
    $stmt->close();
    $conn->close();
    header('Location: ' . '/index.php');
    exit();
}
?>
<script>
    alert("提交成功！");
    window.location.href = '/index.php';
</script>