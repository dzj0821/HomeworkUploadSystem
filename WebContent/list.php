<?php
require_once 'hidden/session_start.php';
require_once 'hidden/config.php';

if (!isset($_SESSION['account'])) {
    header('Location: ' . '/index.php');
    exit();
}
$account = $_SESSION['account'];
$conn = new mysqli($database_host, $database_account, $database_password, $database_name);
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
$conn->query("SET NAMES UTF8");
require_once 'hidden/session_class_id.php';
$class_id = $_SESSION['class_id'];
$stmt = $conn->prepare("SELECT a.id, a.homework_name, b.id, c.user_name FROM ((SELECT id, homework_name, publisher_account FROM"
        . " homework WHERE class_id = ?) as a LEFT JOIN (SELECT id, homework_id FROM"
        . " upload WHERE user_account = ?) as b ON a.id = b.homework_id), "
        . "(SELECT user_name, account FROM user) as c WHERE publisher_account = c.account");
$stmt->bind_param("ii", $class_id, $account);
$stmt->execute();
$stmt->store_result();
$stmt->bind_result($homework_id, $homework_name, $upload_id, $user_name);
for ($i = 0; $stmt->fetch(); $i++) {
    $homework_list[$i]['homework_name'] = $homework_name;
    $homework_list[$i]['homework_id'] = $homework_id;
    $homework_list[$i]['upload_id'] = $upload_id;
    $homework_list[$i]['user_name'] = $user_name;
}
$stmt->close();
$conn->close();
?>
<!DOCTYPE html>
<html lang="en">

    <head>
        <?php require_once 'hidden/header_head.php'; ?>
        <title>作业上传系统-作业列表</title>
        <style>
            table{
                text-align: center;
            }
            th{
                text-align: center;
            }
        </style>
        <script>
            function del(id) {
                if (confirm("你确定要删除这个提交吗？")) {
                    window.location.href = "delete_homework_request.php?id=" + id;
                }
            }
        </script>
    </head>

    <body>
        <?php require_once 'hidden/header_body.php'; ?>
        <div class="main" style="text-align: center;">
            <h1 style="text-align:center;">作业列表</h1>

            <div class="table-responsive">
                <table class="table table-striped" style="margin:0 auto;">
                    <thead>
                        <tr>
                            <th>作业名称</th>
                            <th>发布者</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        if (isset($homework_list)) {
                            foreach ($homework_list as $homework) {
                                ?>
                                <tr>
                                    <td><?php echo $homework['homework_name']; ?></td>
                                    <td><?php echo $homework['user_name']; ?></td>
                                    <td>
                                        <?php
                                        if ($homework['upload_id'] === NULL) {
                                            echo "未提交";
                                        } else {
                                            echo "已提交";
                                        }
                                        ?>
                                    </td>
                                    <td>
                                        <?php
                                        if ($homework['upload_id'] === NULL) {
                                            ?>
                                            <a href="upload_homework.php?id=<?php echo $homework['homework_id']; ?>">提交作业</a>
                                            <?php
                                        } else {
                                            ?>
                                            <a href="javascript:void(0)" onclick="del(<?php echo $homework['homework_id']; ?>)">删除提交</a>
                                            <?php
                                        }
                                        ?>
                                    </td>
                                </tr>
                                <?php
                            }
                        }
                        ?>
                    </tbody>
                </table>
            </div>
            <?php /*
              <div style="text-align: center;">
              <ul class="pagination">
              <li><a href='./classlist.php?page=1'>&lt;&lt;</a></li>
              <?php
              for ($i = $page - 2; $i <= $page + 2; $i++) {
              if ($i >= 1 && $i <= $max_page) {
              if ($i == $page) {
              echo "<li class='active'><a href='./classlist.php?page=$i'>$i</a></li>";
              } else {
              echo "<li><a href='./classlist.php?page=$i'>$i</a></li>";
              }
              }
              }
              ?>
              <li><a href='./classlist.php?page=<?php echo $max_page; ?>'>&gt;&gt;</a></li>
              </ul>
              </div>
             * 
             */
            ?>
        </div>
    </div>
</body>

</html>