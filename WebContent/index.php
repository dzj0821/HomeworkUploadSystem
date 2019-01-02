<?php
require_once 'hidden/session_start.php';
if (isset($_SESSION['account'])) {
    require_once 'list.php';
    exit();
}
?>
<html>
    <head>
        <?php require_once 'hidden/header_head.php'; ?>
        <title>作业上传系统-主页</title>
    </head>

    <body>
        <?php require_once 'hidden/header_body.php'; ?>
        <div class="main" style="text-align: center;">
            <h1>作业上传系统</h1>
        </div>
    </body>
</html>
