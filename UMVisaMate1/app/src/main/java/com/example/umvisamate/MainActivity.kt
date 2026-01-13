package com.example.umvisamate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// 数据库实体类
data class User(
    val email: String,
    val password: String,
    val name: String,
    val fullName: String,
    val role: String,
    val passport: String,
    val faculty: String,
    val visaDaysLeft: Int,
    val pendingReview: Int = 0,
    val approved: Int = 0,
    val urgent: Int = 0,
    val inReview: Int = 0,
    val processed: Int = 0
)

// 模拟数据库
val userDatabase = listOf(
    User(
        email = "ahmed@um.edu.my",
        password = "123456",
        name = "Ahmed",
        fullName = "Ahmed Hassan",
        role = "Student",
        passport = "A12345678",
        faculty = "Faculty of Computer Science",
        visaDaysLeft = 278
    ),
    User(
        email = "sarah@um.edu.my",
        password = "faculty123",
        name = "Dr. Sarah Ahmad",
        fullName = "Dr. Sarah Ahmad",
        role = "FacultyOfficer",
        passport = "",
        faculty = "Faculty of Computer Science",
        visaDaysLeft = 0,
        pendingReview = 12,
        approved = 47
    ),
    User(
        email = "razak@um.edu.my",
        password = "visa123",
        name = "Razak bin Abdullah",
        fullName = "Razak bin Abdullah",
        role = "VisaUnitOfficer",
        passport = "",
        faculty = "UM International Office",
        visaDaysLeft = 0,
        urgent = 8,
        inReview = 34,
        processed = 156
    )
)

// ------------------- 小组配色规范 -------------------
private val Biscay = Color(0xFF163269)     // Primary Base/Text
private val White = Color(0xFFFFFFFF)     // Background/Canvas
private val LightGray = Color(0xFFF3F4F5) // Background Secondary
private val SteelBlue = Color(0xFF4A7FBA) // Interactive/CTA
private val DarkGreen = Color(0xFF008000) // Success Status
private val WarningRed = Color(0xFFCC0000) // Warning/Alert
private val MaizeYellow = Color(0xFFFCCB05)// Notification
private val GrayIcon = Color(0xFF808080)  // 灰色标识色
private val LightYellow = Color(0xFFFEEAA7) // Urgent 浅黄

// 页面路由
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object StudentDashboard : Screen("student_dashboard")
    object FacultyOfficer : Screen("faculty_officer")
    object VisaUnitOfficer : Screen("visa_unit_officer")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    primary = Biscay,
                    onPrimary = White,
                    secondary = SteelBlue,
                    onSecondary = White
                ),
                typography = Typography(
                    bodyLarge = TextStyle(fontSize = 14.sp, color = Biscay),
                    bodyMedium = TextStyle(fontSize = 12.sp, color = GrayIcon),
                    titleLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Biscay),
                    titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Biscay),
                    labelSmall = TextStyle(fontSize = 10.sp, color = GrayIcon)
                ),
                shapes = Shapes(
                    small = RoundedCornerShape(8.dp),
                    medium = RoundedCornerShape(12.dp),
                    large = RoundedCornerShape(16.dp)
                )
            ) {
                val navController = rememberNavController()
                var currentUser by remember { mutableStateOf<User?>(null) }

                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.route
                ) {
                    composable(Screen.Login.route) {
                        LoginScreen(
                            onLoginSuccess = { user ->
                                currentUser = user
                                when (user.role) {
                                    "Student" -> navController.navigate(Screen.StudentDashboard.route)
                                    "FacultyOfficer" -> navController.navigate(Screen.FacultyOfficer.route)
                                    "VisaUnitOfficer" -> navController.navigate(Screen.VisaUnitOfficer.route)
                                }
                            }
                        )
                    }

                    composable(Screen.StudentDashboard.route) {
                        currentUser?.let { user ->
                            if (user.role == "Student") {
                                StudentDashboardScreen(user = user)
                            } else {
                                navController.popBackStack(Screen.Login.route, false)
                            }
                        } ?: navController.popBackStack(Screen.Login.route, false)
                    }

                    composable(Screen.FacultyOfficer.route) {
                        currentUser?.let { user ->
                            if (user.role == "FacultyOfficer") {
                                FacultyOfficerScreen(user = user)
                            } else {
                                navController.popBackStack(Screen.Login.route, false)
                            }
                        } ?: navController.popBackStack(Screen.Login.route, false)
                    }

                    composable(Screen.VisaUnitOfficer.route) {
                        currentUser?.let { user ->
                            if (user.role == "VisaUnitOfficer") {
                                VisaUnitOfficerScreen(user = user)
                            } else {
                                navController.popBackStack(Screen.Login.route, false)
                            }
                        } ?: navController.popBackStack(Screen.Login.route, false)
                    }
                }
            }
        }
    }
}

// 第一页：登录界面
@Composable
fun LoginScreen(
    onLoginSuccess: (User) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("Student") }
    var errorMsg by remember { mutableStateOf("") }

    fun validateLogin() {
        val matchedUser = userDatabase.find {
            it.email == email && it.password == password && it.role == selectedRole
        }
        if (matchedUser != null) {
            errorMsg = ""
            onLoginSuccess(matchedUser)
        } else {
            errorMsg = "Invalid email/password/role!"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "UM VISA MATE",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Biscay
        )
        Text(
            text = "Universiti Malaya Visa Management",
            fontSize = 12.sp,
            color = GrayIcon,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email Address") },
            placeholder = { Text(text = "your.email@um.edu.my") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter your password") },
            trailingIcon = {
                TextButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(text = if (passwordVisible) "Hide" else "Show", fontSize = 12.sp, color = Biscay)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            shape = RoundedCornerShape(8.dp)
        )

        Text(
            text = "Login As",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Biscay,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedRole == "Student",
                onClick = { selectedRole = "Student" },
                colors = RadioButtonDefaults.colors(selectedColor = Biscay)
            )
            Column(Modifier.padding(start = 8.dp)) {
                Text(text = "Student", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "International Student", fontSize = 12.sp, color = GrayIcon)
            }
        }

        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedRole == "FacultyOfficer",
                onClick = { selectedRole = "FacultyOfficer" },
                colors = RadioButtonDefaults.colors(selectedColor = Biscay)
            )
            Column(Modifier.padding(start = 8.dp)) {
                Text(text = "Faculty Officer", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "Faculty Admin", fontSize = 12.sp, color = GrayIcon)
            }
        }

        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedRole == "VisaUnitOfficer",
                onClick = { selectedRole = "VisaUnitOfficer" },
                colors = RadioButtonDefaults.colors(selectedColor = Biscay)
            )
            Column(Modifier.padding(start = 8.dp)) {
                Text(text = "Visa Unit Officer", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "Immigration Unit", fontSize = 12.sp, color = GrayIcon)
            }
        }

        if (errorMsg.isNotEmpty()) {
            Text(
                text = errorMsg,
                color = WarningRed,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Button(
            onClick = { validateLogin() },
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(top = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Biscay),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Sign In", fontSize = 16.sp, color = White)
        }
    }
}

// 第二页：学生主界面（纯形状+文本，无任何图标）
@Composable
fun StudentDashboardScreen(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
    ) {
        // 顶部栏（纯文本+圆形占位）
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Biscay)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(text = "Welcome back", fontSize = 12.sp, color = White.copy(0.8f))
                Text(text = user.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = White)
            }
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 通知图标占位（圆形）
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(White.copy(0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🔔", fontSize = 16.sp, color = White)
                }
                // 分享图标占位（圆形）
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(White.copy(0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "↗", fontSize = 16.sp, color = White)
                }
            }
        }

        // 紧急提醒横幅
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaizeYellow)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                // 警告标识占位
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Biscay)
                        .padding(top = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "!", fontSize = 12.sp, color = MaizeYellow, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Urgent Action Required",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Biscay
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Biscay)
                                .padding(4.dp, 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "-${user.visaDaysLeft} days", fontSize = 12.sp, color = White)
                        }
                    }
                    Text(
                        text = "Your visa expires in less than 3 months. Please start renewal now to avoid any complications.",
                        fontSize = 14.sp,
                        color = Biscay.copy(0.9f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // 申请状态标题
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Application Status",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Biscay
            )
            Text(
                text = "Updated 2 hours ago",
                fontSize = 12.sp,
                color = GrayIcon
            )
        }

        // 状态流程（纯形状+文本，无图标）
        Column(modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)) {
            StatusStep(
                shapeColor = DarkGreen,
                shapeText = "✓",
                title = "Pre-Application",
                isCompleted = true,
                hasLineBelow = true
            )
            StatusStep(
                shapeColor = SteelBlue,
                shapeText = "◔",
                title = "Waiting for Faculty",
                subTitle = "In Progress",
                isCompleted = false,
                hasLineBelow = true
            )
            StatusStep(
                shapeColor = GrayIcon,
                shapeText = "$",
                title = "Waiting for Payment",
                isCompleted = false,
                hasLineBelow = true
            )
            StatusStep(
                shapeColor = GrayIcon,
                shapeText = "→",
                title = "Submitted",
                isCompleted = false,
                hasLineBelow = true
            )
            StatusStep(
                shapeColor = GrayIcon,
                shapeText = "👤",
                title = "Approved",
                isCompleted = false,
                hasLineBelow = false
            )
        }

        // 学生信息卡片
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LightGray)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // 用户头像占位
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Biscay.copy(0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "👤", fontSize = 16.sp, color = Biscay)
                    }
                    Column {
                        Text(
                            text = user.fullName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Biscay
                        )
                        Text(
                            text = "International Student",
                            fontSize = 12.sp,
                            color = GrayIcon
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                InfoItem(
                    label = "Passport Number",
                    value = user.passport,
                    prefix = "📄"
                )
                InfoItem(
                    label = "Faculty",
                    value = user.faculty,
                    prefix = "🏫",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // 续签按钮
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SteelBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Start / Continue Renewal →",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }

        // 底部文字
        Text(
            text = "Complete your visa renewal application",
            fontSize = 12.sp,
            color = GrayIcon,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        Text(
            text = "Dashboard",
            fontSize = 12.sp,
            color = Biscay,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
    }
}

// 学生状态步骤组件（纯形状+文本）
@Composable
fun StatusStep(
    shapeColor: Color,
    shapeText: String,
    title: String,
    subTitle: String = "",
    isCompleted: Boolean,
    hasLineBelow: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(shapeColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = shapeText,
                    fontSize = 18.sp,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }
            if (hasLineBelow) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(40.dp)
                        .background(if (isCompleted) shapeColor.copy(0.5f) else GrayIcon.copy(0.3f))
                        .padding(top = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isCompleted) Biscay else GrayIcon
            )
            if (subTitle.isNotEmpty()) {
                Text(
                    text = subTitle,
                    fontSize = 12.sp,
                    color = SteelBlue,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

// 信息项组件（纯文本前缀）
@Composable
fun InfoItem(
    label: String,
    value: String,
    prefix: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = prefix, fontSize = 16.sp, color = GrayIcon)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontSize = 12.sp, color = GrayIcon, modifier = Modifier.width(100.dp))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Biscay)
    }
}

// 第四页：院系管理员界面（纯形状+文本）
@Composable
fun FacultyOfficerScreen(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
    ) {
        // 顶部栏
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Biscay)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(text = "Faculty Portal", fontSize = 12.sp, color = White.copy(0.8f))
                Text(text = user.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = White)
            }
            // 分享图标占位
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(White.copy(0.2f))
                    .align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "↗", fontSize = 16.sp, color = White)
            }
        }

        // 数据卡片
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DataCard(
                number = user.pendingReview.toString(),
                label = "Pending Review",
                shapeColor = SteelBlue,
                shapeText = "◔",
                modifier = Modifier.weight(1f)
            )
            DataCard(
                number = user.approved.toString(),
                label = "Approved",
                shapeColor = DarkGreen,
                shapeText = "✓",
                modifier = Modifier.weight(1f)
            )
        }

        // 功能说明卡片
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LightGray)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Biscay.copy(0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "👤", fontSize = 12.sp, color = Biscay)
                    }
                    Column {
                        Text(
                            text = "Faculty Officer Portal",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Biscay
                        )
                        Text(
                            text = user.faculty,
                            fontSize = 12.sp,
                            color = GrayIcon
                        )
                    }
                }
                Text(
                    text = "Review and approve visa renewal applications from students in your faculty. This module will be available in Module 3.",
                    fontSize = 14.sp,
                    color = Biscay.copy(0.9f),
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        // 禁用按钮
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SteelBlue.copy(0.5f)),
            shape = RoundedCornerShape(12.dp),
            enabled = false
        ) {
            Text(
                text = "View Pending Applications",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }

        // 底部文字
        Text(
            text = "Coming soon in Module 3",
            fontSize = 12.sp,
            color = GrayIcon,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
    }
}

// 第五页：签证管理员界面（纯形状+文本）
@Composable
fun VisaUnitOfficerScreen(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
    ) {
        // 顶部栏
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Biscay)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(text = "Visa Unit Portal", fontSize = 12.sp, color = White.copy(0.8f))
                Text(text = user.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = White)
            }
            // 分享图标占位
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(White.copy(0.2f))
                    .align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "↗", fontSize = 16.sp, color = White)
            }
        }

        // 数据卡片
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DataCard(
                number = user.urgent.toString(),
                label = "Urgent",
                shapeColor = LightYellow,
                shapeText = "!",
                shapeTextColor = MaizeYellow,
                modifier = Modifier.weight(1f)
            )
            DataCard(
                number = user.inReview.toString(),
                label = "In Review",
                shapeColor = SteelBlue.copy(0.1f),
                shapeText = "📄",
                shapeTextColor = SteelBlue,
                modifier = Modifier.weight(1f)
            )
            DataCard(
                number = user.processed.toString(),
                label = "Processed",
                shapeColor = DarkGreen.copy(0.1f),
                shapeText = "✓",
                shapeTextColor = DarkGreen,
                modifier = Modifier.weight(1f)
            )
        }

        // 功能说明卡片
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LightGray)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Biscay.copy(0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🛡", fontSize = 12.sp, color = Biscay)
                    }
                    Column {
                        Text(
                            text = "Immigration Unit Portal",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Biscay
                        )
                        Text(
                            text = user.faculty,
                            fontSize = 12.sp,
                            color = GrayIcon
                        )
                    }
                }
                Text(
                    text = "Process and approve visa renewal applications for all UM international students. Full functionality will be available in Module 4.",
                    fontSize = 14.sp,
                    color = Biscay.copy(0.9f),
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        // 按钮1：View All Applications
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SteelBlue.copy(0.5f)),
            shape = RoundedCornerShape(12.dp),
            enabled = false
        ) {
            Text(
                text = "View All Applications",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }

        // 按钮2：Generate Reports
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = false,
            border = BorderStroke(1.dp, SteelBlue.copy(0.5f))
        ) {
            Text(
                text = "Generate Reports",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = SteelBlue.copy(0.5f)
            )
        }

        // 底部文字
        Text(
            text = "Coming soon in Module 4",
            fontSize = 12.sp,
            color = GrayIcon,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
    }
}

// 数据卡片组件（纯形状+文本）
@Composable
fun DataCard(
    number: String,
    label: String,
    shapeColor: Color,
    shapeText: String,
    shapeTextColor: Color = White,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(LightGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(shapeColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = shapeText,
                    fontSize = 18.sp,
                    color = shapeTextColor,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = number,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Biscay,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = GrayIcon,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}