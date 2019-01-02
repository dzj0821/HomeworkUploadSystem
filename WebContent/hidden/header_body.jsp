<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?php
require_once 'session_start.php';
require_once 'config.php';
//获取用户权限
if (isset($_SESSION['account']) && !isset($_SESSION['permission'])) {
    $account = $_SESSION['account'];
    $conn = new mysqli($database_host, $database_account, $database_password, $database_name);
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }
//设置编码否则默认以GBK读出导致乱码
    $conn->query("SET NAMES UTF8");
    $stmt = $conn->prepare("SELECT class.id, class_name FROM (SELECT class_id FROM permission WHERE user_account = ?) AS a, class WHERE a.class_id = class.id");
    $stmt->bind_param("i", $account);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($class_id, $class_name);
    for ($i = 0; $stmt->fetch(); $i++) {
        $_SESSION['manage_class'][$i]['id'] = $class_id;
        $_SESSION['manage_class'][$i]['name'] = $class_name;
    }
    if (isset($_SESSION['manage_class'])) {
        $_SESSION['permission'] = "administrator";
    } else {
        $_SESSION['permission'] = 'general';
    }
}
?>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.php">作业上传系统</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <?php
                if (isset($_SESSION['account'])) {
                    ?>
                    <li>
                        <a href="list.php">作业列表</a>
                    </li>
                    <?php
                }
                if (isset($_SESSION['permission']) && $_SESSION['permission'] == "administrator") {
                    ?>
                    <li>
                        <a href="publish.php">发布作业</a>
                    </li>
                    <?php
                }
                ?>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <?php
                if (isset($_SESSION['account'])) {
                    ?>
                    <li>
                        <a href="javascript:void(0)">欢迎，<?php echo $_SESSION['account']; ?></a>
                    </li>
                    <li>
                        <a href="exit.php">退出登录</a>
                    </li>
                    <?php
                } else {
                    ?>
                    <li>
                        <a href="login.php">登录</a>
                    </li>
                    <li>
                        <a href="register.php">注册</a>
                    </li>
                    <?php
                }
                ?>
            </ul>
        </div>
    </div>
</nav>