<?php 
require_once 'hidden/session_start.php';
require_once 'hidden/config.php';

if(isset($_SESSION['account'])){
    echo '你已登录！';
    exit();
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <?php require_once 'hidden/header_head.php'; ?>
    <title>作业上传系统-登录</title>
    <script>
        function check(account, password){
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
            return true;
        }
    </script>
    <link href="css/login.css" rel="stylesheet">
</head>

<body>
    <?php require_once 'hidden/header_body.php'; ?>
    <div class="main">
        <div id="login">
            <h1>Login</h1>
            <form method="POST" action="login_request.php">
                <input type="text" required="required" placeholder="学号" name="account"></input>
                <input type="password" required="required" placeholder="密码" name="password"></input>
                <button class="but" type="submit" onClick="return check(account.value,password.value);">登录</button>
            </form>
        </div>
    </div>
</body>

</html>