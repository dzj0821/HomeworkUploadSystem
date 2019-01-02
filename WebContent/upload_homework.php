<?php
require_once 'hidden/session_start.php';
require_once 'hidden/config.php';
//如果未登录或者没有传递需要提交的作业id
if (!isset($_SESSION['account']) || !isset($_GET['id'])) {
    header('Location: ' .  '/index.php');
    exit();
}
$account = $_SESSION['account'];
$id = $_GET['id'];
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
    header('Location: ' .  '/index.php');
    exit();
}
$stmt->close();
$conn->close();
?>
<html>
    <head>
        <?php require_once 'hidden/header_head.php'; ?>
        <title>作业上传系统-上传作业</title>
    </head>

    <body>
        <?php require_once 'hidden/header_body.php'; ?>
        <div class="main">
            <form method="POST" action="upload_homework_request.php" role="form" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name">选择需要提交的文件（大小应小于128M）</label>
                    <input type="text" name="id" value="<?php echo $id; ?>" hidden="hidden" />
                </div>
                <div class="form-group">
                    <label style="color: red;" for="name">注意：目前仅允许上传zip文件，以下限制暂时无效</label>
                    <label for="name">后缀名限制为
                        <?php
                        if ($suffix == "*") {
                            echo "任意";
                        }
                        foreach ($suffix as $value) {
                            echo $value . "&nbsp";
                        }
                        ?></label>
                    <input type="file" name="file" id="file" /> 
                </div>

                <button type="submit" class="btn btn-default">提交</button>
                <label style="color: red;" for="name">注意：点击后请注意浏览器左下角的上传进度，刷新或关闭页面会导致上传失败</label>
            </form>
        </div>
    </body>
</html>
