<?php
require_once 'hidden/session_start.php';
require_once 'hidden/config.php';

if(isset($_SESSION['account'])){
    echo '你已登录！';
    exit();
}
$conn = new mysqli($database_host, $database_account, $database_password, $database_name);
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
//设置编码否则默认以GBK读出导致乱码
$conn->query("SET NAMES UTF8");
$stmt = $conn->prepare("SELECT id, class_name FROM class");
$stmt->execute();
$stmt->store_result();
$stmt->bind_result($id, $class_name);
for($i = 0; $stmt->fetch(); $i++){
    $class_list[$i]['id'] = $id;
    $class_list[$i]['name'] = $class_name;
}
$stmt->close();
$conn->close();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require_once 'hidden/header_head.php'; ?>
    <title>作业上传系统-注册</title>
    <link rel="stylesheet" href="css/register.css"/>  
    <script>
        function check(account, password, user_name, class_id){
            var reg_account = <?php echo $account_pattern; ?>;
            if(!reg_account.test(account)){
                alert("学号格式有误！");
                return false;
            }
            var reg_password = <?php echo $password_pattern; ?>;
            if (!reg_password.test(password)) {
                alert("密码必须为字母或数字且长度在6-18字符之间！");
                return false;
            }
            var password_once = document.getElementById("password_once").value;
            if(password !== password_once){
                alert("两次密码输入不相同！");
                return false;
            }
            var reg_user_name = <?php echo $user_name_pattern_js; ?>;
            if(!reg_user_name.test(user_name)){
                alert("姓名格式有误！");
                return false;
            }
            return true;
        }
    </script>  
</head>

<body>
    <?php require_once 'hidden/header_body.php'; ?>
    <div class="main" style="text-align: center;">
        <div id="register">  
            <h1>Register</h1>  
            <form method="POST" action="register_request.php" role="form">  
                <input type="text" required="required" placeholder="学号" name="account" />
                <input type="password" required="required" placeholder="密码" name="password"/>
                <input type="password" required="required" placeholder="确认密码" id="password_once"/>
                <input type="text" required="required" placeholder="姓名" name="user_name" />
                <div class="form-group">
                    <label for="name">选择你的班级：</label>
                    <select name="class_id" class="form-control">
                        <?php
                        foreach ($class_list as $class) {
                            ?>
                        <option value=<?php echo $class['id']; ?>><?php echo $class['name']; ?></option>
                        <?php
                        }
                        ?>
                    </select>
                </div>
                <button class="but" type="submit" onClick="return check(account.value, password.value, user_name.value, class_id.value);">注册</button>  
            </form>  
        </div>  
    </div>
</body>

</html>