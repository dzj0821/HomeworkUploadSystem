<?php 
require_once 'hidden/session_start.php';
require_once 'hidden/config.php';
//如果已经登录/未提交用户名或密码/用户名和密码不符合格式
if(isset($_SESSION['account']) || !isset($_POST['account']) || !isset($_POST['password']) || !preg_match($account_pattern, $_POST['account']) || !preg_match($password_pattern, $_POST['password'])){
    //回到主页
    header('Location: '.'/index.php');
    exit();
}
$account = $_POST['account'];
$password = $_POST['password'];

$conn = new mysqli($database_host, $database_account, $database_password, $database_name);
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
//加密密码
$password_md5 = md5(md5($password.$salt).$salt);
//设置编码否则默认以GBK读出导致乱码
$conn->query("SET NAMES UTF8");
//判断是否存在用户名和密码都相同的用户
$stmt = $conn->prepare("SELECT user_name FROM user WHERE account = ? AND password_md5 = ?");
$stmt->bind_param("is",$account, $password_md5);
$stmt->execute();
$stmt->store_result();
$stmt->bind_result($name);
//有且只有一个用户时代表登录成功
if($stmt->num_rows() == 1){
    //使用session里的account作为登录状态
    $stmt->fetch();
    $_SESSION['account'] = $account;
    $_SESSION['name'] = $name;
    header('Location: '.'/index.php');
}
else{
    //否则弹出提示框并回到登陆界面
    ?>
<script>
    alert("用户名或密码错误！");
    window.location.href = "<?php echo '/login.php'; ?>";
</script>
<?php
}
$stmt->close();
$conn->close();